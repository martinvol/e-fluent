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
	
    def __str__(self):
        return "Patient"