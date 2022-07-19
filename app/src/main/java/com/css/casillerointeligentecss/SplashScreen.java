package com.css.casillerointeligentecss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    ProgressBar linearProgressBar;
    int Counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        linearProgressBar = findViewById(R.id.progressBar);

        Timer tiempo = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {

                Counter++;
                linearProgressBar.setProgress(Counter);

                if (Counter==100){
                    tiempo.cancel();
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish(); //Para evitar que se vuelve a abrir la Splash Screen
                }
            }
        };
        tiempo.schedule(tarea,100,50);
    }
}