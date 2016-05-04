from . import models
from rest_framework import serializers

class PatientSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(required=True)

    class Meta:
        model = models.Patient
        fields = ('id',)