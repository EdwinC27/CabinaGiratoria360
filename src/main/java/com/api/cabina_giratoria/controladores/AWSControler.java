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

    @GetMapping("/carpeta")
    public ResponseEntity<JSONObject> putCarpeta(@RequestParam(value = "carpeta") String nombreCarpeta) {
        Boolean creacionCarpeta = s3Service.createFolder(nombreCarpeta);

        if (creacionCarpeta) {
            JSONObject correctResponse = new JSONObject();
            correctResponse.put("Exito", "Carpeta creada correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(correctResponse);
        } else {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "No se pudo crear la carpeta");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}