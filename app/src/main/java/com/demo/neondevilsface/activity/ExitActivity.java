package com.demo.neondevilsface.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.neondevilsface.AdAdmob;
import com.demo.neondevilsface.R;



public class ExitActivity extends AppCompatActivity {




    
    @Override 
    public void onDestroy() {

        super.onDestroy();
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_exit);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() ^ 8192);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void no(View view) {
        onBackPressed();
    }

    public void yes(View view) {
        finishAffinity();
    }
}
