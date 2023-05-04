all: clean

#clean:
#	mvn clean
#
#compile:
#	mvn compile
#
#build: compile
#	mvn package

clean:
	gradle clean

compile:
	gradle build

test:
	gradle test

jar:
	gradle build -x test
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/starbucks-api-3.2.jar

run: build
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/starbucks-api-3.2.jar --spring.profiles.active=dev

run-dev: build
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/starbucks-api-3.2.jar --spring.profiles.active=dev

run-test: build
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/starbucks-api-3.2.jar --spring.profiles.active=test

# MySQL DB

mysql:
	docker run -d --network starbucks --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0

mysql-shell:
	docker exec -it mysql bash 

# Docker

docker-build: build
	docker build --platform=linux/amd64 -t spring-starbucks-api .
	docker images

starbucks-network:
	docker network create --driver bridge starbucks

docker-run: docker-build
	docker run --network starbucks -e "MYSQL_HOST=mysql" --name starbucks-api -td -p 8080:8080 spring-starbucks-api

docker-clean:
	docker stop spring-starbucks-api
	docker rm spring-starbucks-api
	docker rmi spring-starbucks-api

docker-shell:
	docker exec -it spring-starbucks-api bash 

docker-push:
	docker login
	docker build -t $(account)/spring-starbucks-api:latest .
	docker push $(account)/spring-starbucks-api:latest
	docker build -t $(account)/spring-starbucks-api:v3.1 .
	docker push $(account)/spring-starbucks-api:v3.1

# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge starbucks-network

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale starbucks=2 -d

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


# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-starbucks-api

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-starbucks-api -- /bin/bash

pod-logs:
	kubectl logs -f spring-starbucks-api

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=spring-starbucks-api

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment spring-starbucks-api-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-starbucks-api -o wide

service-delete:
	kubectl delete service spring-starbucks-api

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress spring-starbucks-api-ingress


# Kong API

kong-db:
	docker run --platform=linux/amd64 -d --name kong-database \
               --network=kong-network \
               -p 5432:5432 \
               -e "POSTGRES_USER=kong" \
               -e "POSTGRES_DB=kong" \
               -e "POSTGRES_PASSWORD=kong" \
               postgres:9.6

kong-setup:
	docker run --platform=linux/amd64 --rm \
     --network=kong-network \
     -e "KONG_DATABASE=postgres" \
     -e "KONG_PG_HOST=kong-database" \
     -e "KONG_PG_USER=kong" \
     -e "KONG_PG_PASSWORD=kong" \
     -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
     kong:2.4.0 kong migrations bootstrap

kong:
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

kong-dbless:
	docker run --platform=linux/amd64 -d --name kong \
    --network=kong-network \
    -e "KONG_DATABASE=off" \
    -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
    -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
    -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
    -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
    -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
    -p 80:8000 \
    -p 443:8443 \
    -p 8001:8001 \
    -p 8444:8444 \
    kong:2.4.0

kong-get-config:
	docker exec -it kong kong config init /home/kong/kong.yml
	docker exec -it kong cat /home/kong/kong.yml >> kong-initial.yml

kong-load-config:
	http :8001/config config=@kong.yml

kong-reload-config:
	docker exec -it kong kong reload

kong-ping:
	http localhost:8080/api/ping apikey:2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