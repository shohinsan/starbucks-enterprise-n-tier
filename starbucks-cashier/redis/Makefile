clean: docker-build
	
docker-clean:
	docker stop redis
	docker rm redis
	docker rmi redis

docker-build: 
	docker build --platform=linux/amd64 -t redis .
	docker images

# Redis DB

redis-local:
	docker run --platform=linux/amd64 --name redis --network kong-network -td -p 6379:6379 redis

redis-official:
	docker run --platform=linux/amd64 --name redis --network kong-network -td -p 6379:6379 redis:4.0

redis-shell:
	docker exec -it redis bash


