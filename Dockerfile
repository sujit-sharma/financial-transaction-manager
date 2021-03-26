FROM openjdk:15
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} reconcil.jar
ENTRYPOINT ["java", "-jar", "/reconcil.jar"]
