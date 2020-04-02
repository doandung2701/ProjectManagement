FROM openjdk:8
VOLUME /tmp
COPY target/project-service.jar /app/project-service.jar
COPY src/main/resources/application.properties /config/application.properties
EXPOSE 8090
# ENTRYPOINT [ "java", "-jar","project-service.jar" ]
CMD java -jar -Dspring.config.location=file:/config/application.properties app/project-service.jar