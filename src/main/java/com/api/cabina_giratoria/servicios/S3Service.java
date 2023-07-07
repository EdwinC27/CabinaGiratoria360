package com.api.cabina_giratoria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import net.minidev.json.JSONObject;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Autowired
    private Validaciones validaciones;

    @Value("${aws.s3.base.url}")
    String S3_BASE_URL;

    @Value("${aws.bucketName}")
    String bucketName;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public JSONObject listFiles(String numeroFiesta) {
        JSONObject listOfFiles = new JSONObject();

        if(!validaciones.isConvertibleToInt(numeroFiesta)) {
            listOfFiles.put("Error", "Elemento ingresado no es un numero");
        }

        String prefix = "fiesta" + numeroFiesta + "/";

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        for (S3Object s3Object : response.contents()) {
            String fileKey = s3Object.key();
            // Excluir la carpeta "fiesta + numero" en sí misma
            if (!fileKey.equals(prefix)) {
                String fileUrl = S3_BASE_URL + bucketName + "/" + fileKey;
                listOfFiles.put(fileKey, fileUrl);
            }
        }

        return listOfFiles;
    }
}
