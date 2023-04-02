FROM openjdk:17
LABEL authors="leosch"
ADD target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","-DSpringProfilesActive=docker","/app.jar"]