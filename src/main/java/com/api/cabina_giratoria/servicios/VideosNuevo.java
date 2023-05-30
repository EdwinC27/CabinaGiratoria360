package com.api.cabina_giratoria.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

@Service
public class VideosNuevo {
    @Value("${direccionComputadora}")
    private String folderPath;

    public String encontrarArchivoNuevo(int numeroFiesta) {
        // Ruta a la carpeta que se desea revisar
        String rutaFile = folderPath + numeroFiesta;

        // Obtener una lista de los archivos en la carpeta, ordenados por fecha de modificación descendente
        File[] files = new File(rutaFile).listFiles();
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
