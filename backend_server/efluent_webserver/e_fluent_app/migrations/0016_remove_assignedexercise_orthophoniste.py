# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2016-05-30 21:13
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('e_fluent_app', '0015_assignedexercise_orthophoniste'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='assignedexercise',
            name='orthophoniste',
        ),
    ]
