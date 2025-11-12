package com.example.pt5;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSettings = findViewById(R.id.btn_open_settings);
        btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        Button btnShow = findViewById(R.id.btn_show_prefs);
        btnShow.setOnClickListener(v -> showCurrentPreferences());

        // Permisos runtime para notificaciones en Android 13+
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    // manejar permiso si hace falta
                });
        ensureNotificationPermissionIfNeeded();
    }

    private void ensureNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void showCurrentPreferences() {
        android.content.SharedPreferences prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("full_name", "");
        String movie = prefs.getString("fav_movie", "");
        boolean newsOn = prefs.getBoolean("news_notification", false);
        Set<String> races = prefs.getStringSet("reply", null);

        StringBuilder sb = new StringBuilder();
        sb.append("Nom: ").append(name == null ? "" : name).append("\n");
        sb.append("Pel·lícula: ").append(movie == null ? "" : movie).append("\n");
        sb.append("Notícies: ").append(newsOn ? "On" : "Off").append("\n");
        sb.append("Races: ");
        if (races == null || races.isEmpty()) sb.append("Cap");
        else sb.append(String.join(", ", races));

        new AlertDialog.Builder(this)
                .setTitle("Preferències actuals")
                .setMessage(sb.toString())
                .setPositiveButton("D'acord", null)
                .show();
    }

}