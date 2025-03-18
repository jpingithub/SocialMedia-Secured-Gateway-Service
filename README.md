

# Social Media API Documentation

## How to Start the Application

To start the entire application, follow these steps:

### Prerequisites
- Ensure you have Java 17 and Gradle 8.11.1 installed.
- Use your own RabbitMQ message broker properties
- Use your own Mail sending properties as well in the Notification Service
- Set up your environment variables as required for each service.
- Clone the repositories in the specified order.

## Important Notes
- *All API endpoints require the JWT Token to be provided in the request headers as part of authentication and authorization. This is passed via the Authorization header.*
- **Exceptions:** The following endpoints do not require the JWT Token
- `/api/v1/users/validate/otp`– For validating the OTP and verifying an email.
- `/api/v1/users/otp`– For generating an OTP to verify an email.
- `/api/v1/users/register` – For new user registration.
- `/api/v1/auth/login` – For logging in and generating the JWT token.
- `/api/v1/auth/validate` – For validating a JWT token.

### Clone and Start Services in Order

1. **Eureka Server**
   ```sh
   git clone https://github.com/jpingithub/E-Eureka-Server.git
   cd E-Eureka-Server
   ./gradlew bootRun
   ```

2. **User Management Service**
   ```sh
   git clone https://github.com/jpingithub/SocialMedia-User-Management-Service.git
   cd SocialMedia-User-Management-Service
   ./gradlew bootRun
   ```

3. **Auth Service**
   ```sh
   git clone https://github.com/jpingithub/SocialMedia-Auth-Service.git
   cd SocialMedia-Auth-Service
   ./gradlew bootRun
   ```

4. **Post Service**
   ```sh
   git clone https://github.com/jpingithub/SocialMedia-Post-Service.git
   cd SocialMedia-Post-Service
   ./gradlew bootRun
   ```

5. **Follow Service**
   ```sh
   git clone https://github.com/jpingithub/SocialMedia-Follow-Service.git
   cd SocialMedia-Follow-Service
   ./gradlew bootRun
   ```

6. **Like and Comment Service**
   ```sh
   git clone https://github.com/jpingithub/SocialMedia-LikeaAndComment-Service.git
   cd SocialMedia-LikeaAndComment-Service
   ./gradlew bootRun
   ```

7. **Notification Service**
   ```sh
   git clone https://github.com/jpingithub/SocialMedia-Notification-Service.git
   cd SocialMedia-Notification-Service
   ./gradlew bootRun
   ```

8. **API Gateway Service**
   ```sh
   git clone https://github.com/jpingithub/SocialMedia-Secured-Gateway-Service.git
   cd SocialMedia-Secured-Gateway-Service
   ./gradlew bootRun
   ```


## 1. User Management Service

### Base URL: `/api/v1/users`

#### **1.1 Register User**

- **Endpoint:** `POST /register`
- **Description:** Registers a new user.
- **ResponseCode:** 201 Created
- **Request Body:**
  ```json
  {
    "firstName": "String",
    "lastName": "String",
    "username": "String",
    "password": "String",
    "email": "String",
    "profileImageUrl": "String",
    "isEmailVerified": "Boolean"
  }
  ```
- **Response:**
  ```json
  {
    "id": "Integer",
    "firstName": "String",
    "lastName": "String",
    "username": "String",
    "profileImageUrl": "String",
    "email": "String"
  }
  ```

#### **1.2 Get OTP**

- **Endpoint:** `GET /otp?email=`
- **Description:** Generate and send an OTP (One Time Password) to the user's email.
- **ResponseCode:** 200 Ok
- **Response:**
  ```json
  {
    "id": "Integer",
    "otp": "Integer",
    "belongsTo": "String",
    "expiresAt": "Long"
  }
  ```

#### **1.3 Verify OTP**

- **Endpoint:** `POST /validate/otp?email=&otp=`
- **Description:** Validate the OTP for the given email.
- **ResponseCode:** 200 Ok
- **Response:**
  true/false

#### **1.4 Get Current LoggedIn User Details**

