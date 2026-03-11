package com.example.firebasedemo;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityNota";
    private static final String dbUrl = "https://fir-demo-c4788-default-rtdb.europe-west1.firebasedatabase.app";


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
        System.out.println("pree leer notas !");

        crearNotes();
        llegirNotas();
    }

    private void crearNotes() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance(dbUrl).getReference("notas");

        Map<String, Object> nota1 = new HashMap<>();
        nota1.put("titulo", "Comprar pan");
        nota1.put("contenido", "Recuerda de comprar pan en el supermercado");
        nota1.put("importante", true);

        Map<String, Object> nota2 = new HashMap<>();
        nota2.put("titulo", "Reunión de trabajo");
        nota2.put("contenido", "Reunión con el equipo de desarrollo a las 10:00 AM");
        nota2.put("importante", true);

        dbRef.push().setValue(nota1);
        dbRef.push().setValue(nota2);
    }


    private void llegirNotas() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance(dbUrl).getReference("notas");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String ultimaNotaId = null;
                    for (DataSnapshot notaSnap : snapshot.getChildren()) {
                        String titulo = notaSnap.child("titulo").getValue(String.class);
                        String contenido = notaSnap.child("contenido").getValue(String.class);
                        Boolean importante = notaSnap.child("importante").getValue(Boolean.class);

                        ultimaNotaId = notaSnap.getKey();
                        Log.d(TAG, "Nota ID: " + ultimaNotaId);
                        Log.d(TAG, "Título: " + titulo);
                        Log.d(TAG, "Contenido: " + contenido);
                        Log.d(TAG, "Importante: " + importante);
                        Log.d(TAG, "-----------------------");
                    }
                    actualizarNota(ultimaNotaId,"Titulo 1", "contenido ... ", false);
                    //borrarNota(ultimaNotaId);
                } else {
                    Log.d(TAG, "No hay notas");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Error leyendo notas: " + error.getMessage());
            }
        });
    }

    private void borrarNota(String notaId) {
        Log.d(TAG, "Borrar nota");
        DatabaseReference dbRef = FirebaseDatabase.getInstance(dbUrl).getReference("notas");
        // Eliminar la nota
        dbRef.child(notaId).removeValue()
                .addOnSuccessListener(aVoid -> {
                    // s'ha eliminat correctament
                })
                .addOnFailureListener(e -> {
                    // Error al eliminar
                });
    }

    private void actualizarNota(String notaId, String nuevoTitulo, String nuevoContenido, Boolean nuevoImportante) {
        Log.d(TAG, "Actualizar nota: " + notaId);

        DatabaseReference dbRef = FirebaseDatabase.getInstance(dbUrl).getReference("notas").child(notaId);

        Map<String, Object> cambios = new HashMap<>();
        if (nuevoTitulo != null) cambios.put("titulo", nuevoTitulo);
        if (nuevoContenido != null) cambios.put("contenido", nuevoContenido);
        if (nuevoImportante != null) cambios.put("importante", nuevoImportante);

        dbRef.updateChildren(cambios)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Nota actualizada correctamente");
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error actualizando nota", e);
                });
    }
}