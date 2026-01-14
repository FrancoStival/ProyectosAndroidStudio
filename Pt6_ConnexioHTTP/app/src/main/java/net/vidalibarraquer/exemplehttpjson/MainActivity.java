package net.vidalibarraquer.exemplehttpjson;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<Personatge> elements = new ArrayList<>();
    private RequestQueue queue = null;

    public List<Personatge> getElements() {
        return elements;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(elements);

        RecyclerView viewLlista = findViewById(R.id.viewLlista);
        viewLlista.setAdapter(adapter);

        // Quan es fa click al botó es carreguen les dades i s'actualitza
        // el recycler view
        findViewById(R.id.btnLoadData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiHaConnexio())
                    loadData(viewLlista, "https://www.vidalibarraquer.net/android/DragonBall.json");
                else
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * Comprova si el dispositiu té connexió
     * @return cert si en té i fals si no en té
     */
    private boolean hiHaConnexio() {
        boolean resultat = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Comprovem la versió del dispositiu Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    resultat = true;
                }
            }
        } else { //versions anteriors d'Android
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                resultat = true;
            } else {
                resultat = false;
            }
        }

        return resultat;
    }

    /**
     * Carega les dades que es troben a la url indicada
     * @param viewLlista el recyclerview que s'ha d'actualitzar
     * @param url Una url on hi ha un objecte JSON
     */
    private void loadData(RecyclerView viewLlista, String url) {
        if ( queue == null )
            queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // S'esborra la llista
                            elements.clear();
                            // Obtenim l'array que té per nom data
                            JSONArray jsonArray = response.getJSONArray("data");
                            //Recorrem tots els elements
                            for (int i = 0; i < jsonArray.length(); i++) {
                                // Afegim el personatge a la llista
                                Personatge personatge = new Personatge(
                                        jsonArray.getJSONObject(i).getString("name"),
                                        jsonArray.getJSONObject(i).getString("planet"),
                                        jsonArray.getJSONObject(i).getString("imatge")
                                );
                                elements.add(personatge);
                            }
                            viewLlista.getAdapter().notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error en obtenir dades", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);
    }

}