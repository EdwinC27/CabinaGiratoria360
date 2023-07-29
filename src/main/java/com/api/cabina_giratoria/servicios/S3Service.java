package com.api.cabina_giratoria.servicios;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.util.IOUtils;
import com.api.cabina_giratoria.model.constants.EndPoints;
import net.minidev.json.JSONArray;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private Validaciones validaciones;

    @Value("${aws.bucketName}")
    String bucketName;

    public ResponseEntity<JSONObject> getLogo(@RequestParam(value = "usuario") String usuario) {
        JSONObject responseJson = new JSONObject();

        String prefix = usuario + "/";

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        ListObjectsV2Result response = amazonS3.listObjectsV2(request);

        List<S3ObjectSummary> objectSummaries = response.getObjectSummaries();
        objectSummaries.sort(Comparator.comparing(S3ObjectSummary::getLastModified).reversed());


        for (S3ObjectSummary s3Object : objectSummaries) {
            String fileKey = s3Object.getKey();
            if (!fileKey.equals(prefix)) {
                String fileExtension = obtenerExtension(fileKey);

                if (fileExtension.equals(".jpeg") || fileExtension.equals(".png") || fileExtension.equals(".jpg") || fileExtension.equals(".git") || fileExtension.equals(".svg")) {
                    String fileUrl = generatePreSignedUrl(bucketName, fileKey);  // Generar la URL prefirmada para el archivo

                    responseJson.put("logo", fileUrl);
                }
            }
        }

        return ResponseEntity.ok(responseJson);
    }


    public ResponseEntity<JSONObject> listFiles(String nombreFiesta, String carpetaUsuario) {
        // Existe la carpeta
        if (validaciones.folderExists(nombreFiesta)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "La carpeta no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        JSONArray listOfFiles = new JSONArray();
        JSONObject responseJson = new JSONObject();

        /*
        if (!validaciones.isConvertibleToInt(numeroFiesta)) {
            listOfFiles.put("Error", "Elemento ingresado no es un numero");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOfFiles);
        }
        */

        String prefix = nombreFiesta + "/";
        String carpeta = carpetaUsuario.endsWith("/") ? carpetaUsuario : carpetaUsuario + "/" + prefix;

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(carpeta)
                .withDelimiter("/");

        ListObjectsV2Result response = amazonS3.listObjectsV2(request);

        List<S3ObjectSummary> objectSummaries = response.getObjectSummaries();
        objectSummaries.sort(Comparator.comparing(S3ObjectSummary::getLastModified).reversed());

        for (S3ObjectSummary s3Object : objectSummaries) {
            String fileKey = s3Object.getKey();
            // Excluir la carpeta "fiesta + numero" en sí misma
            if (!fileKey.equals(prefix)) {
                String fileExtension = obtenerExtension(fileKey);

                if (fileExtension.equals(".mp4")) {
                    String fileUrl = generatePreSignedUrl(bucketName, fileKey);  // Generar la URL prefirmada para el archivo

                    JSONObject fileObject = new JSONObject();
                    fileObject.put(fileKey, fileUrl);
                    listOfFiles.add(fileObject);
                } else if (fileExtension.equals(".txt")) {
                    String resultado = obtenerParteDerechaSinUltimos4(fileKey);
                    responseJson.put("txt", resultado);
                } else if (fileExtension.equals(".jpeg") || fileExtension.equals(".png") || fileExtension.equals(".jpg") || fileExtension.equals(".git") || fileExtension.equals(".svg")) {
                    String fileUrl = generatePreSignedUrl(bucketName, fileKey);  // Generar la URL prefirmada para el archivo

                    responseJson.put("logo", fileUrl);
                }
            }
        }

        responseJson.put("videos", listOfFiles);

        return ResponseEntity.ok(responseJson);
    }

    private String obtenerParteDerechaSinUltimos4(String texto) {
        // Quitar la extensión ".txt"
        if (texto.endsWith(".txt")) {
            texto = texto.substring(0, texto.length() - 4);
        }

        // Eliminar la primera parte de la ruta
        String[] segments = texto.split("/");
        texto = segments[segments.length - 1];

        return texto;
    }

    public String obtenerExtension(String fileKey) {
        int lastDotIndex = fileKey.lastIndexOf(".");
        if (lastDotIndex >= 0 && lastDotIndex < fileKey.length() - 1) {
            return fileKey.substring(lastDotIndex);
        }
        return "";
    }

    public String generatePreSignedUrl(String bucket, String filePath) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 2);

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, filePath)
                .withMethod(HttpMethod.GET)
                .withExpiration(cal.getTime());

        // Opcional: Personalizar los encabezados de respuesta si es necesario
        // ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        // responseHeaders.setContentType("application/octet-stream");
        // request.setResponseHeaders(responseHeaders);

        return amazonS3.generatePresignedUrl(request).toString();
    }


    public ResponseEntity<JSONObject> createFolder(String folderName, String fileName, String carpetaUsuario) {
        // Existe la carpeta
        if (validaciones.folderExists(folderName)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "La carpeta ya existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        String folderKey = carpetaUsuario + "/" + folderName + "/"; // Agrega la ruta completa de la carpeta

        // Contenido vacío para crear la carpeta
        byte[] content = new byte[0];
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length);

        PutObjectRequest request = new PutObjectRequest(bucketName, folderKey + fileName, new ByteArrayInputStream(content), metadata);

        try {
            amazonS3.putObject(request);
            JSONObject correctResponse = new JSONObject();
            correctResponse.put("Exito", "Carpeta y archivo .txt creados correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(correctResponse);
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "No se pudo crear la carpeta o el archivo .txt");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    public ResponseEntity<JSONObject> subirImagen(MultipartFile file, String folderName, String carpetaUsuario) {
        try {
            if (!validaciones.fileIsImage(file)) {
                JSONObject errorResponse = new JSONObject();

                errorResponse.put("Error", "El archivo no es una imagen");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Crear un objeto de metadatos para configurar el tipo de contenido de la imagen
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            String folderKey = carpetaUsuario + "/" + folderName + "/"; // Agrega la ruta completa de la carpeta

            // Subir la imagen a Amazon S3
            amazonS3.putObject(new PutObjectRequest(bucketName, folderKey + file.getOriginalFilename(), file.getInputStream(), metadata));

            JSONObject correctResponse = new JSONObject();
            correctResponse.put("Exito", "Carpeta subida correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(correctResponse);
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "No se pudo subir la imagen");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    public ResponseEntity<JSONObject> deleteFolder(String folderName, String carpetaUsuario) {
        // Existe la carpeta
        if (validaciones.folderExists(folderName)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "La carpeta no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        String folderKey = carpetaUsuario + "/" + folderName + "/"; // Ruta completa de la carpeta a eliminar

        // Obtener la lista de objetos en la carpeta
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(folderKey);
        ListObjectsV2Result result = amazonS3.listObjectsV2(request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        // Eliminar los objetos dentro de la carpeta
        for (S3ObjectSummary object : objects) {
            String key = object.getKey();
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }

        // Eliminar la carpeta en sí
        try {
            amazonS3.deleteObject(bucketName, folderKey);
            JSONObject correctResponse = new JSONObject();
            correctResponse.put("Exito", "Carpeta eliminada correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(correctResponse);
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "No se pudo eliminar la carpeta");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    public ResponseEntity<JSONObject> deleteArchivos() {
        // Obtener la lista de objetos en la carpeta
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName);

        ListObjectsV2Result result = amazonS3.listObjectsV2(request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        // Eliminar los archivos sin extensión
        for (S3ObjectSummary object : objects) {
            String key = object.getKey();
            if (!hasExtension(key)) {
                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
            }
        }

        JSONObject correctResponse = new JSONObject();
        correctResponse.put("Exito", "Archivos sin extensión eliminados correctamente");
        return ResponseEntity.status(HttpStatus.OK).body(correctResponse);
    }

    private boolean hasExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0 && dotIndex < fileName.length() - 1);
    }

    public ResponseEntity<JSONObject> listFolder(String carpetaUsuario) {
        String carpeta = carpetaUsuario.endsWith("/") ? carpetaUsuario : carpetaUsuario + "/";

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(carpeta)
                .withDelimiter("/");

        ListObjectsV2Result response = amazonS3.listObjectsV2(request);

        for (String commonPrefix : response.getCommonPrefixes()) {
            String folderName = commonPrefix.substring(carpeta.length(), commonPrefix.length() - 1);

            if (!folderName.isEmpty()) {
                String displayFolderName = folderName.substring(folderName.lastIndexOf("/") + 1);

                // Get the creation date of the folder directly from the object summary
                S3ObjectSummary objectSummary = amazonS3.listObjects(bucketName, commonPrefix).getObjectSummaries().get(0);
                Date creationDate = objectSummary.getLastModified();


                // Check if the folder is older than 10 days and delete it
                if (isFolderOlderThanTenDays(creationDate)) {
                    deleteFolder(displayFolderName, carpetaUsuario);
                }
            }
        }


        ListObjectsV2Result response2 = amazonS3.listObjectsV2(request);
        JSONObject listOfFolders = new JSONObject();

        for (String commonPrefix : response2.getCommonPrefixes()) {
            String folderName = commonPrefix.substring(carpeta.length(), commonPrefix.length() - 1);

            if (!folderName.isEmpty()) {
                String displayFolderName = folderName.substring(folderName.lastIndexOf("/") + 1);

                listOfFolders.put(displayFolderName, "carpeta"); // Generar la URL de la carpeta
            }
        }

        return ResponseEntity.ok(listOfFolders);
    }

    private boolean isFolderOlderThanTenDays(Date creationDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creationDate);
        calendar.add(Calendar.DAY_OF_MONTH, 10); // Adding 10 days to the creation date

        Date tenDaysAgo = calendar.getTime();
        Date currentDate = new Date();

        return currentDate.after(tenDaysAgo);
    }

    public ResponseEntity<byte[]> downloadFolder(String nombreFiesta, String carpetaUsuario) throws IOException {
        // Obtener la lista de objetos dentro de la carpeta
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(carpetaUsuario + "/" + nombreFiesta + "/");
        ListObjectsV2Result response = amazonS3.listObjectsV2(request);
        List<S3ObjectSummary> objectSummaries = response.getObjectSummaries();

        // Crear una carpeta temporal para almacenar los archivos descargados
        File tempFolder = new File(System.getProperty("java.io.tmpdir"), carpetaUsuario + "_" + nombreFiesta);
        tempFolder.mkdirs();

        // Descargar cada archivo dentro de la carpeta
        for (S3ObjectSummary s3Object : objectSummaries) {
            String fileKey = s3Object.getKey();
            String fileName = fileKey.substring(fileKey.lastIndexOf("/") + 1);
            S3Object s3Object1 = amazonS3.getObject(bucketName, fileKey);

            try (InputStream objectData = s3Object1.getObjectContent()) {
                File outputFile = new File(tempFolder, fileName);
                downloadObject(s3Object1, outputFile);
            } catch (IOException e) {
                // Manejar el error si la descarga falla
                e.printStackTrace();
                // Puedes decidir si deseas continuar descargando otros archivos o detener la descarga en caso de error.
            }
        }

        // Comprimir los archivos descargados en un archivo zip
        String zipFileName = tempFolder.getName() + ".zip";
        File zipFile = new File(tempFolder.getParent(), zipFileName);
        ZipUtils.zipDirectory(tempFolder, zipFile);

        // Eliminar la carpeta temporal con los archivos descargados
        FileUtils.deleteDirectory(tempFolder);

        // Leer el archivo zip y convertirlo a un arreglo de bytes para enviar en la respuesta
        try (InputStream inputStream = new FileInputStream(zipFile)) {
            byte[] zipBytes = IOUtils.toByteArray(inputStream);

            // Configurar las cabeceras de respuesta para que el navegador descargue el archivo zip
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(zipFile.getName()).build());

            // Devolver la respuesta con el archivo zip para que el usuario pueda descargarlo
            return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Manejar el error si ocurre algún problema al leer o enviar el archivo zip
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public void downloadObject(S3Object s3Object, File outputFile) throws IOException {
        try (InputStream objectData = s3Object.getObjectContent();
             OutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = objectData.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
