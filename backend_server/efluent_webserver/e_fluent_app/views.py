from django.shortcuts import render
from django.http import HttpResponse
from django.core.exceptions import ObjectDoesNotExist

from rest_framework.decorators import api_view
from rest_framework.authtoken.models import Token
from rest_framework.views import APIView
from rest_framework import permissions, status
from rest_framework.response import Response



from . import serializers, models
# Create your views here.


class AddPatient(APIView):
    """
    List all snippets, or create a new snippet.
    """
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,)

    # def get(self, request, format=None):
    #     snippets = Snippet.objects.all()
    #     serializer = SnippetSerializer(snippets, many=True)
    #     return Response(serializer.data)

    def post(self, request, format=None):
        serializer = serializers.PatientSerializer(data=request.data)
        if not serializer.is_valid():            
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


        requested = models.CustomUser.objects.get(id=request.user.id)
        patient_id = serializer.validated_data['id']
        

        if not isinstance(requested.get_role(), models.Orthophoniste):
            return Response({'detail' : "Patients can't do this actions", 'id' : patient_id}, 
                    status = status.HTTP_401_UNAUTHORIZED)


        try:
            patient = models.Patient.objects.get(id=patient_id)
            #print(request.user.get_role())
            patient.orthophoniste = models.CustomUser.objects.get(id=request.user.id).orthophoniste
            # print("llego2")
            patient.save()

        except ObjectDoesNotExist:
            return Response({'detail' : "Patient does not exist", 'id' : patient_id}, 
                    status = status.HTTP_400_BAD_REQUEST)



                # add
            # fail
        
        return Response(serializer.data, status=status.HTTP_200_OK)
        #return Response(serializer.data, status=status.HTTP_501_NOT_IMPLEMENTED)



#@login_required
@api_view(['POST',])
def add_patient(request):
    """An Orthoponist will use this view to add a patient to its list"""
