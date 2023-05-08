package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.servicios.ServicioDropBox;
import com.dropbox.core.DbxException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ControladorPeticion {

    @Autowired
    ServicioDropBox servicioDropBox;

    @GetMapping("/archivos")
    public JSONObject query(@RequestParam(value = "accion") String accion, @RequestParam(value = "fiesta") int numeroFiesta) {

        return servicioDropBox.getPeticion(accion, numeroFiesta);
    }

}
