package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.servicios.ServicioDropBox;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorPeticion {

    @Autowired
    ServicioDropBox servicioDropBox;

    @GetMapping("/archivos")
    public JSONObject query(@RequestParam(value = "accion") String accion, @RequestParam(value = "fiesta") String numeroFiesta) {

        return servicioDropBox.getPeticionURL(accion, numeroFiesta);
    }

}
