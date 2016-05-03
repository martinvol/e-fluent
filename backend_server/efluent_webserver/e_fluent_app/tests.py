from django.test import TestCase

# Create your tests here.

from e_fluent_app.models import Orthophoniste, Patient
from django.contrib.auth.models import User
from e_fluent_app.models import CustomUser


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