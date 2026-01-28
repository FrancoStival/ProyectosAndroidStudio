package com.example.pt7_bd_local;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText etNom, etCognoms, etTelefon, etMarca, etModel, etMatricula;
    Button btnAlta, btnBaixa, btnModificar, btnConsultar;

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

        myDb = new DatabaseHelper(this);

        etNom = findViewById(R.id.etNom);
        etCognoms = findViewById(R.id.etCognoms);
        etTelefon = findViewById(R.id.etTelefon);
        etMarca = findViewById(R.id.etMarca);
        etModel = findViewById(R.id.etModel);
        etMatricula = findViewById(R.id.etMatricula);

        btnAlta = findViewById(R.id.btnAlta);
        btnBaixa = findViewById(R.id.btnBaixa);
        btnModificar = findViewById(R.id.btnModificar);
        btnConsultar = findViewById(R.id.btnConsultar);

        addData();
        deleteData();
        updateData();
        viewData();
    }

    public void addData() {
        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricula = etMatricula.getText().toString().trim();
                if (matricula.isEmpty()) {
                    Toast.makeText(MainActivity.this, "La matrícula és obligatòria", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Control d'errors: comprovar si ja existeix
                Cursor res = myDb.getData(matricula);
                if (res != null && res.getCount() > 0) {
                    Toast.makeText(MainActivity.this, "Error: Ja existeix un vehicle amb aquesta matrícula", Toast.LENGTH_LONG).show();
                    res.close();
                    return;
                }

                boolean isInserted = myDb.insertData(
                        etNom.getText().toString(),
                        etCognoms.getText().toString(),
                        etTelefon.getText().toString(),
                        etMarca.getText().toString(),
                        etModel.getText().toString(),
                        matricula
                );

                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Vehicle registrat correctament", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "Error al registrar el vehicle", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData() {
        btnBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricula = etMatricula.getText().toString().trim();
                if (matricula.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Indica la matrícula per donar de baixa", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer deletedRows = myDb.deleteData(matricula);
                if (deletedRows > 0) {
                    Toast.makeText(MainActivity.this, "Vehicle eliminat", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "No s'ha trobat cap vehicle amb aquesta matrícula", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateData() {
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricula = etMatricula.getText().toString().trim();
                if (matricula.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Indica la matrícula per modificar", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdate = myDb.updateData(
                        etNom.getText().toString(),
                        etCognoms.getText().toString(),
                        etTelefon.getText().toString(),
                        etMarca.getText().toString(),
                        etModel.getText().toString(),
                        matricula
                );

                if (isUpdate) {
                    Toast.makeText(MainActivity.this, "Dades actualitzades", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error en actualitzar (Matrícula no trobada)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewData() {
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricula = etMatricula.getText().toString().trim();
                if (matricula.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Indica la matrícula a consultar", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor res = myDb.getData(matricula);
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No s'han trobat dades", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (res.moveToFirst()) {
                    etNom.setText(res.getString(1));
                    etCognoms.setText(res.getString(2));
                    etTelefon.setText(res.getString(3));
                    etMarca.setText(res.getString(4));
                    etModel.setText(res.getString(5));
                    Toast.makeText(MainActivity.this, "Dades carregades", Toast.LENGTH_SHORT).show();
                }
                res.close();
            }
        });
    }

    private void clearFields() {
        etNom.setText("");
        etCognoms.setText("");
        etTelefon.setText("");
        etMarca.setText("");
        etModel.setText("");
        etMatricula.setText("");
    }
}
