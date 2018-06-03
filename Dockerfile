FROM openjdk:8
COPY ./target/StatisticsMicroservice.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "StatisticsMicroservice.jar"]