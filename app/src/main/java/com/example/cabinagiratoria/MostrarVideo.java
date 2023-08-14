package com.example.cabinagiratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cabinagiratoria.Model.MP4Utils;

public class MostrarVideo extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_video);

        videoView = findViewById(R.id.videoView);

        // Obtén la ruta del último video agregado
        String rutaVideoAudio = MP4Utils.getSelectedFileWithAudio();

        if (rutaVideoAudio != null) {
            String moviesFolder = "Movies"; // La carpeta base que deseas identificar
            int startIndex = rutaVideoAudio.indexOf(moviesFolder) + moviesFolder.length() + 1; // +1 para eliminar la barra "/"
            String desiredPath = rutaVideoAudio.substring(startIndex);

            // Ruta completa del video en el directorio Movies
            String videoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                    + "/" + desiredPath;

            Toast.makeText(this, "ruta: " + videoPath, Toast.LENGTH_SHORT).show();

            // Configura la ruta del video en el VideoView
            videoView.setVideoURI(Uri.parse(videoPath));

            // Crea un MediaController y asócialo al VideoView
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // mediaPlayer.setVolume(0, 0); // Establece el volumen del audio a cero
                }
            });

            // Iniciar la reproducción del video
            videoView.start();
        }
    }
}