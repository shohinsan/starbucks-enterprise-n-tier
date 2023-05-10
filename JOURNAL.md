<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />
  <h3 align="center">Daily Journal on Starbucks Project</h3>
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
    <li><a href="#day-1">Day 1</a></li>
    <li><a href="#day-2">Day 2</a></li>
    <li><a href="#day-3">Day 3</a></li>
    <li><a href="#day-4">Day 4</a></li>
    <li><a href="#day-5">Day 5</a></li>
    <li><a href="#day-6">Day 6</a></li>
    <li><a href="#day-7">Day 7</a></li>
    <li><a href="#day-8">Day 8</a></li>
    <li><a href="#day-9">Day 9</a></li>
    <li><a href="#day-10">Day 10</a></li>
  </ol>
</details>

<!-- Daily Journals -->

## Day 1 

`date` May 2, 2023
<br />
`commit` [708d73d](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/708d73d33d45824df565edc9cf7dafbb289d6de4) 
<br />
`topic` Kong API

### Purpose
Kong is an API gateway that provides a unified entry point for all your APIs and microservices. It manages and secures APIs by providing features such as authentication, rate limiting, request/response transformations, load balancing, and more.

### Challenges
* It was difficult to be able to connect to Kong API, but I found out that I was using the wrong network for my project since it was similar to the previous assignment, lab 8

### Testing
![image](https://user-images.githubusercontent.com/22685770/235778488-7fb2326d-aa98-4f85-9311-ca04ea1a22cb.png)

### Improvements
* Once I fixed the network bug, I was able to connect to the Kong API through httpie and curl as shown below:
![image](https://user-images.githubusercontent.com/22685770/235778725-40b0e19b-a075-4cae-80f0-d1d29056508c.png)
![image](https://user-images.githubusercontent.com/22685770/235778791-0380f48f-d65d-4159-ba8b-5813c43503b2.png)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 2

`date` May 3, 2023
<br />
`commit` [d835760](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/d835760aa3b4b6991272b0418619f6b49fb05212)
<br />
`topic` RabbitMQ Code Implementation and GitHub Actions

### Purpose

I'm willing to make GitHub actions work because I want to reduce manual errors and save time by automating repetitive tasks like code formatting and deployment. Also I've been working on adding the code for RabbitMQ because I want a reliable message broker to handle asynchronous communication between microservices in my distributed system and ensure seamless scalability and fault tolerance and be prepared for my project

### Challenges
* I'm having difficulties to make GitHub Actions work. For some reason, everything is working correct in local using Cloud SQL, but GitHub is refusing to accept that.

![image](https://user-images.githubusercontent.com/22685770/236125178-0634461d-debf-403a-ab66-6f1025c26a61.png)
![image](https://user-images.githubusercontent.com/22685770/236125100-8a1d144a-aae3-4dfa-974f-31fdaf1c86a2.png)

### Testing
![image](https://user-images.githubusercontent.com/22685770/236125070-2afafb79-8b84-4e90-a80e-ca1ab8f5504a.png)

### Improvements
* The only thing I was able to do is to make my RabbitMQ work locally, but that's just a beginning. I'm going to implement RabbitMQ Kubernetes Operator along the way as long as I get it right.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 3

`date` May 4, 2023
<br />
`commit` [bd1ff03](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/bd1ff03623d3e50b9ff4b6ec205fc5a4fbf1c40e)
<br />
`topic` Admin Login and Its Controller

### Purpose

Having an admin or privileged account grants me a source of truth and the ability to see and control anything and everything in my database, therefore I worked hard on implementing this amazing feature into my project

### Challenges
* At first, it was not working properly, but after I went to my scheduled office hours on May 4th.
### Testing


### Improvements
* I fixed the bug by changing the routes in my GetMapping requests. Then It worked perfect and as intended

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 4

`date` May 5, 2023
<br />
`commit`
<br />
`topic` RabbitMQ with Single Pod

### Purpose

Being able to receive and send messages throgh the broker using Google Cloud.

### Challenges
* At the moment, I'm having an issue connecting RabbitMQ to External Load Balancer I created in Google Cloud
### Testing
![Screenshot 2023-05-05 at 11 22 24 PM](https://user-images.githubusercontent.com/22685770/236606096-e9c3d008-a409-4188-a162-6d53c8eb908e.png)

### Improvements
* Other than trying to fix RabbitMQ bug, I only made slight changes in my code for better readability and accessibility

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 5

`date` May 6, 2023
<br />
`commit`
<br />
`topic` Stateless Session

### Purpose

The reason I I removed HashMap from Starbucks Services which was Stateful is because it was storing data in itself.

### Challenges
* I must have an ability to store all the data in the database and also in an event queue (RabbitMQ, still under development)
### Testing
* No tests here

### Improvements
* Changed Get Details of a Starbucks Order and Clear Active Order to be Stateless

![Screenshot 2023-05-06 at 11 34 26 PM](https://user-images.githubusercontent.com/22685770/236661865-b7f29bf1-ab0e-4dfb-823a-3f6ad9d29b34.png)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 6

`date` May 8, 2023
<br />
`commit`
<br />
`topic` Starbucks Cashier to Cloud

### Purpose

To be able to run Starbucks Cashier in cloud 

### Challenges
* I'm having trouble connecting Starbucks Cashier in Cloud because of the pod version, I'll try to fix it another day.
### Testing
* No tests here

### Improvements
* No improvements

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 7

`date` May 8, 2023
<br />
`commit`
<br />
`topic` RabbitMQ Worker

### Purpose

Separated RabbitMQ as suggested in office hours

### Challenges
* Not being able to connect to Starbucks API, it works and breaks some time, so I'm working on that currently in order to fix the issue

![image](https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/24fdb171-1c3d-436c-b857-34bf2a2389f5)

### Improvements
* No improvements

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
