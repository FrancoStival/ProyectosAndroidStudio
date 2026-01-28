package com.example.prova1_stival_franco;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LlistaJocsActivity extends AppCompatActivity {
    private List<Object> elements = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;
    private RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_jocs);

        String url = getIntent().getStringExtra("url");

        RecyclerView viewLlista = findViewById(R.id.viewLlista);
        viewLlista.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(elements);
        viewLlista.setAdapter(adapter);

        if (hiHaConnexio()) {
            loadData(url);
        } else {
            Toast.makeText(this, "No hi ha connexió a internet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hiHaConnexio() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN));
        } else {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
    }

    private void loadData(String url) {
        if (queue == null) queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(url,
                response -> {
                    try {
                        elements.clear();
                            JSONArray jsonArray = response.getJSONArray("games");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                elements.add(new Juego(
                                        obj.getInt("id"),
                                        obj.getString("slug"),
                                        obj.getString("name"),
                                        obj.getString("released"),
                                        obj.getDouble("rating")
                                ));
                            }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(LlistaJocsActivity.this, "Error en obtenir dades", Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}