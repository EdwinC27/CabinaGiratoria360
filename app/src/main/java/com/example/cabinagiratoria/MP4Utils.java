package com.example.cabinagiratoria;

public class MP4Utils {
    private static String selectedFileUri;

    private static String selectedFileWithAudio;

    private static String selectedFileProcess;


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

    public static void setSelectedFileProcess(String uri) {
        selectedFileProcess = uri;
    }

    public static String getSelectedFileProcess() {
        return selectedFileProcess;
    }
}
