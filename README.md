# Opensell

Opensell is a website that let user create and search ads. The website use React in the frontend and Spring Boot in the backend.

<!-- Nee to put the some icon that give info -->
![Home ](https://raw.githubusercontent.com/HMDOC/readme-src/main/home.png)

## Installation
<!-- Dependencies -->
<details open><summary><b>External dependencies</b></summary>
<br />

- [nodejs](https://nodejs.org/en/download/prebuilt-installer)
- [MariaDB](https://mariadb.org/download/)
- [JDK 21](https://www.oracle.com/ca-en/java/technologies/downloads/#java21)

<button onclick="alert(10)">hi there</button>

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
# Import the project in your IDE.
```

In your IDE, you need to add this in the `run configurations` of your project section `VM options` :
```sh
-Dport=9108 -Durl="jdbc:mariadb://localhost:3306/mydatabase" -Duser="john" -Dpwd="THIS_IS_NOT_MY_PASSWORD" -DmailPort=587 -Demail="nothankyou@github.com" -DmailPassword="THIS_IS_NOT_MY_PASSWORD" -DuploadPath="<PATH_OF_THE_IMAGES>" -DallowedUrl="http://localhost/" -DserverUrl="<BACKEND_URL>"
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
