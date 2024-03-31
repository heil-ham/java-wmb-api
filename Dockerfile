FROM openjdk:17-jdk-alpine
COPY target/wmbspring-0.0.1-SNAPSHOT.jar wmbspring-1.0.0.jar
ENTRYPOINT ["java","-jar","/wmbspring-1.0.0.jar"]