# Web Server and database

Making a login:

```HTTP
POST http://localhost:8000/API/api-token-auth/ username=ortho1 
```

will return:


```json
{
    "token": "4d6e32726e321448fc212242fc03a3fa84c80510"
}
```

then that token should used to make API calls with the token in a HTTP header, like this

```HTTP
Authorization: Token 4d6e32726e321448fc212242fc03a3fa84c80510
```