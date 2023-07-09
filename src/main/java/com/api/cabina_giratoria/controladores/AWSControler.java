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
    public ResponseEntity<JSONObject> getVideos(@RequestParam(value = "fiesta") String nombreFiesta) {
        ResponseEntity<JSONObject> respuestaVideos = s3Service.listFiles(nombreFiesta);

        if (respuestaVideos.getBody().isEmpty()) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "Carpeta Vacia");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        return respuestaVideos;
    }

    @GetMapping("/carpeta/crear")
    public ResponseEntity<JSONObject> putCarpeta(@RequestParam(value = "carpeta") String nombreCarpeta) {
        return s3Service.createFolder(nombreCarpeta);
    }

    @GetMapping("/carpeta/eliminar")
    public ResponseEntity<JSONObject> deleteCarpeta(@RequestParam(value = "carpeta") String nombreCarpeta) {
        return s3Service.deleteFolder(nombreCarpeta);
    }
}