- **Endpoint:** `GET /current/details`
- **Description:** Fetch the details of the currently logged-in user.
- **ResponseCode:** 200 Ok
- **Response:**
  ```json
  {
    "id": "Integer",
    "firstName": "String",
    "lastName": "String",
    "username": "String",
    "profileImageUrl": "String",
    "email": "String"
  }
  ```

#### **1.5 Update User**

- **Endpoint:** `PUT /`
- **Description:** Update the profile details of the currently logged-in user.
- **ResponseCode:** 200 Ok
- **Request Body:**
  ```json
  {
    "firstName": "String",
    "lastName": "String",
    "username": "String",
    "password": "String",
    "email": "String",
    "profileImageUrl": "String",
    "isEmailVerified": "Boolean"
  }
  ```
- **Response:**
  ```json
  {
    "id": "Integer",
    "firstName": "String",
    "lastName": "String",
    "username": "String",
    "profileImageUrl": "String",
    "email": "String"
  }
  ```

#### **1.6 Search User**

- **Endpoint:** `GET /search?username=`
- **Description:** Find a user by their username.
- **ResponseCode:** 200 Ok
- **Response:**
  ```json
  {
    "id": "Integer",
    "firstName": "String",
    "lastName": "String",
    "username": "String",
    "profileImageUrl": "String",
    "email": "String"
  }
  ```  

## 2. Auth Service

### Base URL: `/api/v1/auth`

#### **2.1 User Login**

- **Endpoint:** `POST /login`
- **Description:** Authenticates the user and generates a JWT token.
- **ResponseCode:** 200 Ok
- **Request Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response:**
  ```json
  {
    "token": "string"
  }
  ```

#### **2.2 User Logout**

- **Endpoint:** `GET /logout`
- **Description:** Logs out the user by invalidating the provided JWT token.
- **ResponseCode:** 200 Ok
- **Response:**
  ```json
  {
    "message": "string"
  }
  ```

#### **2.3 Validate Token**
- **Endpoint:** `GET /validate?token=`
- **Description:** Validates the provided JWT token.
- **ResponseCode:** 200 Ok
- Response : 
```json
{
  "isValid": "boolean",
  "userName": "String"
}
```

## 3. Post Service

### Base URL: `/api/v1/posts`

#### **3.1 Create a Post**

- **Endpoint:** `POST /`
- **Description:** Creates a new post for the currently logged-in user.
- **ResponseCode:** 200 Ok
- **Request Body:**
  ```json
   {
    "content": "String",
    "imageUrl": "String"
  }
  ```
- **Response:**
  ```json
  {
    "id": "Integer",
    "content": "String",
    "imageUrl": "String",
    "postedBy": "String",
    "createdAt": "String"
  }
  ```

#### **3.2 Get Post by ID**

- **Endpoint:** `GET /{id}`
- **Description:** Retrieves a post by its ID.
- **ResponseCode:** 200 Ok
- **Response:**
  ```json
  {
     "id": "Integer",
    "content": "String",
    "imageUrl": "String",
    "postedBy": "String",
    "createdAt": "String"
  }
  ```

#### **3.3 Get Posts of Currently Logged-in User**

- **Endpoint:** `GET /users`
- **Description:** Retrieves all posts created by the currently logged-in user.
- **ResponseCode:** 200 Ok
- **Response:**
  ```json
  [
    {
      "id": "Integer",
      "content": "String",
      "imageUrl": "String",
      "postedBy": "String",
      "createdAt": "String"
    }
  ]
  ```

#### **3.4 Delete Post by ID**

- **Endpoint:** `DELETE /{id}`
- **Description:** Deletes a post by its ID.
- **ResponseCode:** 204 No Content

#### **3.5 Get Posts of specific User**

- **Endpoint:** `GET /users/{username}`
- **Description:** Retrieves all posts created by a specific user.
- **ResponseCode:** 200 Ok
- **Response:**
  ```json
  [
    {
      "id": "Integer",
      "content": "String",
      "imageUrl": "String",
      "postedBy": "String",
      "createdAt": "String"
    }
  ]
  ```

## 4. Follow Service

### Base URL: `/api/v1/follows`

#### **4.1 Follow a User**

