package com.api.cabina_giratoria.controladores;

import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorPeticion {

    @GetMapping("/archivos")
    public JSONObject query() {
        JSONObject jsObject = new JSONObject();

        jsObject.put("Hola", "prueba");

        return jsObject;
    }

}
