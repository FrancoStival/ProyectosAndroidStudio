package com.example.pt11_anuncios;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {
        });

        Button btnBanner = findViewById(R.id.btnBanner);
        Button btnInterstitial = findViewById(R.id.btnInterstitial);

        btnBanner.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
            } else {
                Toast.makeText(this, "No hi ha connexió a Internet per carregar el Banner", Toast.LENGTH_SHORT).show();
            }
        });

        btnInterstitial.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                startActivity(new Intent(MainActivity.this, InterstitialActivity.class));
            } else {
                Toast.makeText(this, "No hi ha connexió a Internet per carregar l'Interstitial", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}