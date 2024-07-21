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

### Frontend

- Typescript
- React
- Axios
- react-bootstrap
- react-modal

### Backend

- Java
- Spring Boot
- Lombok

## Installation
<!-- Dependencies -->
<details open><summary><b>External dependencies</b></summary>
<br />

- [nodejs](https://nodejs.org/en/download/prebuilt-installer)
- [MariaDB](https://mariadb.org/download/)
- [JDK 21](https://www.oracle.com/ca-en/java/technologies/downloads/#java21)

</details>

<!-- Frontend section -->
<details open><summary><b>Frontend</b></summary>
<br />

Setup :
```
git clone https://github.com/HMDOC/opensell-frontend
cd opensell-frontend/
npm install
```

Create a file named `data.json` in the `src` folder with this content :
```json
{
    "url": "<BACKEND_URL>"
}
```
</details>

<!-- Backend section -->
<details open><summary><b>Backend</b></summary>
<br />

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

# The path where the image are going to be stored. This path should not end with /
UPLOAD_PATH=

# The url that can make request to the backend. The only one you need is the one of the frontend
ALLOWED_URLS=

SERVER_URL=
```

To enable images :
```
You need to create two folders one named "/ad-image" that will contain the images for the ads and the other "/customer-profil" will contain the profil pictures of the users. They need to be accessible by http like this : http://<BACKEND_URL>/ad-image/.
```

</details>

## Run the project

```sh
# Frontend
npm start

# Backend : run with your IDE.
```

## Preview

![Login](https://raw.githubusercontent.com/HMDOC/readme-src/main/login.png)

![Home Login](https://raw.githubusercontent.com/HMDOC/readme-src/main/connected_option_in_main_page.png)

![My Ads](https://raw.githubusercontent.com/HMDOC/readme-src/main/my-ads.png)

![Profil](https://raw.githubusercontent.com/HMDOC/readme-src/main/profil.png)

![Settings](https://raw.githubusercontent.com/HMDOC/readme-src/main/settings.png)
