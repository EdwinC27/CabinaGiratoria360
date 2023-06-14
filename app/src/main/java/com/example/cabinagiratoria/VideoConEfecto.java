package com.example.cabinagiratoria;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoConEfecto {
    public static void realintizarVideo() {
        ida();
        vuelta();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                unirVideos();
            }
        }, 50000); // 10000 milisegundos = 10 segundos
    }

    public static void ida() {
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
            public void apply(long executionId, int returnCode) {
                Log.d("Return realintizarVideoDuplicado IDA: ", String.valueOf(returnCode));
                if (returnCode == 0) {
                    MP4Utils.setSelectedFileProcess(newArchivo);
                }
            }
        });
    }

    public static void vuelta() {
        String video = MP4Utils.getSelectedFileVideo();
        String newArchivo = createVideoFileRegreso().getPath();

        String[] command = {"-i", video, "-filter_complex",
                "[0:v]trim=0:3,setpts=PTS-STARTPTS[v1];" +
                        "[0:v]trim=3:8,setpts=2.0*(PTS-STARTPTS)[v2];" +
                        "[0:v]trim=8:12,setpts=0.5*(PTS-STARTPTS)[v3];" +
                        "[v1][v2][v3]concat=n=3:v=1:a=0,reverse[out]",
                "-map", "[out]", newArchivo};


        FFmpeg.executeAsync(command, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                Log.d("Return realintizarVideoDuplicado Regreso: ", String.valueOf(returnCode));
                if (returnCode == 0) {
                    MP4Utils.setSelectedFileRever(newArchivo);
                }
            }
        });
    }

    public static File createVideoFileRegreso() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String videoFileName = "VIDEO_Vuelta" + timeStamp + ".mp4";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File videoFile = new File(storageDir, videoFileName);
        return videoFile;
    }

    public static void unirVideos() {
        String videoIda = MP4Utils.getSelectedFileProcess();
        String videoRegreso = MP4Utils.getSelectedFileRever();
        String newArchivo = createVideoFileFinal().getPath();


        String[] command = {"-y", "-i", videoIda, "-i", videoRegreso, "-filter_complex",
                "[0:v]scale=w=640:h=480:force_original_aspect_ratio=1[v0];[1:v]scale=w=640:h=480:force_original_aspect_ratio=1[v1];[v0][v1]concat=n=2:v=1[a]",
                "-map", "[a]", newArchivo};

        FFmpeg.executeAsync(command, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                Log.d("Return Final: ", returnCode + ": ********************************************************");
                if (returnCode == 0) {
                    MP4Utils.setSelectedFileFinal(newArchivo);
                    MP4Utils.setSelectedFileWithAudio(VideoConMusica.cambiarVideoPorVideoConAudio());

                }
            }
        });
    }

    public static File createVideoFileFinal() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String videoFileName = "VIDEO_Final" + timeStamp + ".mp4";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File videoFile = new File(storageDir, videoFileName);
        return videoFile;
    }
}
