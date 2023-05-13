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

The Cashier app and particularly the Starbucks API are designed for scalability, supported by multiple pods. They are capable of managing millions of user requests, and the load balancer assists in distributing these requests across the various pods - an external load balancer for the cashiers, and Kong's internal load balancer for all requests directed towards the Starbucks API.

However, there is a constraint regarding the number of active orders. Only one active order per register can be processed at a given time. Consequently, any request to place a new order in the register will be denied and remain unprocessed. This limitation isn't influenced by the number of pods, as all API pods draw data exclusively from the database and don't contain static data (following the removal of the activeOrders hashmap and subsequent code update).

This system could be enhanced by incorporating RabbitMQ. For example, we could initially send order placement requests to the RabbitMQ queue, where orders would be held pending execution. Then, the API would retrieve the next order from the queue for the register and set it as active, continuing this process until the queue is empty. Regrettably, due to not just time restrictions, but also being able to dequeue from the database, I wasn't able to implement RabbitMQ. Though, I gave it a try and uploaded RabbitMQ in GKE, which works as intended. 

The rest of the necessary technology stack was utilized appropriately for the project, and the accompanying journal provides further details on the project's construction.

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

* It was difficult to be able to connect to Kong API, I had to first work on figuring out the configuration. I had problems using the correct endpoints and ports for the API to connect to my application, after figuring out that, I had problems with my get and post requests. POSTMAN was useful in configuring the parameters such as the correct headers to use in the body of the requests, but with practice and online resources I was able to test my requests against the API.

### Testing

![7  Jumpbox Http](https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/055c6ab6-0201-449b-a30c-273373ef23b1)
![9  Kong Curl 2](https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/4dd860f0-c9e8-4073-8499-2f655abc68c0)
![13  Final http Result](https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/89b12cd2-7c99-408d-8cfd-e45a43a32be8)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 2

`date` May 3, 2023
<br />
`commit` [d835760](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/d835760aa3b4b6991272b0418619f6b49fb05212)
<br />
`topic` RabbitMQ and GitHub Actions

### Purpose

I wanted to use RabbitMQ because it provides a resilient messaging queue system that aids in asynchronous communication within a software system. It allows me to decouple applications, enabling them to dispatch and receive data without needing immediate processing. This improves the responsiveness and scalability of my applications, by effectively managing data flow and preventing system overload.

Also, another thing I attemted to implement, but again, failed, is to utilize GitHub Actions as it offers a powerful solution for both Continuous Integration (CI) and Continuous Deployment (CD), streamlining the process of code updates and software releases. By automatically building, testing, and deploying applications whenever there's a change to the codebase, it reduces my manual workload. This leads to more consistent and frequent deployments, expediting product development, and enhancing software quality.

### Challenges

* Implementing both RabbitMQ and GitHub Actions posed distinct challenges. With RabbitMQ, the complexity arose from integrating it into the existing system architecture and ensuring its robust performance under high loads, whereas with GitHub Actions, setting up an efficient CI/CD pipeline and troubleshooting failed builds and deployments presented significant hurdles.

### Testing

<img width="1326" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/5029bc00-581d-4d8a-8ad7-4a3b9fdbbbf0">
![image](https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/184747c2-7245-4586-a780-7f967875219f)
<img width="2550" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/608dd6e4-9a29-40a0-a792-0baa48645980">

Therefore, I wasn't been able to accomplish these task while workin on my final project.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 3

`date` May 4, 2023
<br />
`commit` [bd1ff03](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/bd1ff03623d3e50b9ff4b6ec205fc5a4fbf1c40e)
<br />
`topic` Admin Functionality

### Purpose

Admin functionality serves the crucial role of defining security configurations, such as authentication and authorization rules. It ensures that appropriate access controls are in place, thereby safeguarding sensitive data and functionalities. The admin login, specifically, is an essential part of this security framework, providing a secure way for administrators to access privileged functions and manage the application effectively.

