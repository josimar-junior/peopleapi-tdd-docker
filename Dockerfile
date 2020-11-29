FROM openjdk:11.0.7-slim
LABEL maintainer="juniorpaz99@gmail.com"

ENV LANG C.UTF-8

ADD target/peopleapi.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "-Dkeycloak.auth-server-url=http://keycloak:8080/auth", "/app/app.jar"]
