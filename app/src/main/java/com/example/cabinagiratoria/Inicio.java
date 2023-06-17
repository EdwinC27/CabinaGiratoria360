package com.example.cabinagiratoria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_MP3 = 2;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button buttonSubirMP3 = findViewById(R.id.buttonSubirMP3);
        buttonSubirMP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si se tienen los permisos necesarios
                if (ContextCompat.checkSelfPermission(Inicio.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permiso otorgado, abrir el explorador de archivos
                    seleccionarArchivoMP3();
                } else {
                    // Permiso denegado, solicitar permisos
                    ActivityCompat.requestPermissions(Inicio.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                }
            }
        });
    }

    public void iniciarEvento(View view) {
        Intent intent = new Intent(this, GrabarVideo.class);
        startActivity(intent);
    }

    // Manejar la respuesta del usuario sobre los permisos solicitados
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso otorgado, el usuario puede subir archivos
                seleccionarArchivoMP3();
            } else {
                // Permiso denegado, el usuario no puede subir archivos
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para abrir el explorador de archivos
    private void seleccionarArchivoMP3() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg");
        startActivityForResult(intent, REQUEST_CODE_SELECT_MP3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_MP3 && resultCode == RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();
            MP3Utils.setSelectedFileAudio(selectedFileUri.getPath());
            mostrarMP3FueSeleccionado();
        }
    }

    private void mostrarMP3FueSeleccionado() {
        if(MP3Utils.getSelectedFileAudio() != null) Toast.makeText(this, "Cancion agregada", Toast.LENGTH_SHORT).show();
        if(MP3Utils.getSelectedFileAudio() == null) Toast.makeText(this, "No se pudo agregar la cancion ", Toast.LENGTH_SHORT).show();
    }
}