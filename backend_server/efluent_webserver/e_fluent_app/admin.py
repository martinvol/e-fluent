from django.contrib import admin

# Register your models here.

from .models import *

admin.site.register(Orthophoniste)
admin.site.register(Patient)
admin.site.register(Exercise)
admin.site.register(AssignedExercise)
admin.site.register(Meeting)