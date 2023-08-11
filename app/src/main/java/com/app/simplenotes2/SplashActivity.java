package com.app.simplenotes2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2000; // Delay in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk memulai LoginActivity setelah Splash Screen
                Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);

                // Menutup SplashActivity agar tidak dapat diakses kembali dengan tombol back
                finish();
            }
        }, SPLASH_DELAY);
    }
}

//Signature :
//10120146 - Irshal Mulky H - IF4