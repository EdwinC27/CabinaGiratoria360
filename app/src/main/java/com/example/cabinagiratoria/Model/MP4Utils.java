package com.example.cabinagiratoria.Model;

public class MP4Utils {
    private static String selectedFileUri;

    public static void setSelectedFileVideo(String uri) {
        selectedFileUri = uri;
    }

    public static String getSelectedFileVideo() {
        return selectedFileUri;
    }

}