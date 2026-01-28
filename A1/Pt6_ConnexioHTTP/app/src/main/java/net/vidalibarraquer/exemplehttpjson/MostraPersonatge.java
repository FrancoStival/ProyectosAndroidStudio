package net.vidalibarraquer.exemplehttpjson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class MostraPersonatge extends AppCompatActivity {
    List<Personatge> personatges;

    ImageView im1;
    TextView tv1;
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_personatge);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recuperem l'extra de les dades anteriors
        Intent intent = getIntent();
        final String imatge = intent.getStringExtra("image");
        final String planeta = intent.getStringExtra("planet");
        final String nom = intent.getStringExtra("name");



        //Creem URL
        String URL = "https://www.vidalibarraquer.net/android/"+imatge;


        im1 = (ImageView) findViewById(R.id.imageView);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);


        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(final Bitmap response) {
                im1.setImageBitmap(response);
                tv1.setText(getResources().getText(R.string.nom) + nom);
                tv2.setText(getString(R.string.planeta) + planeta);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MostraPersonatge.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MostraPersonatge.this);
        requestQueue.add(imageRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
