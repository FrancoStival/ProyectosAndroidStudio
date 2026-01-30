package com.example.pt7_1_room_bbdd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pt7_1_room_bbdd.db.AppDatabase;
import com.example.pt7_1_room_bbdd.model.Tag;
import com.example.pt7_1_room_bbdd.model.Tasca;
import com.example.pt7_1_room_bbdd.model.TascaTag;
import com.example.pt7_1_room_bbdd.model.TascaWithTags;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private TascaAdapter adapter;
    private Spinner spinnerFilter;
    private List<Tag> allTags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasques);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TascaAdapter();
        recyclerView.setAdapter(adapter);

        spinnerFilter = findViewById(R.id.spinnerFilter);
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadTasques();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        findViewById(R.id.btnAfegirTasca).setOnClickListener(v -> showAfegirTascaDialog());
        findViewById(R.id.btnAfegirTag).setOnClickListener(v -> showAfegirTagDialog());

        loadTags();
        loadTasques();
    }

    private void loadTags() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            allTags = db.tagDao().getAllTags();
            runOnUiThread(() -> {
                List<String> tagNames = new ArrayList<>();
                tagNames.add("Totes");
                for (Tag t : allTags) tagNames.add(t.nom);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tagNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFilter.setAdapter(adapter);
            });
        });
    }

    private void loadTasques() {
        int selectedPos = spinnerFilter.getSelectedItemPosition();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<TascaWithTags> tasques;
            if (selectedPos <= 0) {
                tasques = db.tascaDao().getAllTasquesWithTags();
            } else {
                int tagId = allTags.get(selectedPos - 1).id;
                tasques = db.tascaDao().getTasquesByTag(tagId);
            }
            runOnUiThread(() -> adapter.setTasques(tasques));
        });
    }

    private void showAfegirTascaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nova Tasca");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_afegir_tasca, null);
        EditText etTitol = view.findViewById(R.id.etTitol);
        EditText etDesc = view.findViewById(R.id.etDescripcio);
        Spinner spEstat = view.findViewById(R.id.spEstat);
        LinearLayout tagsContainer = view.findViewById(R.id.tagsContainer);

        String[] estats = {"pendent", "en procés", "completada"};
        ArrayAdapter<String> estatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estats);
        spEstat.setAdapter(estatAdapter);

        List<CheckBox> checkBoxes = new ArrayList<>();
        for (Tag tag : allTags) {
            CheckBox cb = new CheckBox(this);
            cb.setText(tag.nom);
            cb.setTag(tag.id);
            tagsContainer.addView(cb);
            checkBoxes.add(cb);
        }

        builder.setView(view);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String titol = etTitol.getText().toString();
            String desc = etDesc.getText().toString();
            String estat = spEstat.getSelectedItem().toString();
            long now = System.currentTimeMillis();

            if (titol.isEmpty()) return;

            AppDatabase.databaseWriteExecutor.execute(() -> {
                long tascaId = db.tascaDao().insertTasca(new Tasca(titol, desc, estat, now, now));
                for (CheckBox cb : checkBoxes) {
                    if (cb.isChecked()) {
                        db.tascaDao().insertTascaTag(new TascaTag((int) tascaId, (int) cb.getTag()));
                    }
                }
                loadTasques();
            });
        });
        builder.setNegativeButton("Cancel·lar", null);
        builder.show();
    }

    private void showAfegirTagDialog() {
        EditText et = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Nou Tag")
                .setView(et)
                .setPositiveButton("Afegir", (dialog, which) -> {
                    String nom = et.getText().toString();
                    if (!nom.isEmpty()) {
                        AppDatabase.databaseWriteExecutor.execute(() -> {
                            db.tagDao().insertTag(new Tag(nom));
                            loadTags();
                        });
                    }
                })
                .setNegativeButton("Cancel·lar", null)
                .show();
    }
}
