package com.api.cabina_giratoria.servicios;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Date;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private Validaciones validaciones;

    @Value("${aws.bucketName}")
    String bucketName;

    public ResponseEntity<JSONObject> listFiles(String nombreFiesta) {
        JSONObject listOfFiles = new JSONObject();

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

        for (S3ObjectSummary s3Object : response.getObjectSummaries()) {
            String fileKey = s3Object.getKey();
            // Excluir la carpeta "fiesta + numero" en sí misma
            if (!fileKey.equals(prefix)) {
                String fileUrl = generatePreSignedUrl(bucketName, fileKey);  // Generar la URL prefirmada para el archivo

                listOfFiles.put(fileKey, fileUrl);
            }
        }

        return ResponseEntity.ok(listOfFiles);
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


    public boolean createFolder(String folderName) {
        String folderKey = folderName + "/"; // Agrega "/" al final para indicar que es una carpeta

        // Contenido vacío para crear la carpeta
        byte[] content = new byte[0];
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length);

        PutObjectRequest request = new PutObjectRequest(bucketName, folderKey, new ByteArrayInputStream(content), metadata);
        try {
            amazonS3.putObject(request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
