FROM openjdk:8
ADD target/gateway-service.jar gateway-service.jar
ADD src/main/resources/application.yml application.yml
EXPOSE 8000
ENTRYPOINT [ "java", "-jar","-Dspring.config.location=application.yml","gateway-service.jar" ]