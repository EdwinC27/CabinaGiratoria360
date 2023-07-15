package com.api.cabina_giratoria.servicios;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

import net.minidev.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private Validaciones validaciones;

    @Value("${aws.bucketName}")
    String bucketName;

    public ResponseEntity<JSONObject> listFiles(String nombreFiesta) {
        // Existe la carpeta
        if(validaciones.folderExists(nombreFiesta)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "La carpeta no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        JSONArray listOfFiles = new JSONArray();

        /*
        if(!validaciones.isConvertibleToInt(numeroFiesta)) {
            listOfFiles.put("Error", "Elemento ingresado no es un numero");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listOfFiles);
        }
        */

        String prefix = nombreFiesta + "/";

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        ListObjectsV2Result response = amazonS3.listObjectsV2(request);

        List<S3ObjectSummary> objectSummaries = response.getObjectSummaries();
        objectSummaries.sort(Comparator.comparing(S3ObjectSummary::getLastModified).reversed());

        for (S3ObjectSummary s3Object : objectSummaries) {
            String fileKey = s3Object.getKey();
            // Excluir la carpeta "fiesta + numero" en sí misma
            if (!fileKey.equals(prefix)) {
                String fileUrl = generatePreSignedUrl(bucketName, fileKey);  // Generar la URL prefirmada para el archivo

                JSONObject fileObject = new JSONObject();
                fileObject.put(fileKey, fileUrl);

                listOfFiles.add(fileObject);
            }
        }


        JSONObject responseJson = new JSONObject();
        responseJson.put("videos", listOfFiles);

        return ResponseEntity.ok(responseJson);
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


    public ResponseEntity<JSONObject> createFolder(String folderName) {
        // Existe la carpeta
        if(validaciones.folderExists(folderName)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "La carpeta ya existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        String folderKey = folderName + "/"; // Agrega "/" al final para indicar que es una carpeta

        // Contenido vacío para crear la carpeta
        byte[] content = new byte[0];
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length);

        PutObjectRequest request = new PutObjectRequest(bucketName, folderKey, new ByteArrayInputStream(content), metadata);
        try {
            amazonS3.putObject(request);
            JSONObject correctResponse = new JSONObject();
            correctResponse.put("Exito", "Carpeta creada correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(correctResponse);
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "No se pudo crear la carpeta");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    public ResponseEntity<JSONObject> deleteFolder(String folderName) {
        // Existe la carpeta
        if(!validaciones.folderExists(folderName)) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "La carpeta no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        String folderKey = folderName + "/"; // Agrega "/" al final para indicar que es una carpeta

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

    public ResponseEntity<JSONObject> listFolder() {
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withDelimiter("/");

        ListObjectsV2Result response = amazonS3.listObjectsV2(request);

        JSONObject listOfFolders = new JSONObject();
        for (String commonPrefix : response.getCommonPrefixes()) {
            String folderName = commonPrefix.substring(0, commonPrefix.length() - 1); // Eliminar la barra diagonal al final
            listOfFolders.put(folderName, "carpeta"); // Generar la URL de la carpeta
        }

        return ResponseEntity.ok(listOfFolders);
    }
}
