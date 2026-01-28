package net.vidalibarraquer.exemplesqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<VideoGame> videojocs;

    public EditText etNom;
    public EditText etEmpresa;
    public Button btnInserir;
    public RecyclerView viewLlista;

    ManegadorDades db = new ManegadorDades(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videojocs = db.getAllVideoGames();
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(videojocs);
        viewLlista = (RecyclerView) findViewById(R.id.viewLlista);
        viewLlista.setAdapter(adapter);

        etNom = (EditText) findViewById(R.id.etNom);
        etEmpresa = (EditText) findViewById(R.id.etEmpresa);
        btnInserir = (Button) findViewById(R.id.add);


        btnInserir.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.add) {
            VideoGame vg = new VideoGame();
            vg.setNom(etNom.getText().toString());
            vg.setEmpresa(etEmpresa.getText().toString());
            db.addVideogames(vg);
            etNom.setText("");
            etEmpresa.setText("");
            videojocs.add(vg);
            //Notificar que s'ha afegit un element
            viewLlista.getAdapter().notifyItemInserted(videojocs.size()-1);

        }
    }

}
