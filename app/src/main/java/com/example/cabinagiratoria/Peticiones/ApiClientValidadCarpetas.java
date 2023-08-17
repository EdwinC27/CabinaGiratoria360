package com.example.cabinagiratoria.Peticiones;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;


public class ApiClientValidadCarpetas {

    private static final String API_URL = "http://3.128.181.152:8080/api/carpeta/?usuario="; // Reemplaza esto con la URL de la API que deseas usar
    private RequestQueue requestQueue;

    public ApiClientValidadCarpetas(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void hacerPeticionAPI(final String usuario, final ApiResponseListener listener) {
        String finalURL = API_URL + usuario;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, finalURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                });

        requestQueue.add(request);
    }

    public interface ApiResponseListener {
        void onResponse(String response);
        void onError(String error);
    }
}