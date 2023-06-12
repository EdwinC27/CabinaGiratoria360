package com.example.cabinagiratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class ProcesandoVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesando_video);

        // Obtén la ruta del último video agregado
        String rutaUltimoVideo = obtenerUltimoVideo();
        // Obtén la ruta del último video agregado
        MP4Utils.setSelectedFileVideo(rutaUltimoVideo);
        MP4Utils.setSelectedFileWithAudio(MP4Utils.getSelectedFileVideo());

        VideoConEfecto.realintizarVideo();
    }

    private String obtenerUltimoVideo() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File[] files = storageDir.listFiles();

        if (files != null && files.length > 0) {
            // Ordenar los archivos por fecha de modificación descendente
            Arrays.sort(files, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.compare(f2.lastModified(), f1.lastModified());
                }
            });

            return files[0].getAbsolutePath();
        }
        return null;
    }

    public void terminar(View view) {
        // Abrir otro Activity
        Intent intent = new Intent(this, MotrarLastVideo.class);
        startActivity(intent);
    }
}