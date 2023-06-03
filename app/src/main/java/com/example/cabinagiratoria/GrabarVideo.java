package com.example.cabinagiratoria;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GrabarVideo extends AppCompatActivity {
    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int DURATION_LIMIT_SECONDS = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_video);

        // Validación de permisos
        if (ContextCompat.checkSelfPermission(GrabarVideo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GrabarVideo.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GrabarVideo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
            Toast.makeText(this, "La aplicacion no cuenta con permisos",  Toast.LENGTH_SHORT).show();
        }
    }

    public void tomarVideo(View view) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, DURATION_LIMIT_SECONDS);

        // Generar una URI de archivo segura utilizando FileProvider
        Uri videoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", createVideoFile());

        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);

        // Concede permisos de lectura a otras aplicaciones
        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(takeVideoIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, videoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Archivo guardado", Toast.LENGTH_SHORT).show();
        }
    }

    private File createVideoFile() {
        // Crea un archivo de video con un nombre único
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String videoFileName = "VIDEO_" + timeStamp + ".mp4";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File videoFile = new File(storageDir, videoFileName);
        return videoFile;
    }
}


