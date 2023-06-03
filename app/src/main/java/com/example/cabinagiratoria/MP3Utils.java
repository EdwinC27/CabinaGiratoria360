package com.example.cabinagiratoria;

import android.net.Uri;

public class MP3Utils {
    private static Uri selectedFileUri;

    public static void setSelectedFileUri(Uri uri) {
        selectedFileUri = uri;
    }

    public static Uri getSelectedFileUri() {
        return selectedFileUri;
    }
}

