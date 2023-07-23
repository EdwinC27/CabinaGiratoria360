# FontEnd de conexión con AWS

Este proyecto fue generado con [Angular CLI](https://github.com/angular/angular-cli) versión 15.2.4.

El objetivo principal de este proyecto es permitirte visualizar los archivos almacenados en tu cuenta de S3.

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
3. Modifica el archivo environmet.ts que se encuentra en la ruta **src/environments/**, proporcionando los datos necesarios para la autenticación hacia la API.
4. Para ejecutar la aplicación, siga estos pasos:
     - Abra una terminal en la raíz del proyecto y ejecute:
      ```
        npm install
      ```
  
      - Ejecute este comando:
      ```
        ng serve
      ```
      Este comando iniciará la aplicación en el puerto 4200 de su computadora.

### Clona el repositorio.
1. Clone el repositorio de Git en su PC con el siguiente comando:
     ```
        git clone git@github.com:EdwinC27/CabinaGiratoria.git
     ```
2. Una vez descargado, abra la carpeta del proyecto.
3. Cambiate a la rama de FrontEnd-Pubilco-AWS.
4. Modifica el archivo environmet.ts que se encuentra en la ruta **src/environments/**, proporcionando los datos necesarios para la autenticación hacia la API.
5. Finalmente, para ejecutar la aplicación, primero use el comando:
       - Abra una terminal en la raíz del proyecto y ejecute:
      ```
        npm install
      ```
  
      - Ejecute este comando:
      ```
        ng serve
      ```
      Este comando iniciará la aplicación en el puerto 4200 de su computadora. Acceda a la URL **http://localhost:4200/** para ver la aplicación.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
