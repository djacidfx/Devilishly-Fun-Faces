package com.demo.neondevilsface.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.neondevilsface.R;



public class PrivacyPolicyActivity extends AppCompatActivity {
    private FrameLayout adContainerView;
    LinearLayout btn_back;

    @Override 
    public void onPause() {

        super.onPause();
    }

    @Override 
    public void onResume() {
        super.onResume();

    }

    @Override 
    public void onDestroy() {

        super.onDestroy();
    }



    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_privacy_policy);
        WebView webView = (WebView) findViewById(R.id.privacypolicyweb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://mywild.work/privacy");


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() ^ 8192);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.btn_back);
        this.btn_back = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                PrivacyPolicyActivity.this.onBackPressed();
            }
        });
    }
}
