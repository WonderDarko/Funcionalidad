package com.bitalaluis.funcionalidad;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ListBookFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;
    Context context;
    private RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_book, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bookList = new ArrayList<>();
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(getActivity());

        // Realiza la solicitud a la API
        fetchBooksFromAPI();

        return view;
    }

    private void fetchBooksFromAPI() {
        String url = "http://192.168.1.188/pruebas/apiBack.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String titulo = jsonObject.getString("titulo");
                        String subtitulo = jsonObject.getString("subtitulo");
                        String autor = jsonObject.getString("autor");
                        String isbn = jsonObject.getString("isbn");
                        String anio = jsonObject.getString("anio_publicacion");
                        float precio = (float) jsonObject.getDouble("precio");

                        Book book = new Book(titulo, subtitulo, autor, isbn, anio, precio);
                        bookList.add(book);
                    }

                    // Notificar al adaptador que los datos han cambiado
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONError", "Error al parsear JSON: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar el error de la solicitud
                Log.e("VolleyError", "Error al realizar la solicitud POST: " + error.toString());
            }
        }) {
            // Agregar parámetros POST si es necesario
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("opcion", "1");
                // Agregar más parámetros si es necesario
                return params;
            }
        };

        // Agregar la solicitud a la cola de Volley
        requestQueue.add(request);
    }
}