FROM adoptopenjdk/openjdk11
EXPOSE 8082
ARG JAR_FILE=build/libs/community-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} community.jar
ENTRYPOINT ["java", "-jar","/community.jar"]