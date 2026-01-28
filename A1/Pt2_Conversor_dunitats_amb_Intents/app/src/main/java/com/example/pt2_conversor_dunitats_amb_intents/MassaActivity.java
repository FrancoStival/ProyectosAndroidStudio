package com.example.pt2_conversor_dunitats_amb_intents;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MassaActivity extends AppCompatActivity {

    private EditText etValorEntrada;
    private RadioGroup rgOrigen, rgDesti;
    private Button btnConvertir;
    private TextView tvResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massa);

        etValorEntrada = findViewById(R.id.etValorEntradaMassa);
        rgOrigen = findViewById(R.id.rgUnitatOrigenMassa);
        rgDesti = findViewById(R.id.rgUnitatDestiMassa);
        btnConvertir = findViewById(R.id.btnConvertirMassa);
        tvResultat = findViewById(R.id.tvResultatMassa);

        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertirMassa();
            }
        });
    }

    private void convertirMassa() {
        String valorText = etValorEntrada.getText().toString().trim();

        if (valorText.isEmpty()) {
            etValorEntrada.setError(getString(R.string.error_valor_requerit));
            return;
        }

        double valorEntrada;
        try {
            valorEntrada = Double.parseDouble(valorText);
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

        double valorEnGrams = convertirAgrams(valorEntrada, idOrigen);
        double resultat = convertirDesdeGrams(valorEnGrams, idDesti);

        String resultatFormatat = String.format("%.2f", resultat);

        String simbolDesti = obtenirSimbolUnitat(idDesti);

        String resultatText = getString(R.string.resultat_prefix) + " " + resultatFormatat + " " + simbolDesti;
        tvResultat.setText(resultatText);
    }

    private double convertirAgrams(double valor, int idOrigen) {
        if (idOrigen == R.id.rbKgOrigenMassa) return valor * 1000;
        if (idOrigen == R.id.rbLliuraOrigenMassa) return valor * 453.592;
        if (idOrigen == R.id.rbUncaOrigenMassa) return valor * 28.3495;
        if (idOrigen == R.id.rbStoneOrigenMassa) return valor * 6350.29;
        return 0;
    }

    private double convertirDesdeGrams(double valorGrams, int idDesti) {
        if (idDesti == R.id.rbKgDestiMassa) return valorGrams / 1000;
        if (idDesti == R.id.rbLliuraDestiMassa) return valorGrams / 453.592;
        if (idDesti == R.id.rbUncaDestiMassa) return valorGrams / 28.3495;
        if (idDesti == R.id.rbStoneDestiMassa) return valorGrams / 6350.29;
        return 0;
    }

    private String obtenirSimbolUnitat(int idRadioButton) {
        if (idRadioButton == R.id.rbKgDestiMassa) return getString(R.string.simbol_kg);
        if (idRadioButton == R.id.rbLliuraDestiMassa) return getString(R.string.simbol_lb);
        if (idRadioButton == R.id.rbUncaDestiMassa) return getString(R.string.simbol_oz);
        if (idRadioButton == R.id.rbStoneDestiMassa) return getString(R.string.simbol_st);
        return "";
    }
}
