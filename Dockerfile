FROM openjdk:21
WORKDIR /home/opensell

ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} backend.jar
RUN mkdir -p images/ad-image
RUN mkdir -p images/customer-profile

CMD [ "java", "-DACTIVE_PROFILE=prod", "-jar", "backend.jar" ]
EXPOSE 8080