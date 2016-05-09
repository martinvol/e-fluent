from django.db import models

# Create your models here.

from django.contrib.auth.models import User


class CustomUser(User):
    class Meta:
        proxy = True

    # def __str__(self):
    #     return self.name.upper()

    def get_role(self):
        ortho = getattr(self, 'orthophoniste', None)
        patient = getattr(self, 'patient', None)
        return ortho if ortho else patient


class Orthophoniste(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, null=False)

    def __str__(self):
        return "Orthophoniste"

class Patient(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, null=False)
    orthophoniste = models.ForeignKey(Orthophoniste, null=True, blank=True)

	
    def __str__(self):
        return "Patient"

#Signals to create a patient #TODO

class Exercise(models.Model):
    name = models.CharField(max_length=200 ,null=False, blank=False)
    #Audio = URL
    #Category

class AssignedExercise(models.Model):
    patient = models.ForeignKey(Patient, null=False, blank=False)
    exercise = models.ForeignKey(Exercise, null=False, blank=False)
