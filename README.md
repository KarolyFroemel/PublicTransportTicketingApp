# Public Transport Application

This repo is part of my CV. A small demo to represent my Spring and Angular skills. <br>
I used CircleCi for continuous integration, IntelliJ to develop the backend application, Webstorm for frontend development. <br>

backend repo address: https://github.com/KarolyFroemel/PublicTransportTicketingApp
frontend repo address: https://github.com/KarolyFroemel/PublicTransportFrontendApp
keycloak repo address: https://github.com/KarolyFroemel/Keycloak

# CircleCI status: [![KarolyFroemel](https://circleci.com/gh/KarolyFroemel/PublicTransportTicketingApp.svg?style=svg)](https://circleci.com/gh/KarolyFroemel/PublicTransportTicketingApp)

##Application info
This application has three parts <br>
1. Angular frontend (based on Maximilian Schwarzmüller Udemy course)
2. Java Spring backend
3. Keycloak server to authorize and authenticate users

##Angular frontend address:
Please read the README and how-to-use.txt files on the Angular project <br>
repo: https://github.com/KarolyFroemel/PublicTransportFrontendApp

##Java Backend address:
repo: master <br>
address: https://github.com/KarolyFroemel/PublicTransportTicketingApp/tree/master <br>
info: <br>
Run it as a simple spring boot application. <br>
There are two parts: <br>
1. Openapi to describe and generate endpoints
2. Rest module which is effect the openapi generated sources <br>

The backend runs on port 8081 (default) <br>
The application uses an h2 in memory db. You can reach it at address http://localhost:8081/h2-console <br>
username: test_user, password: password or see the application.properties file in the public-transport-rest module


##Keycloak server address:
repo: keycloak <br>
address: https://github.com/KarolyFroemel/Keycloak <br>
Run the keycloak-16.1.0\bin\standalone.bat file. The server runs on http://localhost:8080/auth/. <br>
Username: admin, password: admin

This keycloak server contains the necessary configuration. <br>
There are four users to test the application. <br>
1. ADMIN: email: admin1@ptc.com, password: 123456
2. PASSENGER: david.beckham@gmail.com, password: 123456
4. PASSENGER: david.silva@gmail.com, password: 123456
5. PASSENGER: edinson.cavani@gmail.com, password: 123456

