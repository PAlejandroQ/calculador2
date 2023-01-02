FROM openjdk:20-ea-17-jdk
COPY build/libs/calculador-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
