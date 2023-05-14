<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- ABOUT THE PROJECT -->
# Journal

[Journal Link](https://github.com/shohinsan/starbucks-enterprise-n-tier/blob/master/JOURNAL.md)

<!-- ABOUT THE PROJECT -->
# Installation Guide

<!-- PROJECT LOGO -->
<br />
  <h3 align="center">Multi-Tier End-To-End Starbucks Enterprise Solutions</h3>
  
<p align="center">
  <img src="https://user-images.githubusercontent.com/22685770/235343403-e84bb1f3-7153-4971-9972-9704c84ba812.jpg" alt="My Image" width="300" height="auto">
</p>
  <p align="center">
    <br />
    <br />
    <br />
    <a href="#">View Demo</a>
    ·
    <a href="https://github.com/shohinsan/starbucks-enterprise-n-tier/issues">Report Bug</a>
    ·
    <a href="https://github.com/shohinsan/starbucks-enterprise-n-tier/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

<p align="center">
  <img src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/084d78f8-c720-4e94-9939-63c603464383" alt="My Image" width="800" height="auto">
</p>


<!-- [![Product Name Screen Shot][product-screenshot]](https://example.com) -->

This is the comprehensive project for CMPE 172 (Enterprise Software Development) at San Jose State University, for the Spring 2023 semester. This project is a multi-tiered, end-to-end system composed of several elements:

* A web-based application enabling cashiers to oversee their customers' orders (referred to as the Cashier's app)
* A mobile application facilitating payment for customer orders (termed as the Starbucks app)
* A Starbucks API responsible for processing requests coming from both the Cashier's and Starbucks apps
* A database designed to maintain records of orders and cards

## Architecture

<p align="center">
  <img src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/5c612ce6-002f-4400-a0db-f259ebbc77d9" alt="My Image" width="800" height="auto">
</p>


<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![Bootstrap](https://img.shields.io/badge/bootstrap-%23563D7C.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Google Cloud](https://img.shields.io/badge/GoogleCloud-%234285F4.svg?style=for-the-badge&logo=google-cloud&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

* Maven Dependencies
  ```sh
  mvn package
  ```
  
* Postman Import
  - spring-starbucks-kong-collection.json
  - starbucks-kong-environment.json
  
* Docker Desktop
  - MySQL
  - Kong API
  - Starbucks API
  - Starbucks Cashier

* Mobile CLI
  - cd project_folder
  
  ```
  java -cp starbucks-app.jar -Dapiurl="http://localhost:8080" -Dapikey="2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ" -Dregister="5012349" starbucks.Main 2>debug.log
  ```


### Installation

#### starbucks-api 

1. Create Network Bridge 
`docker network create --driver bridge starbucks`
2. Create Kong API Docker Instance
```
docker run --platform=linux/amd64 -d --name kong \
     --network=starbucks \
     -e "KONG_DATABASE=off" \
     -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
     -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
     -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
     -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
     -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
     -p 80:8000 \
     -p 443:8443 \
     -p 127.0.0.1:8001:8001 \
     -p 127.0.0.1:8444:8444 \
     kong:2.4.0
```
3. Create Kong Config File
```
	docker exec -it kong kong config init /home/kong/kong.yml
	docker exec -it kong cat /home/kong/kong.yml >> kong-initial.yml
```
4. Cmd + A , and replace kong.yaml with the following:
```
_format_version: "1.1"

services:
- name: starbucks
  protocol: http
  host: starbucks-api
  port: 8080
  path: /
  plugins:
  - name: key-auth  
  routes:
  - name: api
    paths:
    - /api
consumers:
- username: apiclient
  keyauth_credentials:
  - key: 2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ
```
5. Install HTTPIE on your local machine:
```
brew install httpie
```
6. Create MySQL instance in Docker
```
docker run --platform=linux/amd64 -d --network starbucks --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0
```
7. Create MySQL user
```
login:
- mysql -u root -p
- cmpe172
create:
- create database starbucks;
- create user 'admin'@'%' identified by 'cmpe172'; 
- grant all on starbucks.* to 'admin'@'%';
```
8. Test httpie (Kong supports GET request only)
```
- http GET :8001/status
- http GET :8001/config config=@kong.yaml
- http --ignore-stdin :8001/config config=@kong.yaml
```
9. Reload Kong Config
```
docker exec -it kong kong reload
```
10. Continue testing httpie:
```
- http localhost/api/ping
- http GET localhost/api/ping key:2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ
```
11. Create starbucks-api Instance
```
docker run --platform=linux/amd64 -d --name starbucks-api \
    --network kong-network -td -e env=dev \
    spring-starbucks-api \
    java -jar -Dspring.profiles.active=dev /srv/spring-starbucks-api-3.1.jar
``` 

#### Once you're done with docker, modify MySQL used in localhost to Google's Cloud SQL
1. Cloud SQL command
```
gcloud sql connect mysql8 --user=root --quiet
```
2. Login to MySQL
```
mysql -u private-ip -p -h <INSTANCE_CONNECTION_NAME>:db-name
```

#### starbucks-cashier
1. Create cashier instance
```
docker run --platform=linux/amd64 --network starbucks -e "MYSQL_HOST=mysql" --name starbucks-cashier -td -p 9090:9090 starbucks-cashier
```

#### Docker Desktop
1. Install HTTPIE (everytime to test apikey)
```
apt-get update && apt-get install -y httpie
```
2. Test apikey
```
- http GET localhost:8080/api/ping apikey:2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ
- curl http://localhost:8080/api/ping
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

#### starbucks-app
* Login: login
* Scan: touch (3,3)
* Pay: touch (2,2), => touch (3,3)
* Pay again: => touch (3,3), touch (2,2), => touch (3,3)

<!-- USAGE -->

## Usage

- Run `starbucks-api` sub-project before running `starbucks-cashier`
- Send card request from `starbucks-client` to `starbucks-cashier`

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [x] Docker Demo
    - [x] Show the Starbucks API "Kong API Gateway" endpoint and API being used.  Demo this via a CURL ping test to API.
    - [x] Show the launch manifest / configuration for Starbucks Cash Register confirming connection to API via Kong with API Key
    - [x] Start up and Log into your Starbucks Cash Register App (your ported Spring MVC version)
    - [x] Show the launch command to run Starbucks "Mobile" App with connection to the same Kong API Gateway and API Key
    - [x] Start up the "Starbucks Mobile App" pointing out the "Store/Register" ID being used
    - [x] Connect to Backend MySQL Database and query the "New Starbucks Card" created by Starbucks Mobile App
    - [x] Query to show the Card Number, Card Code and starting Balance in "Activated Status"

- [x] Google Cloud Demo
    - [x] Support Admin Logins for Starbucks Employees
    - [x] Cashiers App Deployed and running on GKE PODS
    - [x] Supports Order Selection - Dring Type, Size, Milk Option
    - [x] Rendering must use MVC by processing REST API calls from Starbucks API via Kong Gateway on GCP
    - [x] Order Created via REST API call to Kong Endpoint with API key running on GCP
    - [x] Order Payment Reflected on UI with Updated Balance Paid by Mobile App
    - [x] External IP of Load Balancer used for Demo/Screnshots of Cashier's App
    - [x] Evidence of Load Balancer with Heahtly Backends on GCP / GKE
    - [x] Evidence of Healthy Running Starbucks API Pods in GKE
    - [x] Evidence of Reachability Tests via Ping API call with Kong API key to Service Endpoint
    - [x] Evidence of Running MySQL Instance on Cloud SQL
    - [x] Evidence that MySQL IP is used by Cashier's App to Persist Order Data
    - [x] Evidence that Data Stored in MySQL DB cooresponds to Data visible on Web UI
    - [x] Evidence that RabbitMQ is Deployed in GKE or GCP and used in Project via changes to Spring Source Code
    - [x] Evidence via Demo/Screenshots that the Functionality Works
    - [x] Moible App should be connected to Starbucks API via Kong using API key
    - [x] Cashier's App and other Apps in Demo must connect to Starbucks API via Kong using API Key
    - [x] Source code for Apps shows evidence that REST API requests are using API Key Auth

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Use this space to list resources you find helpful and would like to give credit to. I've included a few of my favorites to kick things off!

[Official Kong Documentation](https://docs.konghq.com/)
[Kong GitHub Repository](https://github.com/Kong/kong)
[Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
[Spring Boot GitHub Repository](https://github.com/spring-projects/spring-boot)
[Google Cloud SQL Documentation](https://cloud.google.com/sql/docs)
[Google Cloud SQL GitHub Repository](https://github.com/GoogleCloudPlatform/google-cloud-java/tree/master/google-cloud-clients/google-cloud-sql)
[Oracle Java Documentation](https://docs.oracle.com/en/java/)
[OpenJDK GitHub Repository](https://github.com/openjdk/jdk)
[Gradle Documentation](https://docs.gradle.org)
[Gradle GitHub Repository](https://github.com/gradle/gradle)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/shohinsan/readme.svg?style=for-the-badge
[contributors-url]: https://github.com/shohinsan/starbucks-enterprise-n-tier/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/shohinsan/readme.svg?style=for-the-badge
[forks-url]: https://github.com/shohinsan/starbucks-enterprise-n-tier/forks
[stars-shield]: https://img.shields.io/github/stars/shohinsan/readme.svg?style=for-the-badge
[stars-url]: https://github.com/shohinsan/starbucks-enterprise-n-tier/stargazers
[issues-shield]: https://img.shields.io/github/issues/shohinsan/readme.svg?style=for-the-badge
[issues-url]: https://github.com/shohinsan/starbucks-enterprise-n-tier/issues
[license-shield]: https://img.shields.io/github/license/shohinsan/readme.svg?style=for-the-badge
[license-url]: https://github.com/shohinsan/starbucks-enterprise-n-tier/blob/master/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/shohin-abdulkhamidov/
[product-screenshot]: ![starbucks](https://user-images.githubusercontent.com/22685770/235320606-cf6e5174-26f1-4a2e-97ec-a20b4daabc28.jpg)
