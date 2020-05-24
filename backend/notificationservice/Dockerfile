FROM openjdk:8
VOLUME /tmp
COPY target/notification-service.jar /app/notification-service.jar
COPY src/main/resources/application.properties /config/application.properties
EXPOSE 9090
# ENTRYPOINT [ "java", "-jar","project-service.jar" ]
CMD java -jar -Dspring.config.location=file:/config/application.properties app/notification-service.jar