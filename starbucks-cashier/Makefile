all: clean

clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

run-jar: build
	java -jar target/starbucks-cashier-1.0.jar

# MySQL DB

mysql:
	docker run --platform=linux/amd64 -d --network kong-network --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=welcome mysql:8.0

mysql-shell:
	docker exec -it mysql bash

# Docker

docker-build: build
	docker build --platform=linux/amd64 -t starbucks-cashier .
	docker images

kong-network:
	docker network create --driver bridge kong-network

docker-run: docker-build
	docker run --platform=linux/amd64 --network starbucks -e "MYSQL_HOST=mysql" --name starbucks-cashier -td -p 9090:9090 starbucks-cashier

docker-clean:
	docker stop starbucks-cashier
	docker rm starbucks-cashier
	docker rmi starbucks-cashier

docker-shell:
	docker exec -it starbucks-cashier bash

docker-push:
	docker login
	docker build --platform=linux/amd64 -t $(account)/starbucks-cashier:v1.0 .
	docker push $(account)/starbucks-cashier:v1.0

# Compose

network-ls:
	docker network ls

network-create:
	docker network create --driver bridge $(network)

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale starbucks-cashier=2 -d

lb-up:
	docker-compose up -d lb

starbucks-up:
	docker-compose up -d starbucks

mysql-up:
	docker-compose up -d mysql

compose-down:
	docker-compose down

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost

# Redis DB

redis-local:
	docker run --platform=linux/amd64 --name redis --network kong-network -td -p 6379:6379 redis

redis-official:
	docker run --platform=linux/amd64 --name redis --network kong-network -td -p 6379:6379 redis:4.0

redis-shell:
	docker exec -it redis bash


cashier:
	docker run --network starbucks --name starbucks-nodejs -td -p 90:9090  \
	-e "api_endpoint=http://spring-starbucks-api:8080" \
	--platform=linux/amd64 paulnguyen/starbucks-nodejs