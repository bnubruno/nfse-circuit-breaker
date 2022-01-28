DOCKER_COMPOSE_FILE=docker-compose.development.yml

start:
	docker-compose -f $(DOCKER_COMPOSE_FILE) up
down:
	docker-compose -f $(DOCKER_COMPOSE_FILE) down