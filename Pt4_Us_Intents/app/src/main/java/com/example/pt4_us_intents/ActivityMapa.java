package com.example.pt4_us_intents;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityMapa extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        Button btnMapa = findViewById(R.id.btnObrirMapa);
        btnMapa.setOnClickListener(v -> {
            Uri ubicacion = Uri.parse("geo:41.3851,2.1734?q=Barcelona");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, ubicacion);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(this, "No es pot obrir el mapa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
