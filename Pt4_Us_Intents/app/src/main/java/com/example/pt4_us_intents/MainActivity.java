package com.example.pt4_us_intents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Button btnAlarma = findViewById(R.id.btnCrearAlarma);
        Button btnIntent2 = findViewById(R.id.btnIntent2);

        btnAlarma.setOnClickListener(v -> {
            Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
            alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma de prova");
            alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, 8);
            alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, 30);

            if (alarmIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(alarmIntent);
            } else {
                Toast.makeText(this, "No es pot crear l'alarma", Toast.LENGTH_SHORT).show();
            }
        });

        btnIntent2.setOnClickListener(v -> {
            Intent navegadorIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.android.com"));
            if (navegadorIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(navegadorIntent);
            } else {
                Toast.makeText(this, "No es pot obrir el navegador", Toast.LENGTH_SHORT).show();
            }
        });
    }
}