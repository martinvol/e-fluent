from django.contrib import admin

# Register your models here.

from .models import *

admin.site.register(Orthophoniste)
admin.site.register(Patient)