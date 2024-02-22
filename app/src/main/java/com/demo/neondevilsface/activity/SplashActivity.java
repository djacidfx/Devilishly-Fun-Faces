package com.demo.neondevilsface.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.neondevilsface.R;


public class SplashActivity extends AppCompatActivity {
    ProgressBar progressbar;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() ^ 8192);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        this.progressbar = progressBar;
        progressBar.setIndeterminate(true);
        new Handler().postDelayed(new Runnable() { 
            @Override 
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 3000L);
    }
}
