package com.example.pt4_us_intents;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityEmail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        Button btnEnviar = findViewById(R.id.btnEnviarEmail);
        btnEnviar.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"exemple@correu.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Prova d'intent");

            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            } else {
                Toast.makeText(this, "No hi ha cap app de correu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
