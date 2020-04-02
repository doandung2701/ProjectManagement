FROM openjdk:8
VOLUME /tmp
COPY target/task-service.jar /app/task-service.jar
COPY src/main/resources/application.properties /config/application.properties
EXPOSE 8020
# ENTRYPOINT [ "java", "-jar","task-service.jar" ]
CMD java -jar -Dspring.config.location=file:/config/application.properties app/task-service.jar