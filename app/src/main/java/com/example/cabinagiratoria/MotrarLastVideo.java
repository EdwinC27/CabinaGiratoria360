package com.example.cabinagiratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MotrarLastVideo extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_last_video);

        videoView = findViewById(R.id.videoView);

        // Obtén la ruta del último video agregado
        String rutaVideoAudio = MP4Utils.getSelectedFileWithAudio();

        if (rutaVideoAudio != null) {
            eliminarVideo();
            // Configura la ruta del video en el VideoView
            videoView.setVideoPath(rutaVideoAudio);


            // Crea un MediaController y asócialo al VideoView
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    //mediaPlayer.setVolume(0, 0); // Establece el volumen del audio a cero
                }
            });

            // Inicia la reproducción del video
            videoView.start();
        }
    }

    private void eliminarVideo() {
        Video.eliminarVideo(MP4Utils.getSelectedFileVideo());
        Video.eliminarVideo(MP4Utils.getSelectedFileProcess());
        Video.eliminarVideo(MP4Utils.getSelectedFileFinal());
        Video.eliminarVideo(MP4Utils.getSelectedFileRever());
    }
}
