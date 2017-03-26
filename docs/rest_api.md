# API Documentation

Prepend `/a` before everything.

## Auth routes

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

## Konnect routes

All konnect routes start with `/k`.
All of them also REQUIRE the `apikey` query string parameter

POST `/ping` - tell the server you're alive

Request format:

```json
{
    "latitude": 45.15,
    "longitude": 68.12
}
```

Response format:

code:

`bad request` - invalid parameters
`ok` - ok.

GET `/nearby/{dist:double}` - get a json array of nearby people



response format:

code:

`bad request` - you haven't pinged and aren't marked as alive
`ok` - good job.

data:

```json

```