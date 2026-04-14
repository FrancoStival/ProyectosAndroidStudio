package com.example.pt11_anuncios;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private final String TAG = "InterstitialActivity";
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        tvInfo = findViewById(R.id.tvInterstitialInfo);

        loadInterstitialAd();
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        // Sample Interstitial Ad Unit ID
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        tvInfo.setText("Anunci carregat. Es mostrarà ara...");
                        
                        // Mostrem l'anunci immediatament quan es carregui, segons el requisit
                        mInterstitialAd.show(InterstitialActivity.this);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                        tvInfo.setText("Error en carregar l'anunci.");
                        Toast.makeText(InterstitialActivity.this, "Error al carregar l'anunci", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}