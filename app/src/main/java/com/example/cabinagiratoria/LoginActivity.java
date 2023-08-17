package com.example.cabinagiratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cabinagiratoria.Peticiones.ApiClientValidarUsuario;

import java.io.IOException;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtener referencias a los elementos de la interfaz de usuario
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void onLoginButtonClicked(View view) throws JSONException, IOException {
        // Obtener el nombre de usuario y contraseña ingresados
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();


        ApiClientValidarUsuario apiClient = new ApiClientValidarUsuario(this);
        apiClient.hacerPeticionAPI(username, password, new ApiClientValidarUsuario.ApiResponseListener() {
            @Override
            public void onResponse(boolean response) {
                if (response) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    mandarOtraActivity(username);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Log.d("Error: ", error);
                Toast.makeText(LoginActivity.this, "Ocurrio un error al iniciar sesion", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void mandarOtraActivity(String usuario) {
        Intent intent = new Intent(this, EscogerEventoActivity.class);
        intent.putExtra("nombreUsuario", usuario);
        startActivity(intent);
    }
}