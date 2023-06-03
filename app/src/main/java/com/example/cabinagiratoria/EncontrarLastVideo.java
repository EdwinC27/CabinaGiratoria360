package com.example.cabinagiratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class EncontrarLastVideo extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontrar_last_video);

        videoView = findViewById(R.id.videoView);

        // Obtén la ruta del último video agregado
        String rutaUltimoVideo = obtenerUltimoVideo();


        if (rutaUltimoVideo != null) {
            // Configura la ruta del video en el VideoView
            videoView.setVideoPath(rutaUltimoVideo);


            // Crea un MediaController y asócialo al VideoView
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0, 0); // Establece el volumen del audio a cero
                }
            });

            // Inicia la reproducción del video
            videoView.start();
        }
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

}
