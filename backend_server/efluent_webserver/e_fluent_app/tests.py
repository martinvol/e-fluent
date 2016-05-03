from django.test import TestCase

# Create your tests here.

#from django.contrib.auth.models import User
from e_fluent_app.models import Orthophoniste
from django.contrib.auth.models import User


class UsersTestCase(TestCase):
    def setUp(self):
        user = User.objects.create_user("username", "mail@test.com", "password")
        self.orth = Orthophoniste()
        self.orth.user = user
        self.orth.save()

    def test_basic(self):
        """Animals that can speak are correctly identified"""
        pass
        # lion = Animal.objects.get(name="lion")
        # cat = Animal.objects.get(name="cat")
        # self.assertEqual(lion.speak(), 'The lion says "roar"')
        # self.assertEqual(cat.speak(), 'The cat says "meow"')