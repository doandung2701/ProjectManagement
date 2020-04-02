# FROM openjdk:8
# VOLUME /tmp
# ADD target/service-registry.jar service-registry.jar
# EXPOSE 8761
# ENTRYPOINT [ "java", "-jar","service-registry.jar" ]
FROM openjdk:8
VOLUME /tmp
COPY target/service-registry.jar /app/service-registry.jar
COPY src/main/resources/application.properties /config/application.properties
EXPOSE 8761
CMD java -jar -Dspring.config.location=file:/config/application.properties app/service-registry.jar
# ENTRYPOINT [ "java", "-jar","","service-registry.jar" ]