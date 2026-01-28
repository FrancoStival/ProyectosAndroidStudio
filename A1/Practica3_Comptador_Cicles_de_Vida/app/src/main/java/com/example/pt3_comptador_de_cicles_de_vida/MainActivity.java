package com.example.pt3_comptador_de_cicles_de_vida;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvOnCreate, tvOnStart, tvOnRestart, tvOnResume, tvOnPause, tvOnStop, tvOnDestroy;

    private int onCreateCount = 0;
    private int onStartCount = 0;
    private int onRestartCount = 0;
    private int onResumeCount = 0;
    private int onPauseCount = 0;
    private int onStopCount = 0;
    private int onDestroyCount = 0;

    private static final String KEY_ON_CREATE = "onCreateCount";
    private static final String KEY_ON_START = "onStartCount";
    private static final String KEY_ON_RESTART = "onRestartCount";
    private static final String KEY_ON_RESUME = "onResumeCount";
    private static final String KEY_ON_PAUSE = "onPauseCount";
    private static final String KEY_ON_STOP = "onStopCount";
    private static final String KEY_ON_DESTROY = "onDestroyCount";

    private static final String TAG = "ComptadorCicles";

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

        tvOnCreate = findViewById(R.id.tvOnCreate);
        tvOnStart = findViewById(R.id.tvOnStart);
        tvOnRestart = findViewById(R.id.tvOnRestart);
        tvOnResume = findViewById(R.id.tvOnResume);
        tvOnPause = findViewById(R.id.tvOnPause);
        tvOnStop = findViewById(R.id.tvOnStop);
        tvOnDestroy = findViewById(R.id.tvOnDestroy);

        if (savedInstanceState != null) {
            onCreateCount = savedInstanceState.getInt(KEY_ON_CREATE, 0);
            onStartCount = savedInstanceState.getInt(KEY_ON_START, 0);
            onRestartCount = savedInstanceState.getInt(KEY_ON_RESTART, 0);
            onResumeCount = savedInstanceState.getInt(KEY_ON_RESUME, 0);
            onPauseCount = savedInstanceState.getInt(KEY_ON_PAUSE, 0);
            onStopCount = savedInstanceState.getInt(KEY_ON_STOP, 0);
            onDestroyCount = savedInstanceState.getInt(KEY_ON_DESTROY, 0);
        }

        onCreateCount++;
        Log.i(TAG, "onCreate() executat " + onCreateCount + " cops");
        updateUI();
    }

    private void updateUI() {
        tvOnCreate.setText(getString(R.string.on_create_text, onCreateCount));
        tvOnStart.setText(getString(R.string.on_start_text, onStartCount));
        tvOnRestart.setText(getString(R.string.on_restart_text, onRestartCount));
        tvOnResume.setText(getString(R.string.on_resume_text, onResumeCount));
        tvOnPause.setText(getString(R.string.on_pause_text, onPauseCount));
        tvOnStop.setText(getString(R.string.on_stop_text, onStopCount));
        tvOnDestroy.setText(getString(R.string.on_destroy_text, onDestroyCount));
    }

    @Override
    protected void onStart() {
        super.onStart();
        onStartCount++;
        Log.i(TAG, "onStart() executat " + onStartCount + " cops");
        updateUI();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onRestartCount++;
        Log.i(TAG, "onRestart() executat " + onRestartCount + " cops");
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeCount++;
        Log.i(TAG, "onResume() executat " + onResumeCount + " cops");
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseCount++;
        Log.i(TAG, "onPause() executat " + onPauseCount + " cops");
        updateUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onStopCount++;
        Log.i(TAG, "onStop() executat " + onStopCount + " cops");
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyCount++;
        Log.i(TAG, "onDestroy() executat " + onDestroyCount + " cops");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_ON_CREATE, onCreateCount);
        outState.putInt(KEY_ON_START, onStartCount);
        outState.putInt(KEY_ON_RESTART, onRestartCount);
        outState.putInt(KEY_ON_RESUME, onResumeCount);
        outState.putInt(KEY_ON_PAUSE, onPauseCount);
        outState.putInt(KEY_ON_STOP, onStopCount);
        outState.putInt(KEY_ON_DESTROY, onDestroyCount);
    }
}
