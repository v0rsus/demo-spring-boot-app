# Drone application

## Tools
Java 11 and Gradle

## Config
The application can be configured with ‘resources/application.properties‘ file.

## Database
The application has an embedded H2 in memory database inside. The console is available via 
http://localhost:9080/app/h2-console with credential sa/password.
The databased initialised when the application started with the DDL ‘resources/schema.sql‘ and DML ‘resources/data.sql‘.

## HTTP
There is postman collection ‘http.json‘ for testing the application.
