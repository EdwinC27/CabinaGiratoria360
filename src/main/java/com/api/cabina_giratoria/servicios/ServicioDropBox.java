package com.api.cabina_giratoria.servicios;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.*;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ServicioDropBox {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioDropBox.class);
    @Value("${tokenDropBox}")
    private String ACCESS_TOKEN;

    @Autowired
    Archivo archivo;

    @Value("${direccionComputadora}")
    private String folderPath;

    public JSONObject getPeticionURL(String accion, int numeroFiesta) {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("CabinaGiratoria").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        if (accion.equals("GetMessyURLs")) {
            String carpetaFiesta = "/fiesta" + numeroFiesta;
            LOGGER.debug(carpetaFiesta);

            return getMP4FilesMessyURLs(client, carpetaFiesta);
        } else if(accion.equals("Upload")) {
            String nombreArchivo =  archivo.encontrarArchivoNuevo(numeroFiesta);
            String carpetaFiesta = "/fiesta" + numeroFiesta + "/" + nombreArchivo;
            String rutaFile = folderPath + numeroFiesta +  "/" + nombreArchivo;

            return uploadFile(client, rutaFile, carpetaFiesta);
        }  else if(accion.equals("Delete")) {
            String carpetaFiesta = "/fiesta" + numeroFiesta ;
            LOGGER.debug(carpetaFiesta);

            return deleteFolder(client, carpetaFiesta);
        } else if(accion.equals("GetSortedURLs")) {
            String carpetaFiesta = "/fiesta" + numeroFiesta ;

            return getMP4FilesSortedURLs(client, carpetaFiesta);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(accion, "Ruta no encontrada");

        return jsonObject;
    }

    // Get Mp4 Files Urls
    public JSONObject getMP4FilesMessyURLs(DbxClientV2 client, String carpetaFiesta) {
        JSONObject jsonObject = new JSONObject();

        // Get files and folder metadata from Dropbox root directory
        ListFolderResult result = null;

        try {
            // listar los archivos y carpetas del directorio raíz de Dropbox
            result = client.files().listFolder(carpetaFiesta);

            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    //  Si el archivo es un video ".mp4"
                    if(metadata.getPathLower().contains(".mp4")) {
                        jsonObject.put(metadata.getPathLower(), getMp4FileUrl(client, metadata.getPathLower()));
                    }
                }

                // Si no hay más resultados, se rompe el bucle
                if (!result.getHasMore()) {
                    break;
                }

                // Si hay más resultados, se llama al método listFolderContinue() para obtener la siguiente página de resultados
                result = client.files().listFolderContinue(result.getCursor());
            }

        } catch (DbxException e) {
            jsonObject.put("Error", e.getMessage());
        }
        return jsonObject;
    }


    // Get Mp4 File Url
    public String getMp4FileUrl(DbxClientV2 client, String filePath) throws DbxException {
        try {
            /*
            llama al método "listSharedLinksBuilder" de la API de Dropbox para listar
            los enlaces compartidos para el archivo especificado en "filePath".
            La opción "withDirectOnly(true)" se utiliza para asegurarse de que solo se
            devuelvan los enlaces compartidos directamente con el archivo y no con una
            carpeta que lo contenga.
             */
            ListSharedLinksResult existingLinks = client.sharing().listSharedLinksBuilder().withPath(filePath).withDirectOnly(true).start();

            if (existingLinks.getLinks().isEmpty()) {
                // El archivo no tiene un enlace compartido, lo creamos
                return getURL(client, filePath);
            } else {
                // Devolvemos el primer enlace compartido existente
                return existingLinks.getLinks().get(0).getUrl();
            }
        } catch (ListSharedLinksErrorException ex) {
            if (ex.errorValue.isPath()) {
                // El archivo no tiene un enlace compartido, lo creamos
                return getURL(client, filePath);
            } else {
                // Error al listar los enlaces compartidos
                return "Error: " + ex.getMessage();
            }
        }
    }

    public String getURL(DbxClientV2 client, String filePath) throws DbxException {
        /*
        Crea un objeto que representa los metadatos del enlace compartido que se va a crear.
        Luego, utiliza el método createSharedLinkWithSettings para crear un enlace compartido
        a un archivo ubicado en Dropbox. El método toma dos argumentos: filePath y un objeto
        SharedLinkSettings que contiene configuraciones para el enlace compartido.
         */

        SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(filePath, SharedLinkSettings.newBuilder()
                .withRequestedVisibility(RequestedVisibility.PUBLIC)
                .build());
        return sharedLinkMetadata.getUrl();
    }


    // Upload file to Dropbox
    public JSONObject uploadFile(DbxClientV2 client, String direccionfile, String direccionfinalDropBox) {
        JSONObject jsonObject = new JSONObject();
        File file = new File(direccionfile);
        try (InputStream in = new FileInputStream(file)) {
            // Subir el archivo a la cuenta de Dropbox del usuario
            FileMetadata metadata = client.files().uploadBuilder(direccionfinalDropBox)
                    .uploadAndFinish(in);
            // El archivo se subió correctamente
            jsonObject.put("Archivo subido correctamente", metadata.getPathLower());
        } catch (IOException | DbxException e) {
            // Ocurrió un error al subir el archivo
            jsonObject.put("Error al subir el archivo: ", e.getMessage());
        }

        return jsonObject;
    }

    // Delete files to Dropbox
    public JSONObject deleteFolder(DbxClientV2 client, String folderPath) {
        JSONObject jsonObject = new JSONObject();

        try {
            ListFolderResult result = client.files().listFolder(folderPath);
            List<Metadata> entries = result.getEntries();
            if (entries.size() > 0) {
                for (Metadata metadata : entries) {
                    if (metadata instanceof FileMetadata) {
                        client.files().deleteV2(metadata.getPathLower());
                        LOGGER.debug(metadata.getName(), "Archivo eliminado exitosamente");
                    }
                }
                LOGGER.debug("Todos los archivos de la carpeta se han eliminado exitosamente.");

                jsonObject.put("Eliminados", "Todos los archivos de la carpeta se han eliminado exitosamente");
            } else {
                LOGGER.debug("La carpeta está vacía, no hay archivos para eliminar.");

                jsonObject.put("Carpeta Vacía", "La carpeta está vacía no hay archivos para eliminar");
            }
        } catch (Exception e) {
            LOGGER.debug("Error al eliminar los archivos de la carpeta: {}", e.getMessage());

            jsonObject.put(e.getMessage(), "Error al eliminar los archivos de la carpeta");
        }

        return jsonObject;
    }

    // Get Last Mp4 File Url
    public JSONObject getMP4FilesSortedURLs(DbxClientV2 client, String carpetaFiesta) {
        JSONObject jsonObject = new JSONObject();

        // Obtener archivos y metadatos de la carpeta Dropbox
        ListFolderResult result = null;

        try {
            // Listar los archivos y carpetas del directorio raíz de Dropbox
            result = client.files().listFolder(carpetaFiesta);

            List<FileMetadata> mp4Files = new ArrayList<>();

            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    // Si el archivo es un video ".mp4"
                    if (metadata instanceof FileMetadata && metadata.getPathLower().endsWith(".mp4")) {
                        mp4Files.add((FileMetadata) metadata);
                    }
                }

                // Si no hay más resultados, se rompe el bucle
                if (!result.getHasMore()) {
                    break;
                }

                // Si hay más resultados, se llama al método listFolderContinue() para obtener la siguiente página de resultados
                result = client.files().listFolderContinue(result.getCursor());
            }

            // Ordenar la lista de archivos por fecha de modificación (de más reciente a más antiguo)
            mp4Files.sort(Comparator.comparing(FileMetadata::getClientModified).reversed());

            if (!mp4Files.isEmpty()) {
                JSONArray jsonArray = new JSONArray();
                // Obtener la URL de cada archivo de vídeo y agregarla al JSONArray
                for (FileMetadata mp4File : mp4Files) {
                    jsonArray.add(getMp4FileUrl(client, mp4File.getPathLower()));
                }

                jsonObject.put("videos", jsonArray);
            } else {
                jsonObject.put("Error", "No se encontraron archivos de vídeo en la carpeta especificada.");
            }

        } catch (DbxException e) {
            jsonObject.put("Error", e.getMessage());
        }
        return jsonObject;
    }

}