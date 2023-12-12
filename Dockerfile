FROM openjdk:23-jdk
EXPOSE 8081
ADD target/IntegrationTesting-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]