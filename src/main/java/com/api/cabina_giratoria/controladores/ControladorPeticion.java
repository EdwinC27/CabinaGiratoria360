package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.servicios.ServicioDropBox;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.api.cabina_giratoria.controladores.DropboxController.accessToken;

@RestController
@RequestMapping("/api")
public class ControladorPeticion {

    @Autowired
    ServicioDropBox servicioDropBox;

    @GetMapping("/archivos")
    public JSONObject query(@RequestParam(value = "accion") String accion, @RequestParam(value = "fiesta") String numeroFiesta) {
        JSONObject jsonObject = new JSONObject();
        if(accessToken != null) {
            return servicioDropBox.getPeticionURL(accion, numeroFiesta, accessToken);
        } else {
            jsonObject.put("Error", "Token vacio");
            return jsonObject;
        }
    }

}
