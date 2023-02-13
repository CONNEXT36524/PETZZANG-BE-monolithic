FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
CMD ["./gradlew", "clean", "build"]
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