### Challenges

Implementing Spring Security alongside a load balancer in Google Kubernetes Engine (GKE) presented notable challenges. Configuring both to work harmoniously, while maintaining secure user sessions across multiple pods, was complex. Furthermore, ensuring that the load balancer correctly distributed traffic without compromising the security protocols established by Spring Security added to the difficulties. Additionally, managing the routing of requests accurately to ensure that they reached the appropriate services, while maintaining session continuity in a load-balanced environment, also presented a significant challenge.

### Testing

<img width="1718" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/691eafd2-f7b3-4a3e-b501-458deb6adaca">
<img width="1726" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/71998b58-b53a-4e0a-913b-538a7458611d">

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 4

`date` May 5, 2023
<br />
`commit` [f317f59](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/f317f59285ad8c083781b81de3c39a96a4ce0160)
<br />
`topic` Cloud SQL

### Purpose

I wanted to leverage Google Cloud SQL because it provides a fully-managed relational database service for MySQL, PostgreSQL, and SQL Server. It handles the mundane tasks of database management, such as backups, patch management, and failover, allowing me to focus on developing applications. Moreover, its scalability, high availability, and security features make it an excellent choice for handling my application's data needs.

### Challenges

* Implementing Google Cloud SQL posed a few challenges, the most significant being fine-tuning the database for optimal performance while managing costs. Additionally, ensuring secure connections between the application and the database, as well as configuring the right access privileges without compromising security, were other complex aspects to navigate.

### Testing

<img width="2553" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/4416c839-011b-4bed-bff6-b9bb68b125b3">

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 5

`date` May 6, 2023
<br />
`commit` [f9ecad7](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/f9ecad7fdf77d90c1795750683cace694eb6841d)
<br />
`topic` Stateless Session

### Purpose

Before deploying the Starbucks API code, I confirmed that the API is at least stateless, meaning it doesn't retain any data internally, such as arraylists or hashmaps. Instead, it should store all data within the database and an event queue, such as RabbitMQ. As part of your tasks, you'll need to elucidate the design and implementation, relating it back to the business logic. You'll also need to discuss the capabilities and constraints of your API in conjunction with all other components.

### Challenges

* Implementing stateless functionality to shift from using a hashmap to a database connection presented several challenges. The primary issue was managing the transition to a completely different data structure, which required significant code changes and retesting. Additionally, ensuring efficient and reliable database connections for every request, while maintaining high performance and scalability, was a complex task.

### Testing

<img width="2550" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/e0a85431-0807-4510-8bfe-ec07092fb41e">

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 6

`date` May 8, 2023
<br />
`commit` [a3dd2fa](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/a3dd2fa9b2712b5d39ab8ec41a7434102e87007e)
<br />
`topic` Office Hours

### Purpose

I needed office hours where I had to meet with the professor to ask questions, seek clarification, and receive individualized support. They provided an opportunity to deepen my understanding of the course material, discuss assignments, and receive guidance, ultimately enhancing my learning experience.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 7

`date` May 8, 2023
<br />
`commit` [24eb896](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/24eb896aa72df93d04c6913e4d758777015b5e34)
<br />
`topic` Postman Testing

### Purpose

I also used Postman to interact with the Kong API. By leveraging the environment variables I've set, I can seamlessly test and validate various API endpoints, ensuring their functionality and performance. Postman enables me to send requests, receive responses, and analyze the API's behavior, ultimately helping me ensure the reliability and effectiveness of my Kong API implementation.

### Challenges
 
Firstly, managing the environment variables can be complex, especially when dealing with different environments or configurations. Secondly, understanding the intricacies of the Kong API and its specific authentication methods requires additional research and knowledge. Lastly, accurately simulating various scenarios and handling edge cases during testing can be time-consuming and require meticulous attention to detail. Eventually, I changed current variables to meet Kong API requirements 

<img width="2543" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/9305f2e0-6d85-4c7c-918a-8d0014f2c3d4">

