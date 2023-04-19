FROM openjdk:17
ENV NETWORK_NAME=ShoppingApp
COPY target/Shopping-App-Back-End-0.0.1-SNAPSHOT.jar Shopping-App-Back-End-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Shopping-App-Back-End-0.0.1-SNAPSHOT.jar"]