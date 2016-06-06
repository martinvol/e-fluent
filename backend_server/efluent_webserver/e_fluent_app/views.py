from django.shortcuts import render
from django.http import HttpResponse
from django.core.exceptions import ObjectDoesNotExist

from rest_framework.decorators import api_view
from rest_framework.authtoken.models import Token
from rest_framework.views import APIView
from rest_framework import permissions, status
from rest_framework.response import Response
from rest_framework.authtoken.views import ObtainAuthToken


from django.contrib.auth.decorators import login_required

from . import serializers, models
# Create your views here.



from django.http import HttpResponse
from rest_framework.renderers import JSONRenderer

class CustomLoginHadshake(ObtainAuthToken):
    def post(self, request, *args, **kwargs):
        serializer = self.serializer_class(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.validated_data['user']
        token, created = Token.objects.get_or_create(user=user)
        user.__class__ = models.CustomUser
        user.get_role().role_name
        return Response({'token': token.key, 'role_name': user.get_role().role_name})


class JSONResponse(HttpResponse):
    """
    An HttpResponse that renders its content into JSON.
    """
    def __init__(self, data, **kwargs):
        content = JSONRenderer().render(data)
        kwargs['content_type'] = 'application/json'
        super(JSONResponse, self).__init__(content, **kwargs)



class AddPatient(APIView):
    """
    API call to create a patient and add it to an Orthoponiste
    """


    permission_classes = (permissions.IsAuthenticated,)

    # def get(self, request, format=None):
    #     snippets = Snippet.objects.all()
    #     serializer = SnippetSerializer(snippets, many=True)
    #     return Response(serializer.data)
        # return Response()

    def post(self, request, format=None):
        request.user.__class__ = models.CustomUser
        requested = request.user

        if not isinstance(requested.get_role(), models.Orthophoniste):
            return Response({'detail' : "Patients can't do this actions", 'id' : patient_id}, 
                    status = status.HTTP_401_UNAUTHORIZED)

        serializer = serializers.AddPatientSerializer(data=request.data)
        if not serializer.is_valid():            
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

        patient = serializer.create(serializer.validated_data)
        patient.orthophoniste = requested.get_role()
        patient.save()
        #serializer.save()
        print (serializer.data)


        
        return Response(serializer.data, status=status.HTTP_200_OK)

class PatientList(APIView):
    permission_classes = (permissions.IsAuthenticated,)


    def get(self, request, format=None):
        requested = models.CustomUser.objects.get(id=request.user.id)
        if not isinstance(requested.get_role(), models.Orthophoniste):
            return Response({'detail' : "Patients can't do this actions", 'id' : patient_id}, 
                    status = status.HTTP_401_UNAUTHORIZED)

        serializer = serializers.PatientSerializer(requested.orthophoniste.patient_set.all(), many=True)
        return Response(serializer.data)

        #print(models.Orthophoniste.patient_set.all())

@api_view(['POST'])
def create_auth(request):
    serialized = serializers.UserSerializer(data=request.data)
    if serialized.is_valid():
        print("is_valid")
        new_user = models.CustomUser.objects.create_user(
            serialized.validated_data['email'],
            serialized.validated_data['username'],
            serialized.validated_data['password']
        )
        patient = models.Patient()
        patient.user = new_user
        patient.save()
        return Response(serialized.data, status=status.HTTP_201_CREATED)
    else:
        return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)

class CreateMeeting(APIView):
    permission_classes = (permissions.IsAuthenticated,)

    def get(self, request, format=None):
        request.user.__class__ = models.CustomUser
        meetings = request.user.get_role().get_meetings()
        serializer = serializers.MeetingSerializer(meetings, many=True)
        return Response(serializer.data)


    def post(self, request, format=None):
        # create new rendes-vous, arguments ()
        request.user.__class__ = models.CustomUser

        if request.user.get_role().__class__  != models.Orthophoniste:
            return Response({'detail' : "Patients can't do this actions"}, 
                    status = status.HTTP_401_UNAUTHORIZED)

        data = dict(request.data)

        serializer = serializers.MeetingSerializer(data=data)
        serializer.orthophoniste = request.user.get_role()

        if serializer.is_valid():
            serializer.save()
            #print("is_valid")
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class Exercises(APIView):
    permission_classes = (permissions.IsAuthenticated,)

    def get(self, request, format=None):
        request.user.__class__ = models.CustomUser
        if request.user.get_role().__class__  != models.Orthophoniste:
            exercises = request.user.get_role().get_exercises()
        else:
            return Response({'detail' : "Orthophonistes doesn't have Exercises"}, 
                status=status.HTTP_400_BAD_REQUEST)
        serializer = serializers.ExercisesSerializer(exercises, many=True)
        return Response(serializer.data)


    def post(self, request, format=None):
        print("post")
        request.user.__class__ = models.CustomUser
        if request.user.get_role().__class__  != models.Orthophoniste:
            return Response({'detail' : "Patients can't do this actions"}, 
                    status = status.HTTP_401_UNAUTHORIZED)
        serializer = serializers.ExercisesSerializer(data=request.data)

        if serializer.is_valid():
            if models.Patient.objects.get(id=request.data['patient']).orthophoniste == request.user.get_role():
                serializer.save()
                #print("is_valid")
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            else:
                return Response({'detail' : "You can only add exercises to you patients"}, 
                    status = status.HTTP_401_UNAUTHORIZED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)