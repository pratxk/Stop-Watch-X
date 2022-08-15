package com.example.stopwatchx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int seconds;
    private boolean Running;
    private boolean WasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            savedInstanceState.getInt("Seconds");
            savedInstanceState.getBoolean("Running");
            savedInstanceState.getBoolean("WasRunning");
        }

        runTimer();
    }

    public void onStart(View view){
        Running = true;
    }
    public void onStop(View view){
        Running = false;
    }
    public void onReset(View view){
        Running = false;
        seconds = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        WasRunning = Running;
        Running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(WasRunning){
            Running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Seconds",seconds);
        outState.putBoolean("Running",Running);
        outState.putBoolean("WasRunning",WasRunning);
    }

    private void runTimer(){
        TextView timeview = findViewById(R.id.textView);
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds /3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String Time = String.format(Locale.getDefault(),
                                            "%d:%02d:%02d",
                                             hours,minutes,secs);

                timeview.setText(Time);

                if(Running){
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}