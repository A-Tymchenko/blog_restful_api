# blog_restful_api

Technologies: Java 8, Spring (Boot, JPA, Security) MySQL, Hibernate.

REST resources to implement:

    1. Resource to authenticate a particular user. Users may be hardcoded.
    
    2. Create, update, delete own blog posts.
    
    3. Get the list of all blog posts. Filter option to get only own posts should be present.
    
    4. Get a particular blog post by id.
    
How to Run Application

Open application.yml file inside blog_restful_api/src/main/resources/ directory.

Fill up params in accordance to you local DB, e.g:

datasource:

    url: jdbc:mysql://<your-mysql-host-name>:3306/<your-db-name>
    
    username: <your name>
    
    password: <your password>
    
Run ApplicationRunner.java class

-------------------------------------------------

Registration request example:

Url: http://localhost:8080/api/auth/signup

Method: POST

{
   "username":"test user",
   
   "email":"test@gmailf.comg",
   
   "password":"3edcv543fr4456"
}

-------------------------------------------------

Sign in request example:

Url: http://localhost:8080/api/auth/signin

Method: POST

{
   "email":"test@gmailf.comg",
   
   "password":"3edcv543fr4456"
}

-------------------------------------------------

Create Post request example:

Url: http://localhost:8080/api/posts/create

Method: POST

{
	"title": "test title",
    
	"body": "test test test test test"
}

-------------------------------------------------

Update Post example:

Url: http://localhost:8080/api/posts/update/{id}

Method: PUT

{
	"title": " update post post post",
    
	"body": "quiarto"
}

-------------------------------------------------

Get Post by Id example:

Url: http://localhost:8080/api/posts/get/{id}

Method: GET

-------------------------------------------------

Get All Posts example:

Url: http://localhost:8080/api/posts

Method: GET

-------------------------------------------------

Get All Posts By Current User

Url: http://localhost:8080/api/posts/publisher

Method: GET

-------------------------------------------------

Delete Post

Url: http://localhost:8080/api/posts/delete/{id}

Method: DELETE
