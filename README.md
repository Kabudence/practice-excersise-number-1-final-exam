# ACME Learning Center Platform

## Summary
-Josue Hidalgo-Kabudence
## Features
- RESTFULL API
- OpenAPI Documentation
- Swagger UI
- Spring Boot Framework
- Spring Data JPA
- Validation
- MySQL Database
- Domain-Driven Design

## Bounded Contexts
This version of ACME Learning Center Platform is divided into three bounded contexts: Profiles, Learning, and Identity and Access Management (IAM).

### Profiles Context

The Profiles Context is responsible for managing the profiles of the users. It includes the following features:

- Create a new profile.
- Get a profile by id.
- Get all profiles.

This context includes also an anti-corruption layer to communicate with the Learning Context. The anti-corruption layer is responsible for managing the communication between the Profiles Context and the Learning Context. It offers the following capabilities to other bounded contexts:
- Create a new Profile, returning ID of the created Profile on success.
- Get a Profile by Email, returning the associated Profile ID on success.

### Learning Context

The Learning Context is responsible for managing the courses, course learning paths and course enrollments. Its features include:

- Create a Course.
- Get a Course by id.
- Update a Course information.
- Delete a Course.
- Get all Courses.
- Add an existing Tutorial to Course Learning Path.
- Register a new Student with implicit profile creation.
- Submit a Student Enrollment Request in a Course.
- Cancel a Student Enrollment Request in a Course.
- Confirm a Student Enrollment Request in a Course.
- Reject a Student Enrollment Request in a Course.
- Get all Enrollments for a Course.

This context includes also an anti-corruption layer to communicate with the Profiles Context. The anti-corruption layer is responsible for managing the communication between the Learning Context and the Profiles Context. It consumes the capabilities offered by the Profiles Context to:

- Create the Profile of a new Student.
- Get the Profile ID of a Student by Email.

Tutorial is a concept that represents a learning resource. It is used to build the learning path of a course. The Tutorial aggregate and its publishing lifecycle are part of the Publishing bounded context, which is beyond the scope of this platform version.

### Identity and Access Management (IAM) Context

The IAM Context is responsible for managing platform users, including the sign in and sign up processes. It applies JSON Web Token based authorization and Password hashing. It also adds a request authorization middleware to Spring Boot Pipeline, in order to validate included token in request header on endpoints that require authorization. Its capabilities include:
- Create a new User (Sign Up).
- Authenticate a User (Sign In).
- Get a User by ID.
- Get All Users.
- Get All Roles.
- Use Spring Security features to implement an authorization pipeline based on request filtering.
- Generate and validate JSON Web Tokens.
- Apply Password hashing.

This version implements the following roles: Admin, Instructor, and User. The roles are used to manage the access to the platform features. The Admin role has access to all features, the Instructor and Student roles should have access according to business rules.

This context includes also an anti-corruption layer. The anti-corruption layer is responsible for managing the communication between the IAM Context and other bounded Contexts. Its capabilities include:

- Create a new User, returning ID of the created User on success. If roles are provided, it will also assign them to the user, otherwise the default role is assigned.
- Get a User by ID, returning the associated User ID on success.
- Get a User by Username, returning the associated User ID on success.

- In this version, Open API documentation includes support for JSON Web Token based authorization.

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#using.devtools)
* [Validation](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#io.validation)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#web)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

