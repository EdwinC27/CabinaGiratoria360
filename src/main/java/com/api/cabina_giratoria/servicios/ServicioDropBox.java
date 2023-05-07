package com.api.cabina_giratoria.servicios;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServicioDropBox {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioDropBox.class);
    @Value("${tokenDropBox}")
    private String ACCESS_TOKEN;

    public JSONObject getPeticion(String accion) {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("CabinaGiratoria").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        if(accion.equals("GetUrl")) {
            return getMp4FilesUrls(client);
        } else {
            return null;
        }
    }


    // Get Mp4 Files Urls
    public JSONObject getMp4FilesUrls(DbxClientV2 client) {
        JSONObject jsonObject = new JSONObject();

        // Get files and folder metadata from Dropbox root directory
        ListFolderResult result = null;

        try {
            // listar los archivos y carpetas del directorio raíz de Dropbox
            result = client.files().listFolder("");

            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    //  Si el archivo es un video ".mp4"
                    if(metadata.getPathLower().contains(".mp4")) {
                        jsonObject.put(metadata.getPathLower(), "(URL)");
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

}
