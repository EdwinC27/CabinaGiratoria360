# FontEnd de conexión con Dropbox

Este proyecto fue generado con [Angular CLI](https://github.com/angular/angular-cli) versión 15.2.4.

El objetivo principal de este proyecto es permitirte visualizar los videos almacenados en tu cuenta de Dropbox. Para lograrlo, es necesario configurar previamente tu cuenta en la API correspondiente. Puedes encontrar las instrucciones para realizar esta configuración en la sección de la API de este repositorio.

## Requisitos:
1. Tener Node instalado en su computadora. Si no lo tiene, puede descargarlo <a href="https://nodejs.org/en/">aquí</a>.
2. Tenga instalado npm en su computadora. Si no lo tienes, puedes hacerlo con el siguiente comando:
    ```
      npm instalar npm@último -g
    ```
3. Tenga Angular instalado en su computadora. Si no lo tienes, puedes hacerlo con el siguiente comando:
    ```
      npm instalar @angular/cli
    ```

## Inicialización
Para utilizar este proyecto, puede seguir una de las siguientes instrucciones:

### Descargue el proyecto en un archivo .zip.
1. Descarga el proyecto en formato .zip desde la opción "Código" de este repositorio.
2. Una vez descargado, extraiga el archivo y abra la carpeta del proyecto.
3. Para ejecutar la aplicación, siga estos pasos:
     - Abra una terminal en la raíz del proyecto y ejecute:
      ```
        npm install
      ```
  
      - Ejecute este comando:
      ```
        ng serve
      ```
      Este comando iniciará la aplicación en el puerto 4200 de su computadora. Acceda a la URL **http://localhost:4200/** para ver la aplicación.

### Clona el repositorio.
1. Clone el repositorio de Git en su PC con el siguiente comando:
     ```
        git clone git@github.com:EdwinC27/CabinaGiratoria.git
     ```
2. Una vez descargado, abra la carpeta del proyecto.
3. Finalmente, para ejecutar la aplicación, primero use el comando:
       - Abra una terminal en la raíz del proyecto y ejecute:
      ```
        npm install
      ```
  
      - Ejecute este comando:
      ```
        ng serve
      ```
      Este comando iniciará la aplicación en el puerto 4200 de su computadora. Acceda a la URL **http://localhost:4200/** para ver la aplicación.

### Docker
1. Descarga el archivo docker-compose.yml que se encuentra en la carpeta dockerDir.
2. Una vez descargado, abre el archivo.
3. Abre una terminal y navega hasta la ubicación donde se encuentra el archivo.
4. Ejecuta el siguiente comando en la terminal:
``` 
    docker-compose up -d
```
Este comando iniciará los contenedores de Docker definidos en el archivo docker-compose.yml en segundo plano. Espera unos segundos hasta que los contenedores estén listos para ser utilizados.
