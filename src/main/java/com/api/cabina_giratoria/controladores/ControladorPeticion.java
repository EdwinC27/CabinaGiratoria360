package com.api.cabina_giratoria.controladores;

import com.api.cabina_giratoria.servicios.ServicioDropBox;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Realizar una petición a Dropbox")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL de la petición obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Token vacío o parámetros incorrectos o faltantes"),
            @ApiResponse(responseCode = "500", description = "Error al conectarse con Dropbox o servicios externos")
    })
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
