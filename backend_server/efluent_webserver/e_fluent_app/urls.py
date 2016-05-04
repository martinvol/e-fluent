from django.conf.urls import include, url
from rest_framework.authtoken import views


from django.contrib.auth.models import User
from e_fluent_app.models import CustomUser
from rest_framework import routers, serializers, viewsets


# Serializers define the API representation.
class UserSerializer(serializers.HyperlinkedModelSerializer):
    role = serializers.SerializerMethodField()

    class Meta:
        model = CustomUser
        fields = ('url', 'username', 'email', 
            'role'
            )

    def get_role(self, obj):
        return str(obj.get_role())
        #return 'hola'

# ViewSets define the view behavior.
class UserViewSet(viewsets.ModelViewSet):
    queryset = CustomUser.objects.all()
    serializer_class = UserSerializer

# Routers provide an easy way of automatically determining the URL conf.
router = routers.DefaultRouter()
router.register(r'users', UserViewSet)


urlpatterns = [
	url(r'^', include(router.urls)),
    url(r'^api-token-auth/', views.obtain_auth_token)
    #url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework'))
]