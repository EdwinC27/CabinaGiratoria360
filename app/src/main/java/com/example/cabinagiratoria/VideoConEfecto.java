package com.example.cabinagiratoria;

import android.util.Log;

import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.util.concurrent.CompletableFuture;


public class VideoConEfecto {

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

}
