#!/bin/bash

function checkDockerContainer {
    echo "Checking docker container: ${1}"

    while true; do
        result=$(docker inspect --format='{{.State.Health.Status}}' ${1})
        if [ "$result" == "healthy" ]; then
            break
        fi

        echo "Waiting for docker container to start..."
        sleep 1
    done

    echo "Docker container started successfully!"
}

docker-compose -f ./docker/localstack/docker-compose.yml up -d
checkDockerContainer "localstack-main"

docker-compose -f ./docker/docker-compose.yml up -d
checkDockerContainer "wonders-of-the-world_db"

./mvnw spring-boot:run