FROM eclipse-temurin:21
WORKDIR /home/opensell
COPY ./src/main/resources/images ./images
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} opensell.jar
COPY keystore.p12 keystore.p12
CMD ["java", "-DACTIVE_PROFILE=prod", "-DAPP_IMAGE_SERVER_PATH=/home/opensell/images", "-jar", "opensell.jar"]
EXPOSE 8080