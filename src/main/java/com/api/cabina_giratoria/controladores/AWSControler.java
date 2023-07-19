package com.api.cabina_giratoria.controladores;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.api.cabina_giratoria.servicios.S3Service;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Patch;
import java.io.IOException;

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

    @GetMapping("/carpeta")
    public ResponseEntity<JSONObject> putCarpeta() {
        return s3Service.listFolder();
    }

    @GetMapping("/carpeta/crear")
    public ResponseEntity<JSONObject> putCarpeta(@RequestParam(value = "carpeta") String nombreCarpeta, @RequestParam(value = "archivo") String nombreArchivo) {
        String file = nombreArchivo + ".txt";
        return s3Service.createFolder(nombreCarpeta, file);
    }

    @GetMapping("/carpeta/eliminar")
    public ResponseEntity<JSONObject> deleteCarpeta(@RequestParam(value = "carpeta") String nombreCarpeta) {
        return s3Service.deleteFolder(nombreCarpeta);
    }

    @GetMapping("/archivos/eliminar")
    public ResponseEntity<JSONObject> deleteArchivos() {
        return s3Service.deleteArchivos();
    }

    @PostMapping("/upload")
    public ResponseEntity<JSONObject> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("carpeta") String nombreFiesta) {
        return s3Service.subirImagen(file, nombreFiesta);
    }
}