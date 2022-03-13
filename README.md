# Public Transport Application

This repo is built for job seeking. A small demo to represent my Spring and Angular experiences. <br>
I used CircleCi to continuous integration, IntelliJ to develop the backend application, Webstorm to frontend developing. <br>

base repo adress: https://github.com/KarolyFroemel/PublicTransportTicketingApp

# CircleCI status: [![KarolyFroemel](https://circleci.com/gh/KarolyFroemel/PublicTransportTicketingApp.svg?style=svg)](https://circleci.com/gh/KarolyFroemel/PublicTransportTicketingApp)

##Application info
This application has three part <br>
1. Angular frontend
2. Java Spring backend
3. Keycloak server to authorize and authenticate users

##Angular frontend address:
Please read the README and how-to-use.txt files on the angular <br>
repo: frontend_angular <br> 
address: https://github.com/KarolyFroemel/PublicTransportTicketingApp/tree/frontend_angular

##Java Backend address:
repo: master <br>
address: https://github.com/KarolyFroemel/PublicTransportTicketingApp/tree/master <br>
info: <br>
Run it simple spring boot application. <br>
There are two part: <br>
1. Openapi to describe and generate endpoints
2. Rest module which is effect the openapi generated sources <br>

The backend runs on 8081 port (default) <br>
The application uses h2 in memory db. You can reach it in http://localhost:8081/h2-console address <br>
username: test_user, password: password or see the application.properties file in public-transport-rest module


##Keycloak server address:
repo: keycloak <br>
address: https://github.com/KarolyFroemel/PublicTransportTicketingApp/tree/keycloak <br>
Run the keycloak-16.1.0\bin\standalone.bat file. The server runs on http://localhost:8080/auth/. <br>
Username: admin, password: admin

This keycloak server contains the necessary configuration. <br>
The two user to test the application. <br>
1. ADMIN: email: admin1@ptc.com, password: 123456
2. PASSENGER: david.beckham@gmail.com, password: 123456
4. PASSENGER: david.silva@gmail.com, password: 123456
5. PASSENGER: edinson.cavani@gmail.com, password: 123456

