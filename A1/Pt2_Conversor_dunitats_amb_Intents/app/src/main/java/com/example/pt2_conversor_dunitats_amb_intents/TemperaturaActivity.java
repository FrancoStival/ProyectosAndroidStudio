package com.example.pt2_conversor_dunitats_amb_intents;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class TemperaturaActivity extends AppCompatActivity {

    private EditText etValorEntrada;
    private RadioGroup rgOrigen, rgDesti;
    private Button btnConvertir;
    private TextView tvResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

        etValorEntrada = findViewById(R.id.etValorEntradaTemperatura);
        rgOrigen = findViewById(R.id.rgUnitatOrigenTemperatura);
        rgDesti = findViewById(R.id.rgUnitatDestiTemperatura);
        btnConvertir = findViewById(R.id.btnConvertirTemperatura);
        tvResultat = findViewById(R.id.tvResultatTemperatura);

        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertirTemperatura();
            }
        });
    }

    private void convertirTemperatura() {
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

        double resultat = convertir(valorEntrada, idOrigen, idDesti);
        String resultatFormatat = String.format("%.2f", resultat);
        String simbolDesti = obtenirSimbolUnitat(idDesti);

        String resultatText = getString(R.string.resultat_prefix) + " " + resultatFormatat + " " + simbolDesti;
        tvResultat.setText(resultatText);
    }

    private double convertir(double valor, int idOrigen, int idDesti) {
        if (idOrigen == idDesti) return valor;

        if (idOrigen == R.id.rbCelsiusOrigen) {
            if (idDesti == R.id.rbFahrenheitDesti) return valor * 9 / 5 + 32;
            if (idDesti == R.id.rbKelvinDesti) return valor + 273.15;
            if (idDesti == R.id.rbRankineDesti) return (valor + 273.15) * 9 / 5;
        }

        if (idOrigen == R.id.rbFahrenheitOrigen) {
            if (idDesti == R.id.rbCelsiusDesti) return (valor - 32) * 5 / 9;
            if (idDesti == R.id.rbKelvinDesti) return (valor - 32) * 5 / 9 + 273.15;
            if (idDesti == R.id.rbRankineDesti) return valor + 459.67;
        }

        if (idOrigen == R.id.rbKelvinOrigen) {
            if (idDesti == R.id.rbCelsiusDesti) return valor - 273.15;
            if (idDesti == R.id.rbFahrenheitDesti) return (valor - 273.15) * 9 / 5 + 32;
            if (idDesti == R.id.rbRankineDesti) return valor * 9 / 5;
        }

        if (idOrigen == R.id.rbRankineOrigen) {
            if (idDesti == R.id.rbCelsiusDesti) return (valor - 491.67) * 5 / 9;
            if (idDesti == R.id.rbFahrenheitDesti) return valor - 459.67;
            if (idDesti == R.id.rbKelvinDesti) return valor * 5 / 9;
        }

        Toast.makeText(this, getString(R.string.error_conversio_no_implementada), Toast.LENGTH_SHORT).show();
        return 0;
    }

    private String obtenirSimbolUnitat(int idRadioButton) {
        if (idRadioButton == R.id.rbCelsiusDesti) return getString(R.string.simbol_celsius);
        if (idRadioButton == R.id.rbFahrenheitDesti) return getString(R.string.simbol_fahrenheit);
        if (idRadioButton == R.id.rbKelvinDesti) return getString(R.string.simbol_kelvin);
        if (idRadioButton == R.id.rbRankineDesti) return getString(R.string.simbol_rankine);
        return "";
    }
}
