package com.api.cabina_giratoria.servicios;

import org.springframework.stereotype.Service;

@Service
public class Validaciones {

    public boolean isConvertibleToInt(String numeroFiesta) {
        try {
            Integer.parseInt(numeroFiesta);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
