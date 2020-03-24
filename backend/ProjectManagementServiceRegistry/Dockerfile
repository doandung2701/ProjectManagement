FROM openjdk:8
ADD target/service-registry.jar service-registry.jar
EXPOSE 8000
ENTRYPOINT [ "java", "-jar","service-registry.jar" ]