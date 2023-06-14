package com.example.cabinagiratoria;

import android.util.Log;

import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;

public class VideoConMusica {
    FFmpeg fFmpeg;

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

}
