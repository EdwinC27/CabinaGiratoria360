package com.example.cabinagiratoria.Model;

public class MP4Utils {
    private static String selectedFileUri;
    private static String selectedFileRever;
    private static String selectedFileProcess;
    private static String selectedFileFinal;
    private static String selectedFileWithAudio;

    public static void setSelectedFileVideo(String uri) {
        selectedFileUri = uri;
    }

    public static String getSelectedFileVideo() {
        return selectedFileUri;
    }

    public static String getSelectedFileRever() {
        return selectedFileRever;
    }

    public static void setSelectedFileRever(String selectedFileRever) {
        MP4Utils.selectedFileRever = selectedFileRever;
    }

    public static String getSelectedFileProcess() {
        return selectedFileProcess;
    }

    public static void setSelectedFileProcess(String selectedFileProcess) {
        MP4Utils.selectedFileProcess = selectedFileProcess;
    }

    public static String getSelectedFileFinal() {
        return selectedFileFinal;
    }

    public static void setSelectedFileFinal(String selectedFileFinal) {
        MP4Utils.selectedFileFinal = selectedFileFinal;
    }

    public static String getSelectedFileWithAudio() {
        return selectedFileWithAudio;
    }

    public static void setSelectedFileWithAudio(String selectedFileWithAudio) {
        MP4Utils.selectedFileWithAudio = selectedFileWithAudio;
    }
}