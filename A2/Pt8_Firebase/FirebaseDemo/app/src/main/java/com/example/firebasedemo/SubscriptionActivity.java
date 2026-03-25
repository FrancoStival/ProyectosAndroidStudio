package com.example.firebasedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.Normalizer;

public class SubscriptionActivity extends AppCompatActivity {

    private static final String TAG = "SubscriptionActivity";
    private Spinner spinnerGroups;
    private Button btnAddSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        spinnerGroups = findViewById(R.id.spinnerGroups);
        btnAddSubscription = findViewById(R.id.btnAddSubscription);

        String[] groups = {
                "Administració i Gestió",
                "Comerç i Màrqueting",
                "Informàtica i Comunicacions",
                "Serveis a la Comunitat"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroups.setAdapter(adapter);

        btnAddSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedGroup = spinnerGroups.getSelectedItem().toString();
                subscribeToTopic(selectedGroup);
            }
        });
    }

    private void subscribeToTopic(String topic) {
        // Firebase topics only allow [a-zA-Z0-9-_.~%]+
        // We must remove accents and special characters like 'ç'
        String sanitizedTopic = normalizeTopic(topic);
        
        Log.d(TAG, "Subscribing to sanitized topic: " + sanitizedTopic);

        FirebaseMessaging.getInstance().subscribeToTopic(sanitizedTopic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SubscriptionActivity.this, 
                                    "Subscrit correctament a: " + topic, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "Subscription failed", task.getException());
                            Toast.makeText(SubscriptionActivity.this, 
                                    "Error al subscriure's: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private String normalizeTopic(String input) {
        // Remove accents
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        
        // Replace 'ç' specifically if normalization didn't catch it in a way we want (usually it does)
        normalized = normalized.replace("ç", "c").replace("Ç", "C");
        
        // Replace spaces and special characters with underscore
        normalized = normalized.replaceAll("[^a-zA-Z0-9]", "_");
        
        // Remove multiple underscores and convert to lowercase
        return normalized.replaceAll("_+", "_").toLowerCase();
    }
}
