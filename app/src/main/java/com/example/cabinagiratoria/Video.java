package com.example.cabinagiratoria;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Video {
    public static File createVideoFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String videoFileName = "VIDEO_" + timeStamp + ".mp4";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File videoFile = new File(storageDir, videoFileName);
        return videoFile;
    }

    public static void eliminarVideo(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) Log.d("Exito", "El archivo de video ha sido eliminado exitosamente. " + filePath);
            else Log.d("Error", "No se pudo eliminar el archivo de video. " + filePath);
        } else {
            Log.d("Error", "El archivo de video no existe en la ruta especificada. " + filePath);
        }
    }
}