### Testing

<img width="2550" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/46731f64-198e-4a86-a08e-d45cc9b5e6e6">
<img width="2546" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/208a2471-4416-4dff-b891-9edb4bf07f51">
<img width="2544" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/18c66cea-17ef-45df-82c3-0b8f61c0f840">
<img width="2541" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/28ced06d-72d5-4130-b2b5-681de170c99b">
<img width="2546" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/2db3efdc-00b2-450f-ba6a-5e724a13a242">
<img width="2551" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/04f35b50-5e2e-498b-95de-267bd15d8835">
<img width="2552" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/bc5f684f-91f3-4f43-988b-36f3c8dab639">
<img width="2553" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/ed25f40f-f111-4bb9-8c18-105b66e33fa6">

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 8

`date` May 10, 2023
<br />
`commit` [9b345fc](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/9b345fc363bf6696bf730a1c3fdeb8acfe0a3752)
<br />
`topic` Deployment Testing

### Purpose

Scalable platform for running containerized applications offered service discovery and load balancing, which helped in managing my applications more efficiently. Moreover, its ability to scale applications based on traffic or custom metrics ensures optimal resource utilization and application performance.

### Challenges

Main challenges I have faced were:
* Configuration Errors: Pulling from Docker Hub in my deployment yaml with wrong tag number
* Networking Issues: I had to google "my ip address" and paste it in SQL networking to be able to access

### Testing

![image](https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/5aa3283c-d9a6-45dd-9880-10bd3ddcc186)
<img width="2548" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/b078a2a8-b574-497e-849b-8491ee91e7b7">

### Improvements

<img width="2542" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/ef9ac0b4-3e1f-4a4e-aa35-45fc958a53b6">
<img width="2541" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/3898b783-b2da-4672-bf2a-d97630fe700c">
<img width="2554" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/fba9be48-3035-48fc-8e3b-f71062af4a86">
<img width="2549" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/180314cf-7af9-4c59-b875-ee18441a2582">

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Day 9

`date` May 11, 2023
<br />
`commit` [9715e2f](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/9715e2f0cc67d6a5ec17fd8eed11980aa9e28f06)
<br />
`topic` Starbucks Cashier

### Purpose

Cashier Web App as it serves as a user-friendly platform for cashiers to manage customer orders efficiently. It provides an intuitive interface for order input, modification, and tracking, streamlining the cashiers' workflow. Additionally, its integration with the backend system and the Starbucks API ensures seamless, real-time updates and coordination with the customer-facing Starbucks app.

### Challenges

* Switching the Cashier Web App from localhost to the Kong API on Google Kubernetes Engine (GKE) posed several challenges. The major hurdle was ensuring a smooth transition and maintaining seamless communication between the web app and the API in the new environment. Additionally, dealing with potential networking and security issues related to the shift to a cloud-based solution was another complex aspect of the transition. Implementing the feature to select a drink, milk, and size in the Cashier Web App also presented its own set of challenges. Designing and integrating these additional user interfaces while ensuring they correctly interact with the backend system and provide a smooth user experience was a complex task.

### Testing

<img width="2554" alt="image" src="https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/0bf89806-5aba-4fea-a340-57a9627e4766">

## Day 10

`date` May 13, 2023
<br />
`commit` [f9de2a9](https://github.com/shohinsan/starbucks-enterprise-n-tier/commit/f9de2a93df60a806a66fb208174d6df65d511868)
<br />
`topic` Final Testing

### Testing

![image](https://github.com/shohinsan/starbucks-enterprise-n-tier/assets/22685770/0ff65bb4-4bb5-4745-974e-34ae4fa08ff2)

That concludes the project! I've opted not to include screenshots of every individual Postman request, as they've been functioning flawlessly. I take great pride in the work I've accomplished, and if you're interested in a brief demonstration of the project, you can find it at the beginning of the readme.

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
