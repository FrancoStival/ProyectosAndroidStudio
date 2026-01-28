package com.example.pt2_conversor_dunitats_amb_intents;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LongitutActivity extends AppCompatActivity {

    private EditText etValorEntrada;
    private RadioGroup rgOrigen, rgDesti;
    private TextView tvResultat;
    private Button btnConvertir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longitut);

        etValorEntrada = findViewById(R.id.etValorEntradaLongitud);
        rgOrigen = findViewById(R.id.rgUnitatOrigenLongitud);
        rgDesti = findViewById(R.id.rgUnitatDestiLongitud);
        btnConvertir = findViewById(R.id.btnConvertirLongitud);
        tvResultat = findViewById(R.id.tvResultatLongitud);

        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertirLongitud();
            }
        });
    }

    private void convertirLongitud() {
        String valorTexto = etValorEntrada.getText().toString().trim();

        if (valorTexto.isEmpty()) {
            etValorEntrada.setError(getString(R.string.error_valor_requerit));
            return;
        }

        double valorEntrada;
        try {
            valorEntrada = Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            etValorEntrada.setError(getString(R.string.error_valor_invalid));
            return;
        }

        int idOrigen = rgOrigen.getCheckedRadioButtonId();
        int idDesti = rgDesti.getCheckedRadioButtonId();

        if (idOrigen == -1 || idDesti == -1) {
            Toast.makeText(this, getString(R.string.error_selecciona_unitats), Toast.LENGTH_SHORT).show();
            return;
        }

        double valorEnMetres = convertirAmetres(valorEntrada, idOrigen);
        double resultat = convertirDesdeMetres(valorEnMetres, idDesti);

        String simbolDesti = obtenirSimbolUnitat(idDesti);

        String resultatFormatat = String.format("%.2f", resultat);
        String resultatText = getString(R.string.resultat_prefix) + " " + resultatFormatat + " " + simbolDesti;
        tvResultat.setText(resultatText);
    }

    private double convertirAmetres(double valor, int idOrigen) {
        if (idOrigen == R.id.rbKmOrigen) return valor * 1000;
        if (idOrigen == R.id.rbMillaOrigen) return valor * 1609.34;
        if (idOrigen == R.id.rbIardaOrigen) return valor * 0.9144;
        if (idOrigen == R.id.rbPolzadaOrigen) return valor * 0.0254;
        return 0;
    }

    private double convertirDesdeMetres(double valorMetres, int idDesti) {
        if (idDesti == R.id.rbKmDesti) return valorMetres / 1000;
        if (idDesti == R.id.rbMillaDesti) return valorMetres / 1609.34;
        if (idDesti == R.id.rbIardaDesti) return valorMetres / 0.9144;
        if (idDesti == R.id.rbPolzadaDesti) return valorMetres / 0.0254;
        return 0;
    }

    private String obtenirSimbolUnitat(int idRadioButton) {
        if (idRadioButton == R.id.rbKmDesti) return getString(R.string.simbol_km);
        if (idRadioButton == R.id.rbMillaDesti) return getString(R.string.simbol_mi);
        if (idRadioButton == R.id.rbIardaDesti) return getString(R.string.simbol_yd);
        if (idRadioButton == R.id.rbPolzadaDesti) return getString(R.string.simbol_in_unit);
        return "";
    }
}
