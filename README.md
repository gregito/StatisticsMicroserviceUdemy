To run in docker container run the following commands from the project's root folder:

Build the image:
```
docker build -t statistics-service .
```

Run the image with all the required environment variable and necessary exposed ports:

(You should set the values replacing the <xy> parts)
```
docker run -d \
-e 'MYSQL_CONTAINER_IP=<your-mysq-server-ip>' \
-e 'MYSQL_DATABASE_USERNAME=<your-mysql-username>' \
-e 'MYSQL_DATABASE_PASSWORD=<your-mysql-password>' \
-e 'TODO_SERVICE_IP=<to-do-service-container-ip>' \
-e 'TODO_SERVICE_PORT=8383' \
-p 8384:8384 \
statistics-service
```

You can find the related to-do-service **[here](https://github.com/gregito/SpringMicroserviceJPA)**.