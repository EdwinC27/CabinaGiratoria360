package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.model.constants.EndPoints;
import com.api.cabina_giratoria.servicios.S3Service;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(EndPoints.BASE)
public class AWSControler {

    private final S3Service s3Service;

    @Autowired
    public AWSControler(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping(EndPoints.VIDEOS)
    public ResponseEntity<JSONObject> getVideos(@RequestParam(value = "fiesta") String nombreFiesta) {
        ResponseEntity<JSONObject> respuestaVideos = s3Service.listFiles(nombreFiesta);

        if (respuestaVideos.getBody().isEmpty()) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("Error", "Carpeta Vacia");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        return respuestaVideos;
    }

    @GetMapping(EndPoints.MOSTRAR_CARPETAS)
    public ResponseEntity<JSONObject> putCarpeta() {
        return s3Service.listFolder();
    }

    @GetMapping(EndPoints.CREAR_CARPETAS)
    public ResponseEntity<JSONObject> putCarpeta(@RequestParam(value = "carpeta") String nombreCarpeta, @RequestParam(value = "archivo") String nombreArchivo) {
        String file = nombreArchivo + ".txt";
        return s3Service.createFolder(nombreCarpeta, file);
    }

    @DeleteMapping(EndPoints.ELIMINAR_CARPETAS)
    public ResponseEntity<JSONObject> deleteCarpeta(@RequestParam(value = "carpeta") String nombreCarpeta) {
        return s3Service.deleteFolder(nombreCarpeta);
    }

    @DeleteMapping(EndPoints.ELIMINAR_ARCHIVOS)
    public ResponseEntity<JSONObject> deleteArchivos() {
        return s3Service.deleteArchivos();
    }

    @PostMapping(EndPoints.SUBIR_IMAGEN)
    public ResponseEntity<JSONObject> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("carpeta") String nombreFiesta) {
        return s3Service.subirImagen(file, nombreFiesta);
    }
}