package com.example.cabinagiratoria;

public class MP4Utils {
    private static String selectedFileUri;

    private static String selectedFileWithAudio;


    public static void setSelectedFileVideo(String uri) {
        selectedFileUri = uri;
    }

    public static String getSelectedFileVideo() {
        return selectedFileUri;
    }


    public static void setSelectedFileWithAudio(String uri) {
        selectedFileWithAudio = uri;
    }

    public static String getSelectedFileWithAudio() {
        return selectedFileWithAudio;
    }
}
