FROM openjdk:8
VOLUME /tmp
COPY target/user-service.jar /app/user-service.jar
COPY src/main/resources/application.properties /config/application.properties
EXPOSE 7010
# ENTRYPOINT [ "java", "-jar","user-service.jar" ]
CMD java -jar -Dspring.config.location=file:/config/application.properties app/user-service.jar