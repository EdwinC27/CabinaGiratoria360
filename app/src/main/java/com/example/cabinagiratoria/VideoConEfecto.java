package com.example.cabinagiratoria;

import android.util.Log;

import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

public class VideoConEfecto {
    public static void realintizarVideo() {
        String video = MP4Utils.getSelectedFileVideo();
        String newArchivo = Video.createVideoFile().getPath();

        String[] command = {"-i", video, "-filter_complex",
                "[0:v]trim=0:3,setpts=PTS-STARTPTS[v1];" +
                "[0:v]trim=3:8,setpts=2.0*(PTS-STARTPTS)[v2];" +
                "[0:v]trim=8:12,setpts=0.5*(PTS-STARTPTS)[v3];" +
                "[v1][v2][v3]concat=n=3:v=1:a=0[out]",
                "-map", "[out]", newArchivo};



        FFmpeg.executeAsync(command, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode ) {
                Log.d("Return realintizarVideoDuplicado: ", String.valueOf(returnCode));
                if (returnCode == 0) {
                    MP4Utils.setSelectedFileProcess(newArchivo);
                    MP4Utils.setSelectedFileWithAudio(VideoConMusica.cambiarVideoPorVideoConAudio());
                }
            }
        });
    }

}
