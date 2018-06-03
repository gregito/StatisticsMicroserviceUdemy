To run in docker container run the following commands from the project's root folder:

Build the image:
```
docker build -t statistics-service .
```

Run the image with all the required environment variable and necessary exposed ports:

(You should set the values replacing the < values > parts)
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

You can find the related (and required) to-do-service **[here](https://github.com/gregito/SpringMicroserviceJPA)**.

The required mysql database table should be: **latest_statistics**

To prepare some example data you can insert some with the following SQL:
```
INSERT INTO `latest_statistics` (`ID`, `DESCRIPTION`, `DATE`, `EMAIL`) VALUES
(1, 'You have <b>3 low priority </b> ToDos and <b>2 high priority</b> ToDo.', '2017-11-11', 'some.email@provider.com'),
(2, 'You have <b>4 low priority </b> ToDos and <b>1 high priority</b> ToDo.', '2017-11-12', 'some.email@provider.com'),
(3, 'You have <b>2 low priority</b> ToDos and <b>0 high priority</b> ToDo', '2017-11-20', 'wicked@dummy.eu'),
(4, 'You have <b>0 low priority</b> ToDos and <b>1 high priority</b> ToDo', '2017-12-24', 'otheremail@someotherprovider.com');
```

