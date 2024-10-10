FROM openjdk:21
WORKDIR /home/opensell
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} opensell.jar
CMD ["java", "-DACTIVE_PROFILE=prod", "-jar", "opensell.jar"]
EXPOSE 8080