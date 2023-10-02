package com.bitalaluis.funcionalidad;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String apiUrl = "http://192.168.1.188/pruebas/apiBack.php";
    private RequestQueue requestQueue;
    Context context;

    EditText editTextUsername, editTextPassword;
    CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        requestQueue = Volley.newRequestQueue(context);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");
            editTextUsername.setText(savedUsername);
            editTextPassword.setText(savedPassword);
            checkBoxRememberMe.setChecked(true);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void login(View view) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {
            iniciarSesion(username, password);
        } else {
            Toast.makeText(LoginActivity.this, "Por favor, ingresa nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarCredenciales(String username, String password, boolean rememberMe) {
        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", rememberMe ? password : "");
        editor.putBoolean("rememberMe", rememberMe);
        editor.apply();
    }

    private void iniciarSesion(final String username, final String password) {
        StringRequest requestLogin = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String mensaje = jsonResponse.getString("mensaje");

                    Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();

                    if (mensaje.equals("Inicio exitoso")) {
                        boolean rememberMe = checkBoxRememberMe.isChecked();
                        guardarCredenciales(username, password, rememberMe);

                        // Redirige a la actividad que desees después del inicio de sesión exitoso
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error en el procesamiento de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(LoginActivity.this, "Error al conectar con el servidor.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Error en el servidor.", Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("opcion", "8"); // Cambia el valor de "8" según tu API
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(requestLogin);
    }
}
