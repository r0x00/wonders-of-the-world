# Wonders Of The World

## Description

This is a small project for me to learn more about spring boot, spring cloud, sqs and dlq. The ideia here is to simulate a ecommerce, where user could buy a product. 

## Dependencies

This project uses [localstack](https://app.localstack.cloud/sign-in) to simulate AWS enviroment (good this exist, if not I would be poor in the end of the project), so you will need to have an account to get an apiKey (you dont need to pay to create an account).

After you copy your apiKey, please run this command in the terminal: 

```
export LOCALSTACK_AUTH_TOKEN=${apiKey}
```

we use this variable to configure the container of localstack. you can check this container in this file:  **./docker/localstack/docker-compose.yml**

## Start project

To start the application you will only need to run the script **./scripts/development.sh**, it will start the postgree db and the localstack container. Run the command bellow to start the script:

```
./scripts/development.sh
```

## Postman

This project have a psotman with all the api routes. To use it please install [posmtan](https://www.postman.com/downloads/) after installing and opening it, you'll need to import the collection that is in the **postman/** folder with name **Wonders of The World.postman_collection**. There you will find users, hotel and reservation endpoints.