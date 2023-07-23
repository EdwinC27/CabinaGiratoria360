# API de conexión con AWS (S3)

Esta API permite establecer una conexión con S3 para realizar diversas acciones y peticiones.

## Uso de la aplicación
Este servicio utiliza la API de AWS.


### Documentación de la API
Puedes explorar la documentación de la API utilizando las siguientes URLs:

- `http://localhost:8080/v3/api-docs`
  - Accede a la especificación OpenAPI en formato JSON.

- `http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config`
  - Accede a la interfaz de usuario Swagger UI.

## Requisitos
Antes de ejecutar el programa, asegúrate de cumplir con los siguientes requisitos:

- Tener instalado Java 11 o una versión más reciente en tu sistema operativo. Si no tienes Java, puedes descargar e instalar la versión correspondiente desde el sitio web: [https://adoptium.net/temurin/releases/](https://adoptium.net/temurin/releases/).

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
Este comando iniciará la aplicación en el puerto 8080 utilizando Maven. Una vez que la aplicación esté en funcionamiento, podrás comenzar a utilizarla.

### Clonar el repositorio
1. Clona el repositorio de Git en tu PC utilizando el siguiente comando:
``` 
    git clone git@github.com:EdwinC27/CabinaGiratoria.git
```
2. Una vez descargado, abre la carpeta del proyecto.
3. Cambiate a la rama de API-AWS.
4. Modifica el archivo `keys.properties` que se encuentra en la ruta `src/main/resources`, proporcionando los datos necesarios para la autenticación hacia la API que desees utilizar. Asegúrate de utilizar tus propios datos de autenticación y las URLs necesarias.
5. Para ejecutar la aplicación, utiliza el siguiente comando:
``` 
    mvn spring-boot:run
```