- **Endpoint:** `POST /following-to/{following}`
- **Description:** Allows the currently logged-in user to follow another user. `{following}` The username of the user to follow.
- **ResponseCode:** 201 Created
- `followingId`: Username of the target user to follow.
- `follwerId`:Username of the logged-in user.
- **Response:**
  ```json
  {
    "id": "Integer",
    "followerId": "String",
    "followingId": "String"
  }
  ```

#### **4.2 Unfollow a User**

- **Endpoint:** `DELETE /{followingUser}`
- **Description:** Allows the currently logged-in user to unfollow another user.
- **ResponseCode:** 200 Ok
- **Response:**
  `"Un-follow successful"`

#### **4.3 Get Followers of Current Logged-in User**
- **Endpoint:** `GET /followers`
- **Description:** Retrieves the list of users who are following the currently logged-in user.
- **ResponseCode:** 200 Ok
- **Response:**
```json
[
  {
    "firstName": "String",
    "lastName": "String",
    "username": "String"
  }
]
```

#### **4.4 Get Followers of a Specific User**
- **Endpoint:** `GET /followers/{username}`
- **Description:** Retrieves the list of users who are following the specified user.
- **ResponseCode:** 200 Ok
- **Response:**
```json
[
  {
    "firstName": "String",
    "lastName": "String",
    "username": "String"
  }
]
```

#### **4.5 Get Followings of Current Logged-in User**
- **Endpoint:** `GET /followings`
- **Description:** Retrieves the list of users the currently logged-in user is following.
- **ResponseCode:** 200 Ok
- **Response:**
```json
[
  {
    "firstName": "String",
    "lastName": "String",
    "username": "String"
  }
]
```


#### **4.6 Get Followings of a Specific User**
- **Endpoint:** `GET /followings/{username}`
- **Description:** Retrieves the list of users the specified user is following.
- **ResponseCode:** 200 Ok
- **Response:**
```json
[
  {
    "firstName": "String",
    "lastName": "String",
    "username": "String"
  }
]
```

## 5. Like and Comment Service

### Base URL: `/api/v1/like-comment`

#### **5.1 Like a Post**

- **Endpoint:** `POST /likes`
- **Description:** Allows the currently logged-in user to like a post.
- **ResponseCode:** 200 Ok
- **Request Body:**
  ```json
  {
    "postId": "Integer",
    "type": "String (LIKE | HEART | CLAP | DISLIKE)"
  }
  ```
- **Response:**
  ```json
  {
    "id": "Integer",
    "userId": "String",
    "postId": "Integer",
    "type": "String (LIKE | HEART | CLAP | DISLIKE)",
    "likedAt": "String"
  }
  ```

#### **5.2 Get Likes of a Post**

- **Endpoint:** `GET /likes/{postId}`
- **Description:** Retrieves all likes for the specified post.
  `{postId}` is the ID of the post whose likes are to be retrieved.
- **ResponseCode:** 200 Ok
- **Response:**
  ```json
  [
    {
      "id": "Integer",
      "userId": "String",
      "postId": "Integer",
      "type": "String (LIKE | HEART | CLAP | DISLIKE)",
      "likedAt": "String"
    }
  ]
  ```

#### **5.3 Comment on a Post**

- **Endpoint:** `POST /comments`
- **Description:** Allows the currently logged-in user to comment on a post.
- **ResponseCode:** 200 Ok
- **Request Body:**
  ```json
  {
    "postId": "Integer",
    "comment": "String"
  }
  ```
- **Response:**
  ```json
  {
    "id": "Integer",
    "userId": "String",
    "postId": "Integer",
    "comment": "String",
    "commentedAt": "String"
  }
  ```

#### **5.4 Get Comments of a Post**

- **Endpoint:** `GET /comments/{postId}`
- **Description:** Retrieves all comments for the specified post.
  `{postId}`is the ID of the post whose comments are to be retrieved.
- **Response:**
  ```json
  [
    {
      "id": "Integer",
      "userId": "String",
      "postId": "Integer",
      "comment": "String",
      "commentedAt": "String"
    }
  ]
  ```

