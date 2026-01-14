package com.example.prova1_stival_franco;

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

public class MostrarLlistaJocs extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4, tv5;
    ImageView imgJoc;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_llista_jocs);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String slug = intent.getStringExtra("slug");
        String name = intent.getStringExtra("name");
        String released = intent.getStringExtra("released");
        double rating = intent.getDoubleExtra("rating", 0.0);

        imgJoc = findViewById(R.id.imgJoc);
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);
        tv5 = findViewById(R.id.textView5);


        tv1.setText(getString(R.string.id) + " " + id);
        tv2.setText(getString(R.string.slug) + " " + slug);
        tv3.setText(getString(R.string.name) + " " + name);
        tv4.setText(getString(R.string.released) + " " + released);
        tv5.setText(getString(R.string.rating) + " " + rating);

        String imageUrl = "https://www.vidalibarraquer.net/android/EXAMEN/GAMES/" + slug + ".jpg";
        loadImage(imageUrl);
    }

    private void loadImage(String url) {
        if (queue == null) queue = Volley.newRequestQueue(this);
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imgJoc.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Opcionalmente poner una imagen por defecto
                    }
                });
        queue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}