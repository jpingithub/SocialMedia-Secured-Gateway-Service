

# Social Media API Documentation

## How to Start the Application

To start the entire application, follow these steps:

### Prerequisites
- Ensure you have Java 17 and Gradle 8.11.1 installed.
- Use your own RabbitMQ message broker properties
- Use your own Mail sending properties as well in the Notification Service
- Set up your environment variables as required for each service.
- Clone the repositories in the specified order.

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

### Base URL: `/user-management`

#### **1.1 Register User**

- **Endpoint:** `POST /register`
- **Description:** Registers a new user.
- **Request Body:**
  ```json
  {
    "username": "string",
    "email": "string",
    "password": "string"
  }
  ```
- **Response:**
  ```json
  {
    "message": "User registered successfully"
  }
  ```

#### **1.2 Get User Profile**

- **Endpoint:** `GET /profile/{userId}`
- **Description:** Retrieves the profile of a specific user.
- **Response:**
  ```json
  {
    "userId": "int",
    "username": "string",
    "email": "string"
  }
  ```

## 2. Auth Service

### Base URL: `/auth`

#### **2.1 Login**

- **Endpoint:** `POST /login`
- **Description:** Authenticates a user and returns a JWT token.
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

#### **2.2 Logout**

- **Endpoint:** `POST /logout`
- **Description:** Logs out a user by invalidating the JWT token.
- **Response:**
  ```json
  {
    "message": "User logged out successfully"
  }
  ```

## 3. Post Service

### Base URL: `/posts`

#### **3.1 Create Post**

- **Endpoint:** `POST /create`
- **Description:** Creates a new post.
- **Request Body:**
  ```json
  {
    "userId": "string",
    "content": "string"
  }
  ```
- **Response:**
  ```json
  {
    "postId": "int",
    "message": "Post created successfully"
  }
  ```

#### **3.2 Get Posts**

- **Endpoint:** `GET /user/{userId}`
- **Description:** Retrieves posts for a specific user.
- **Response:**
  ```json
  [
    {
      "postId": "int",
      "content": "string",
      "createdAt": "string"
    }
  ]
  ```

#### **3.3 Delete Post**

- **Endpoint:** `DELETE /delete/{postId}`
- **Description:** Deletes a specific post by ID.
- **Response:**
  ```json
  {
    "message": "Post deleted successfully"
  }
  ```

## 4. Follow Service

### Base URL: `/follow`

#### **4.1 Follow a User**

- **Endpoint:** `POST /follow`
- **Description:** Follow another user.
- **Request Body:**
  ```json
  {
    "followerId": "string",
    "followingId": "string"
  }
  ```
- **Response:**
  ```json
  {
    "message": "User followed successfully"
  }
  ```

#### **4.2 Unfollow a User**

- **Endpoint:** `POST /unfollow`
- **Description:** Unfollow a user.
- **Request Body:**
  ```json
  {
    "followerId": "string",
    "followingId": "string"
  }
  ```
- **Response:**
  ```json
  {
    "message": "User unfollowed successfully"
  }
  ```

## 5. Like and Comment Service

### Base URL: `/like-comment`

#### **5.1 Like a Post**

- **Endpoint:** `POST /like`
- **Description:** Like a post.
- **Request Body:**
  ```json
  {
    "userId": "string",
    "postId": "int"
  }
  ```
- **Response:**
  ```json
  {
    "message": "Post liked successfully"
  }
  ```

#### **5.2 Unlike a Post**

- **Endpoint:** `POST /unlike`
- **Description:** Unlike a post.
- **Request Body:**
  ```json
  {
    "userId": "string",
    "postId": "int"
  }
  ```
- **Response:**
  ```json
  {
    "message": "Post unliked successfully"
  }
  ```

#### **5.3 Comment on a Post**

- **Endpoint:** `POST /comment`
- **Description:** Add a comment to a post.
- **Request Body:**
  ```json
  {
    "userId": "string",
    "postId": "int",
    "comment": "string"
  }
  ```
- **Response:**
  ```json
  {
    "commentId": "int",
    "message": "Comment added successfully"
  }
  ```

#### **5.4 Delete Comment**

- **Endpoint:** `DELETE /comment/{commentId}`
- **Description:** Delete a specific comment by ID.
- **Response:**
  ```json
  {
    "message": "Comment deleted successfully"
  }
  ```

