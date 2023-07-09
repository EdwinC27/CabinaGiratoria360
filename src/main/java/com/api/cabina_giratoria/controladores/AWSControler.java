package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.servicios.S3Service;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AWSControler {

    private final S3Service s3Service;

    @Autowired
    public AWSControler(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/videos")
    public ResponseEntity<JSONObject> getVideos(@RequestParam(value = "fiesta") String numeroFiesta) {
        ResponseEntity<JSONObject> respuestaVideos = s3Service.listFiles(numeroFiesta);

        if (respuestaVideos.getBody().isEmpty()) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "Carpeta Vacia");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        return respuestaVideos;
    }
}