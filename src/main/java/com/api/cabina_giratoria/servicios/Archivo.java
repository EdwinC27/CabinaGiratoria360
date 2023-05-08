package com.api.cabina_giratoria.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

@Service
public class Archivo {
    // Ruta a la carpeta que se desea revisar
    @Value("${direccionComputadora}")
    private String folderPath;

    public String encontrarArchivoNuevo() {
        // Obtener una lista de los archivos en la carpeta, ordenados por fecha de modificación descendente
        File[] files = new File(folderPath).listFiles();
        Arrays.sort(files, Comparator.comparing(File::lastModified).reversed());

        // Buscar el primer archivo con la extensión ".mp4"
        File latestVideo = Arrays.stream(files)
                .filter(file -> file.getName().endsWith(".mp4"))
                .findFirst()
                .orElse(null);

        if (latestVideo != null) {
            // Si se encontró el archivo más reciente, obtener su nombre
            return  latestVideo.getName();
        } else {
            // Si no se encontró ningún archivo nuevo, hacer algo más
            return null;
        }
    }
}
