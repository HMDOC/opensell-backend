# Opensell

Opensell is a website for buying and selling ads that we made in the course 420-412-MV(project - development of a web application) at Cegep Marie-Victorin.

<!-- Need to put some icon that give info -->
![Home ](https://raw.githubusercontent.com/HMDOC/readme-src/main/home3.png)

## Features

- Login and sign up.
- Creating and modifying ads.
- Searching ads with multiples filter like price range, category, tags, shape and many more.
- Changing information about our account like our phone number, bio, picture, etc.

## Technologies

### Backend

- Java
- Spring Boot
- Lombok

### Frontend

- Typescript
- React
- Axios
- MUI

## Installation
<!-- Dependencies -->
<details open><summary><b>External dependencies</b></summary>

- [nodejs](https://nodejs.org/en/download/prebuilt-installer)
- [MariaDB](https://mariadb.org/download/)
- [JDK 21](https://www.oracle.com/ca-en/java/technologies/downloads/#java21)

</details>
<br />

<!-- Images section -->
<details open><summary><b>Images Server</b></summary>

Setup :
```shell
git clone https://github.com/HMDOC/opensell-images
npm install
```

</details>
<br />

<!-- Backend section -->
<details open><summary><b>Backend</b></summary>

Setup :
```
git clone https://github.com/HMDOC/opensell-backend
```

Create the file `env.properties` in the root folder that contains this :
```properties
# The port of the backend.
SERVER_PORT=

DB_USERNAME=
DB_PASSWORD=
DB_URL=

# The url of the SMTP server. Ex: smtp-mail.outlook.com
SMTP_HOST=

# The port of the SMTP server. Ex: 587
SMTP_PORT=
SMTP_EMAIL=
SMTP_PASSWORD=

# The url that can make request to the backend. The only one you need is the one of the frontend
ALLOWED_URLS=

# The path where the image are going to be stored. This path should end with /public if you are using the image server.
IMAGE_SERVER_PATH=

# The url of the server that contain the image. Ex: http://localhost:$PORT
IMAGE_SERVER_URL=
```
</details>
<br />

<!-- Frontend section -->
<details open><summary><b>Frontend</b></summary>

Setup :
```
git clone https://github.com/HMDOC/opensell-frontend
cd opensell-frontend/
npm install
```

Create a file named `.env.local` in the root folder with this content :
```properties
VITE_PORT=
VITE_BACKEND_URL=

# The secret key for JWT
VITE_JWT_SECRET_KEY=
```
</details>

## Run the project

```sh
# Frontend
npm run dev

# Backend: run with your IDE or :
./mvnw spring-boot:run

# Images
npm start
```

## Preview

![Login](https://raw.githubusercontent.com/HMDOC/readme-src/main/login.png)

![Home Login](https://raw.githubusercontent.com/HMDOC/readme-src/main/connected_option_in_main_page.png)

![My Ads](https://raw.githubusercontent.com/HMDOC/readme-src/main/my-ads.png)

![Profile](https://raw.githubusercontent.com/HMDOC/readme-src/main/profil.png)

![Settings](https://raw.githubusercontent.com/HMDOC/readme-src/main/settings.png)
