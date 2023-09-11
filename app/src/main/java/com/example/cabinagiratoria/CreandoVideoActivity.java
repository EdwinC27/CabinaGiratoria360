package com.example.cabinagiratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CreandoVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creando_video);

        // Obtén una referencia al ImageView
        ImageView gifImageView = findViewById(R.id.gifImageView);

        // Carga el GIF utilizando Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.cargando) // Reemplaza "tu_gif" con el nombre de tu archivo GIF en res/drawable
                .into(gifImageView);
    }
}