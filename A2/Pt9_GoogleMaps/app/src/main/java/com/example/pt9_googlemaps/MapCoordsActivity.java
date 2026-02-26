package com.example.pt9_googlemaps;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapCoordsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_coords);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showCoordsDialog());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void showCoordsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Introdueix Coordenades");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final EditText etLat = new EditText(this);
        etLat.setHint("Latitud");
        layout.addView(etLat);

        final EditText etLng = new EditText(this);
        etLng.setHint("Longitud");
        layout.addView(etLng);

        builder.setView(layout);

        builder.setPositiveButton("Anar", (dialog, which) -> {
            try {
                double lat = Double.parseDouble(etLat.getText().toString());
                double lng = Double.parseDouble(etLng.getText().toString());
                LatLng pos = new LatLng(lat, lng);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(pos).title("Ubicació triada"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
            } catch (Exception e) {
                Toast.makeText(this, "Error en les coordenades", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel·lar", null);
        builder.show();
    }
}
