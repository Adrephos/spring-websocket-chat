# Spring chat backend

This project is a simple chat backend using Spring Boot and WebSockets.

## Running the application

To run the application make sure you have Java 21 installed, gradle and a postgres db deoploy (It can be deployed using [this docker-compose.yml](../docker-compose.yml)).

Make sure to have the database up and running before running the application, you will need to [use this schema](../schema.sql).

To run the application use the following command:

```shell
./gradlew bootRun
```

## Accessing the application

The application will be available at `http://localhost:8080`.

## Accessing the WebSocket

The WebSocket will be available at `ws://localhost:8080/ws-chat`.

You will need to use a JWt token to authenticate the user, it is given through query parameter `token` and it is verified before the handshake.

## API
- Authentication is done using JWT tokens
- Send the token in the Authorization header as `Bearer <token>`

### Endpoints

#### POST /auth/login
- Needs authentication: No
- Description: Authenticates the user and returns a JWT token

##### Request body:

```json
{
  "username": "username",
  "password": "password"
}
```

##### Response body:
- Status code: 200
```json
{
    "token": "token",
    "user": {
        "username": "username",
        "email": "email"
    }
}

```

#### POST /auth/register
- Needs authentication: No
- Description: Registers a new user and returns a JWT token

##### Request body:

```json
{
  "username": "username",
  "password": "password",
  "email": "email"
}
```

##### Response body:
- Status code: 200
```json
{
    "token": "token",
    "user": {
        "username": "username",
        "email": "email"
    }
}
```

#### GET /chats/:username
- Needs authentication: Yes
- Description: Returns all chats for the user

##### Response body:
- Status code: 200
```json
{
    "chats": [
        {
            "id": "uuid-id",
            "firstUsername": "username",
            "secondUsername": "username"
        }
    ]
}
```

#### GET /chats/:chatId/messages
- Needs authentication: Yes
- Description: Returns all messages for the chat

##### Response body:
- Status code: 200
```json
{
    "messages": [
        {
            "id": "uuid-id",
            "content": "content",
            "sentAt": "2022-01-01T00:00:00.000Z",
            "edited": bool | null,
            "editedAt": "2022-01-01T00:00:00.000Z" | null,
            "chatId": "uuid-id",
            "senderUsername": "username",
            "receiverUsername": "username"
        }
    ]
}
```
