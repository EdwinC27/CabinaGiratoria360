package com.api.cabina_giratoria.servicios;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class Archivo {
    private static final Logger LOGGER = LoggerFactory.getLogger(Archivo.class);

    private final ResourceLoader resourceLoader;

    public Archivo(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public boolean LeerArchivoLineaPorLinea(String user, String password) {
        boolean isPresent = false;

        String lineaUsuarioContraseña = user + " " + password;

        String nombreArchivo = "classpath:static/usuarios.txt";

        try {
            Resource resource = resourceLoader.getResource(nombreArchivo);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String linea;
            while ((linea = br.readLine()) != null) {
                // Procesar cada línea aquí
                if (lineaUsuarioContraseña.equals(linea)) {
                    isPresent = true;
                    break;
                }
            }

            br.close();
        } catch (IOException e) {
            LOGGER.error("Error al leer el archivo: " + e.getMessage());
        }

        LOGGER.error("Mostro: " + isPresent);

        return isPresent;
    }
}
