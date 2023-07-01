# API de conexión con Dropbox

Esta API permite establecer una conexión con Dropbox para realizar diversas acciones y peticiones. Antes de utilizar el servicio, es necesario obtener un token de acceso. A continuación, se explica cómo obtenerlo.

## Uso de la aplicación
Este servicio utiliza la API de Dropbox y es utilizado por un frontend que se encuentra en la rama Frontend de este mismo repositorio.

## Obtención del token
Para utilizar este servicio, debes obtener un token de acceso. Sigue estos pasos:

1. Envía una solicitud a la siguiente URL: `http://localhost:8080/dropbox/auth`
2. Esta URL te devolverá un JSON con una URL en su interior. Abre esa URL en tu navegador y otorga el consentimiento para acceder a la API de Dropbox.
3. Una vez completado este proceso, obtendrás el token de acceso necesario para realizar peticiones.

### Uso del servicio
Puedes enviar solicitudes utilizando la siguiente URL: 

`http://localhost:8080/api/archivos?accion="accion"&fiesta="numeroFiesta"`

La parte que debes modificar es el parámetro "accion".

Todas las peticiones se realizan utilizando el método GET. A continuación, se muestran ejemplos de URL con diferentes acciones:

- `http://localhost:8080/api/archivos?accion=GetMessyURLs&fiesta="numeroFiesta"`
  - Esta URL devuelve las URL junto con los nombres de los archivos de la carpeta correspondiente al número de fiesta especificado en la petición.

- `http://localhost:8080/api/archivos?accion=Upload&fiesta="numeroFiesta"`
  - Esta URL permite subir un archivo a la carpeta correspondiente al número de fiesta especificado en la petición.

- `http://localhost:8080/api/archivos?accion=Delete&fiesta="numeroFiesta"`
  - Esta URL permite eliminar los archivos de la carpeta correspondiente al número de fiesta especificado en la petición.

- `http://localhost:8080/api/archivos?accion=GetSortedURLs&fiesta="numeroFiesta"`
  - Esta URL devuelve solo las URL de los archivos de la carpeta correspondiente al número de fiesta especificado en la petición.

### Documentación de la API
Puedes explorar la documentación de la API utilizando las siguientes URLs:

- `http://localhost:8080/v3/api-docs`
  - Accede a la especificación OpenAPI en formato JSON.

- `http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config`
  - Accede a la interfaz de usuario Swagger UI.

## Requisitos
Antes de ejecutar el programa, asegúrate de cumplir con los siguientes requisitos:

- Tener instalado Java 11 o una versión más reciente en tu sistema operativo. Si no tienes Java, puedes descargar e instalar la versión correspondiente desde el sitio web: [https://adoptium.net/temurin/releases/](https://adoptium.net/temurin/releases/).

- Modifica el usuario, la contraseña y el nombre de la base de datos en el archivo `application-dev.properties` que se encuentra en la ruta `src/main/resources`.

- Modifica el archivo llamado `keys.properties` en la carpeta `src/main/resources`, proporcionando los valores necesarios para la autenticación y el uso correcto de la API.

Asegúrate de tener toda esta información correcta y actualizada antes de ejecutar el programa.

## Inicialización
Puedes utilizar este proyecto siguiendo una de las siguientes instrucciones:

### Descargar el proyecto comprimido en formato .zip
1. Descarga el proyecto en formato .zip desde la opción "Code" de este repositorio.
2. Descomprime el archivo descargado y abre la carpeta del proyecto.
3. Modifica el archivo `keys.properties` que se encuentra en la ruta `src/main/resources`, proporcionando los datos necesarios para la autenticación hacia la API que desees utilizar. Asegúrate de utilizar tus propios datos de autenticación y las URLs necesarias.
4. Para ejecutar la aplicación, utiliza el siguiente comando:
``` 
    mvn spring-boot:run
```
Este comando iniciará la aplicación utilizando Maven y Spring Boot. Una vez que la aplicación esté en funcionamiento, podrás comenzar a utilizarla.

### Clonar el repositorio
1. Clona el repositorio de Git en tu PC utilizando el siguiente comando:
``` 
    git clone git@github.com:EdwinC27/CabinaGiratoria.git
```
2. Una vez descargado, abre la carpeta del proyecto.
3. Modifica el archivo `keys.properties` que se encuentra en la ruta `src/main/resources`, proporcionando los datos necesarios para la autenticación hacia la API que desees utilizar. Asegúrate de utilizar tus propios datos de autenticación y las URLs necesarias.
4. Para ejecutar la aplicación, utiliza el siguiente comando:
``` 
    mvn spring-boot:run
```

### Docker
1. Descarga el archivo `docker-compose.yml` que se encuentra en la carpeta `dockerDir`.
2. Una vez descargado, abre el archivo.
3. Abre una terminal y navega hasta la ubicación donde se encuentra el archivo modificado anteriormente.
4. Ejecuta el siguiente comando en la terminal:
``` 
    docker-compose up -d
```
Este comando iniciará los contenedores de Docker definidos en el archivo `docker-compose.yml` en segundo plano. Espera unos segundos hasta que los contenedores estén listos para ser utilizados.

