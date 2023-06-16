package com.example.cabinagiratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;

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

        realintizarVideo();
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

    public void mostrarVideoTerminado() {
        Toast.makeText(this, "¡El video ha sido creado!", Toast.LENGTH_SHORT).show();
    }

    public void realintizarVideo() {
        CompletableFuture<Void> idaFuture = VideoConEfecto.ida();
        CompletableFuture<Void> vueltaFuture = VideoConEfecto.vuelta();

        CompletableFuture.allOf(idaFuture, vueltaFuture).thenAccept((Void) -> {
            String videoIda = MP4Utils.getSelectedFileProcess();
            String videoRegreso = MP4Utils.getSelectedFileRever();
            String newArchivo = Video.createVideoFile("Final").getPath();

            String[] command = {"-y", "-i", videoIda, "-i", videoRegreso, "-filter_complex",
                    "[0:v]scale=w=640:h=480:force_original_aspect_ratio=1[v0];[1:v]scale=w=640:h=480:force_original_aspect_ratio=1[v1];[v0][v1]concat=n=2:v=1:a=0[a]",
                    "-map", "[a]", newArchivo};

            FFmpeg.executeAsync(command, new ExecuteCallback() {
                @Override
                public void apply(long executionId, int returnCode) {
                    Log.d("Return Final: ", returnCode + ": ********************************************************");
                    if (returnCode == 0) {
                        MP4Utils.setSelectedFileFinal(newArchivo);
                        MP4Utils.setSelectedFileWithAudio(VideoConMusica.cambiarVideoPorVideoConAudio());

                        mostrarVideoTerminado();
                    }
                }
            });
        }).exceptionally(ex -> {
            // Se ejecuta si ocurre algún error en ida() o vuelta()
            Log.d("Error en realintizarVideo(): ", ex.getMessage());
            return null;
        });
    }
}