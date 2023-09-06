FROM adoptopenjdk/openjdk11
EXPOSE 8081
ARG JAR_FILE=build/libs/user-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} user.jar
ENTRYPOINT ["java", "-jar","/user.jar"]