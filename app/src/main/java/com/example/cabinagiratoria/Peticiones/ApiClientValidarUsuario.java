package com.example.cabinagiratoria.Peticiones;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

public class ApiClientValidarUsuario {

    private static final String API_URL = "http://3.128.181.152:8080/api/users?user=";
    private RequestQueue requestQueue;


    public ApiClientValidarUsuario(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void hacerPeticionAPI(final String usuario, final String contrasena, final ApiResponseListener listener) {
        String finalURL = API_URL + usuario + "&password=" + contrasena;

        JsonRequest<Boolean> request = new JsonRequest<Boolean>(
                Request.Method.GET,
                finalURL,
                null,
                new Response.Listener<Boolean>() {
                    @Override
                    public void onResponse(Boolean response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                }
        ) {
            @Override
            protected Response<Boolean> parseNetworkResponse(NetworkResponse response) {
                try {
                    String responseBody = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    boolean booleanResponse = Boolean.parseBoolean(responseBody);
                    return Response.success(booleanResponse, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        requestQueue.add(request);
    }

    public interface ApiResponseListener {
        void onResponse(boolean response);
        void onError(String error);
    }

}
