# API Documentation

POST `/register` - Register a user

Request format:

```json
{
    "fullname": "Foo Bar",
    "hangoutsEmail": "foo.bar@gmail.com",
    "ghAuthToken": "1234"
}
```

Response:

codes:

`bad request` - invalid/null username, email
`unauthorized` - failed to verify github access token
`ok` - ok.

data:

json containing the user data

```json
{
    "FullName": "Sum Body",
    "HangoutsEmail": "sum@body.com",
    "GHAuthToken": "123",
    "ApiKey": "9812yy4809y3yq09wgo8as7df"
}
```


