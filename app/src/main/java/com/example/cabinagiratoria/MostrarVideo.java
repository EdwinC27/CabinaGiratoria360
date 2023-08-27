package com.example.cabinagiratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.os.Environment;

import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cabinagiratoria.Model.MP4Utils;

import android.os.Handler;
import okhttp3.*;
import java.io.File;
import java.io.IOException;

public class MostrarVideo extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;
    String nombreUsuario;
    String nombreCarpeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_video);

        videoView = findViewById(R.id.videoView);

        if (getIntent().hasExtra("nombreUsuario")) {
            nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        }
        if (getIntent().hasExtra("nombreCarpeta")) {
            nombreCarpeta = getIntent().getStringExtra("nombreCarpeta");
        }

        // Obtén la ruta del último video agregado
        String rutaVideoAudio = MP4Utils.getSelectedFileWithAudio();

        if (rutaVideoAudio != null) {
            String moviesFolder = "Movies"; // La carpeta base que deseas identificar
            int startIndex = rutaVideoAudio.indexOf(moviesFolder) + moviesFolder.length() + 1; // +1 para eliminar la barra "/"
            String desiredPath = rutaVideoAudio.substring(startIndex);

            // Ruta completa del video en el directorio Movies
            String videoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                    + "/" + desiredPath;

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

            // Retrasa la ejecución del método subirVideo en 2 segundos
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subirVideo(nombreCarpeta, nombreUsuario, rutaVideoAudio);
                }
            }, 2000);
        }
    }


    private void subirVideo(String carpetaUsuario, String nombreUsuario, String rutaVideo) {
        // String video = MP4Utils.getSelectedFileProcess();
        final String API_URL = "http://3.128.181.152:8080/api/upload/video";

        File videoFile = new File(rutaVideo);

        // Crea un objeto FormData para enviar el archivo
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", videoFile.getName(),
                        RequestBody.create(MediaType.parse("video/*"), videoFile))
                .addFormDataPart("carpeta", carpetaUsuario)
                .addFormDataPart("usuario", nombreUsuario)
                .build();

        Log.d("Prueba*****: ", String.valueOf(requestBody));

        // Construye la solicitud POST
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .build();

        // Realiza la solicitud asíncrona
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MostrarVideo.this, "Error al subir el video", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                runOnUiThread(() -> Toast.makeText(MostrarVideo.this, responseBody, Toast.LENGTH_SHORT).show());
            }
        });
    }
}