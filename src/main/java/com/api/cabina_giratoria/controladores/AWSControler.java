package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.servicios.S3Service;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JSONObject getVideos(@RequestParam(value = "fiesta") String numeroFiesta) {
        JSONObject respuestaVideos = s3Service.listFiles(numeroFiesta);

        if(respuestaVideos.isEmpty()) {
            respuestaVideos.put("Error", "Carpeta Vacia");
        }

        return respuestaVideos;
    }
}