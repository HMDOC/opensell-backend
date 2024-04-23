# Étapes requise
1. Avoir Java 21 ou supérieur.
2. Cloner le projet `git clone https://github.com/HMDOC/opensell-backend/`.
3. À l'extérieur du folder du projet, créer un fichier nommé launch.sh avec le contenu suivant:
```sh
# DEFAULT
port=PORT_NUMBER;
url="URL_OF_DATABASE";
user="USERNAME";
pwd="PASSWORD";

# MAIL
mailPort=587;
email="EMAIL";
mailPassword="EMAIL_PASSWORD";

# UPLOAD
uploadPath="PATH_OF_UPLOAD";

# WEBSITE URL
allowedUrl="URL";

# SERVEUR URL
serverUrl="SERVEUR_URL";

sslKey="SSL_KEY";

# Get tous les arguments du projet
getArguments() {
    echo --port=$port --url="$url" --user="$user" --pwd="$pwd" --mailPort="$mailPort" --email="$email" --mailPassword="$mailPassword" --uploadPath="$uploadPath" --allowedUrl="$allowedUrl" --serverUrl="$serverUrl";
}

# Run le projet normal
run() {
    cd $1;
    sudo ./mvnw spring-boot:run -Dspring-boot.run.arguments="$(getArguments)";
}

# Run la jar du projet
jar() {
    sudo java -jar $1 $(getArguments);
}

$*

```
