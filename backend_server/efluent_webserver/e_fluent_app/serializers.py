from . import models
from rest_framework import serializers
from django.contrib.auth.models import User


class PatientSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(required=True)

    class Meta:
        model = models.Patient
        fields = ('id',)



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