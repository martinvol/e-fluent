from . import models
from rest_framework import serializers
from django.contrib.auth.models import User


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('username', 'first_name', 'last_name', 'email')


class PatientSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(required=True)
    user = UserSerializer()

    class Meta:
        model = models.Patient
        fields = ('id', 'user')



class UserSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)


    class Meta:
        model = User
        fields = ('username', 'email', 'password')

    def create(self, validated_data):
        #profile_data = validated_data.pop('profile')
        #user = User.objects.create(**validated_data)
        #Profile.objects.create(user=user, **profile_data)
        return user


class MeetingSerializer(serializers.ModelSerializer):
    orthophoniste = None

    class Meta:
        model = models.Meeting
        fields = ('time', 'patient', 'orthophoniste')
    
    def create(self, validated_data):
        return models.Meeting.objects.create(
            time = validated_data['time'],
            orthophoniste = self.orthophoniste,
            patient = validated_data['patient'] #models.Patient.objects.get(id=validated_data['patient'])
        )

