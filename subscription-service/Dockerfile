FROM adoptopenjdk/openjdk11
EXPOSE 8082
ARG JAR_FILE=build/libs/subscription-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} subscription.jar
ENTRYPOINT ["java", "-jar","/subscription.jar"]