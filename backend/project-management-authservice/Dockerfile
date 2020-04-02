FROM openjdk:8
VOLUME /tmp
COPY target/auth-service.jar /app/auth-service.jar
COPY src/main/resources/application.properties /config/application.properties
EXPOSE 8010
CMD java -jar -Dspring.config.location=file:/config/application.properties app/auth-service.jar
# ENTRYPOINT [ "java", "-jar","","auth-service.jar" ]