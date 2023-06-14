package com.example.cabinagiratoria;

public class MP4Utils {
    private static String selectedFileUri;

    private static String selectedFileWithAudio;

    private static String selectedFileProcess;

    private static String selectedFileRever;

    private static String selectedFileFinal;

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

    public static void setSelectedFileRever(String uri) {
        selectedFileRever = uri;
    }

    public static String getSelectedFileRever() {
        return selectedFileRever;
    }

    public static void setSelectedFileFinal(String uri) {
        selectedFileFinal = uri;
    }

    public static String getSelectedFileFinal() {
        return selectedFileFinal;
    }
}
