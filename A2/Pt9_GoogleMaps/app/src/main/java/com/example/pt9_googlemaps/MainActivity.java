package com.example.pt9_googlemaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCoords = findViewById(R.id.btnCoords);
        Button btnCity = findViewById(R.id.btnCity);
        Button btnCurrent = findViewById(R.id.btnCurrent);

        btnCoords.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapCoordsActivity.class)));
        btnCity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapCityActivity.class)));
        btnCurrent.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapCurrentActivity.class)));
    }
}
