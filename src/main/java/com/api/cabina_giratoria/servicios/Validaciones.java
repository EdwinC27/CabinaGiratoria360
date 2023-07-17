package com.api.cabina_giratoria.servicios;

import com.amazonaws.services.s3.AmazonS3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Validaciones {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucketName}")
    String bucketName;

    public boolean isConvertibleToInt(String numeroFiesta) {
        try {
            Integer.parseInt(numeroFiesta);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean folderExists(String folderName) {
        String folderKey = folderName + "/"; // Agrega "/" al final para indicar que es una carpeta

        return amazonS3.doesObjectExist(bucketName, folderKey);
    }
}