from django.test import TestCase,Client
# Create your tests here.

from e_fluent_app.models import Orthophoniste, Patient
from django.contrib.auth.models import User
from e_fluent_app.models import CustomUser
from rest_framework.test import APIClient


import json
import codecs

class UsersTestCase(TestCase):
    def setUp(self):
        User.objects.create_user("username", "mail@test.com", "password")
        self.user = CustomUser.objects.get(username="username")

    def test_is_ortho(self):

        self.assertEqual(self.user.get_role(), None)

        self.orth = Orthophoniste()
        self.orth.user = self.user
        self.orth.save()

        self.assertEqual(self.user.get_role().__class__, Orthophoniste)
        
    def test_is_patient(self):

        self.assertEqual(self.user.get_role(), None)

        self.orth = Patient()
        self.orth.user = self.user
        self.orth.save()

        self.assertEqual(self.user.get_role().__class__, Patient)


class Login(TestCase):
    def setUp(self):
        User.objects.create_user("orth1", "mail@test.com", "12345678")
        self.user = CustomUser.objects.get(username="orth1")
        self.orth = Orthophoniste()
        self.orth.user = self.user
        self.orth.save()


    def test_login(self):
        #client = Client(Authorization=)
        #response = self.client.post('/api-token-auth/', {'username':'orth1', 'password':'12345678'})
        

        client = APIClient()
        response = client.post('/API/api-token-auth/', {'username':'orth1', 'password':'12345678'}, format='json')
        self.assertEqual(response.status_code, 200)
        self.assertTrue('token' in response.data)

        token = response.data['token']

        response = client.post('/API/add_patient/', format='json')
        
        self.assertEqual(response.status_code, 401)

        client.credentials(HTTP_AUTHORIZATION='Token ' + token)

        response = client.post('/API/add_patient/', format='json')

        # from now on, the client must be loged in

        self.assertEqual(response.status_code, 400)

        response = client.post('/API/add_patient/', {'id': 1}, format='json')
        self.assertEqual(response.status_code, 400)
        
        self.assertEqual(response.data['id'], 1)

        self.create_patient(1)

        response = client.post('/API/add_patient/', {'id': 1}, format='json')
        self.assertEqual(response.status_code, 200)
        
        self.assertEqual(response.data['id'], 1)        

        self.assertEqual(CustomUser.objects.get(username="orth1").get_role(), 
            CustomUser.objects.get(username="pat1").get_role().orthophoniste)


        response = client.get('/API/patient_list/', format='json')        
        self.assertEqual(response.status_code, 200)
        
        self.assertEqual(len(response.data), 1)

        self.create_patient(2)
        response = client.post('/API/add_patient/', {'id': 2}, format='json')
        self.assertEqual(response.status_code, 200)

        response = client.get('/API/patient_list/', format='json')
        self.assertEqual(len(response.data), 2)
        #TODO Patient tries to add a patientn, unautorized error

    def test_register(self):
        client = APIClient()
        self.assertEqual(Patient.objects.all().count(), 0)
        response = client.post('/API/patient_register/', {'username':'orth11', 'password':'12345678', 'email':"mail@example.com"}, format='json')
        print(response.data)
        self.assertEqual(Patient.objects.all().count(), 1)


    def create_patient(self, id):
        id = str(id)
        User.objects.create_user("pat" +id, "mail@test.com", "12345678")
        user = CustomUser.objects.get(username="pat"+id)
        patient = Patient()
        patient.user = user
        patient.save()
