FROM ubuntu

WORKDIR /opensell

# Copy the jar
COPY ./target/backend-1.0.1.jar ./

# Copy the config file
COPY ../launch.sh ./

RUN apt update -y
RUN apt install -y openjdk-21-jdk
RUN apt install -y mariadb-server

# Need to do : mariadb-secure-installation
# CMD [ "java", "-jar", "backend-1.0.1.jar" ]