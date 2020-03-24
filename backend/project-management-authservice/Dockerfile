FROM openjdk:8
ADD target/auth-service.jar auth-service.jar
EXPOSE 8000
ENTRYPOINT [ "java", "-jar","auth-service.jar" ]