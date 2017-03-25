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

Responses:

`bad request` - invalid/null username, email
`unauthorized` - failed to verify github access token
`ok` - ok.


