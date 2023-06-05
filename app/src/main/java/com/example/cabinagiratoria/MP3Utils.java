package com.example.cabinagiratoria;

public class MP3Utils {
    private static String selectedFileUri;

    public static void setSelectedFileAudio(String uri) {
        selectedFileUri = uri;
    }

    public static String getSelectedFileAudio() {
        return selectedFileUri;
    }
}

