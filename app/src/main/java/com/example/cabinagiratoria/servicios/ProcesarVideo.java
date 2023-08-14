package com.example.cabinagiratoria.servicios;

import android.util.Log;

import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.example.cabinagiratoria.Model.MP3Utils;
import com.example.cabinagiratoria.Model.MP4Utils;
import com.example.cabinagiratoria.Model.Video;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ProcesarVideo {
    public void crearVideoFinal() {
        CompletableFuture<Void> idaFuture = ida();
        CompletableFuture<Void> vueltaFuture = vuelta();

        CompletableFuture.allOf(idaFuture, vueltaFuture).thenAccept((Void) -> {
            String videoIda = MP4Utils.getSelectedFileProcess();
            String videoRegreso = MP4Utils.getSelectedFileRever();
            String newArchivo = Video.createVideoFile("Final").getPath();

            String[] command = {"-y", "-i", videoIda, "-i", videoRegreso, "-filter_complex",
                    "[0:v][1:v]concat=n=2:v=1:a=0[a]",
                    "-map", "[a]", newArchivo};

            FFmpeg.executeAsync(command, new ExecuteCallback() {
                @Override
                public void apply(long executionId, int returnCode) {
                    Log.d("Return Final: ", returnCode + ": ********************************************************");
                    if (returnCode == 0) {
                        MP4Utils.setSelectedFileFinal(newArchivo);
                        MP4Utils.setSelectedFileWithAudio(cambiarVideoPorVideoConAudio());

                        eliminarVideos();
                    }
                }
            });
        }).exceptionally(ex -> {
            // Se ejecuta si ocurre algún error en ida() o vuelta()
            Log.d("Error en realintizarVideo(): ", ex.getMessage());
            return null;
        });
    }

    public static CompletableFuture<Void> ida() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        String video = MP4Utils.getSelectedFileVideo();
        String newArchivo = Video.createVideoFile("Ida").getPath();

        String[] command = {"-i", video, "-filter_complex",
                "[0:v]trim=3:6,setpts=PTS-STARTPTS[v1];" +
                        "[0:v]trim=6:8,setpts=0.5*(PTS-STARTPTS)[v2];" +
                        "[0:v]trim=8:11,setpts=2.0*(PTS-STARTPTS)[v3];" +
                        "[v1][v2][v3]concat=n=3:v=1:a=0[out]",
                "-map", "[out]", newArchivo};


        FFmpeg.executeAsync(command, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                Log.d("Return realintizarVideoDuplicado IDA: ", String.valueOf(returnCode));
                if (returnCode == 0) {
                    MP4Utils.setSelectedFileProcess(newArchivo);
                    future.complete(null); // Marcar el CompletableFuture como completado
                } else {
                    future.completeExceptionally(new RuntimeException("Error en ida()")); // Marcar el CompletableFuture como completado con error
                }
            }
        });

        return future;
    }

    public static CompletableFuture<Void> vuelta() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        String video = MP4Utils.getSelectedFileVideo();
        String newArchivo = Video.createVideoFile("Regreso").getPath();

        String[] command = {"-i", video, "-filter_complex",
                "[0:v]trim=3:6,setpts=PTS-STARTPTS[v1];" +
                        "[0:v]trim=6:8,setpts=0.5*(PTS-STARTPTS)[v2];" +
                        "[0:v]trim=8:11,setpts=2.0*(PTS-STARTPTS)[v3];" +
                        "[v1][v2][v3]concat=n=3:v=1:a=0,reverse[out]",
                "-map", "[out]", newArchivo};

        FFmpeg.executeAsync(command, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                Log.d("Return realintizarVideoDuplicado Regreso: ", String.valueOf(returnCode));
                if (returnCode == 0) {
                    MP4Utils.setSelectedFileRever(newArchivo);
                    future.complete(null); // Marcar el CompletableFuture como completado
                } else {
                    future.completeExceptionally(new RuntimeException("Error en vuelta()")); // Marcar el CompletableFuture como completado con error
                }
            }
        });

        return future;
    }

    public static String cambiarVideoPorVideoConAudio() {
        String video = MP4Utils.getSelectedFileFinal();
        String m4a = MP3Utils.getSelectedFileAudio();
        String nuevaCadenaAudio = m4a.replace(m4a.substring(0, m4a.indexOf('/', 1)), "");
        nuevaCadenaAudio = "/storage/emulated/0" + nuevaCadenaAudio;
        File newArchivo = Video.createVideoFile("Con-Audio");

        String[] c = {"-i", video, "-i", nuevaCadenaAudio, "-c:v", "copy", "-c:a", "aac",
                "-map", "0:v:0", "-map", "1:a:0", "-shortest",
                newArchivo.getPath()   };

        MergeVideo(c);

        return newArchivo.getAbsolutePath();
    }

    private static void MergeVideo(String[] co){
        FFmpeg.executeAsync(co, new ExecuteCallback() {
            @Override
            public void apply( long executionId, int returnCode ) {
                Log.d("Return ", String.valueOf(returnCode));
            }
        });
    }

    private static void eliminarVideos() {
        Video.eliminarVideo(MP4Utils.getSelectedFileVideo());
        Video.eliminarVideo(MP4Utils.getSelectedFileProcess());
        Video.eliminarVideo(MP4Utils.getSelectedFileFinal());
        Video.eliminarVideo(MP4Utils.getSelectedFileRever());
    }
}
