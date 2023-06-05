package com.example.cabinagiratoria;

import android.os.Environment;
import android.util.Log;

import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoConMusica {
    FFmpeg fFmpeg;

    public static String cambiarVideoPorVideoConAudio() {
        String video = MP4Utils.getSelectedFileVideo();
        String m4a = MP3Utils.getSelectedFileAudio();
        String nuevaCadenaAudio = m4a.replace(m4a.substring(0, m4a.indexOf('/', 1)), "");
        nuevaCadenaAudio = "/storage/emulated/0" + nuevaCadenaAudio;
        File newArchivo = createVideoFile();

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

    private static File createVideoFile() {
        // Crea un archivo de video con un nombre único
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String videoFileName = "VIDEO_" + timeStamp + ".mp4";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File videoFile = new File(storageDir, videoFileName);
        return videoFile;
    }
}
