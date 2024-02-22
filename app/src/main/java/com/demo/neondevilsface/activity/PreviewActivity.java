package com.demo.neondevilsface.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.demo.neondevilsface.AdAdmob;
import com.demo.neondevilsface.R;
import com.demo.neondevilsface.Utility.AppRater;
import com.demo.neondevilsface.Utility.AppUtil;

import java.io.File;


public class PreviewActivity extends AppCompatActivity {
    LinearLayout btn_back;
    View delete;
    int from_actvity;
    ImageView preview_image;
    View rate_now;
    String save_image_path;
    View share;

    @Override 
    public void onBackPressed() {
        if (this.from_actvity == 0) {
            SharedPreferences sharedPreferences = getSharedPreferences("app_rater", 0);
            int i = sharedPreferences.getInt("total_launch_count", 1);
            int i2 = sharedPreferences.getInt("never_count", 1);
            int i3 = sharedPreferences.getInt("rate_count", 1);
            Long valueOf = Long.valueOf(sharedPreferences.getLong("first_launch_date_time", 0L));
            Long valueOf2 = Long.valueOf(sharedPreferences.getLong("launch_date_time", 0L));
            if (valueOf.longValue() == 0) {
                AppRater.app_launched(this, R.layout.rate_us_dialog, R.id.never, R.id.remindme, R.id.rate_now);
                return;
            } else if (System.currentTimeMillis() >= valueOf2.longValue() + 86400000 && i <= 3 && i2 <= 1 && i3 <= 2) {
                AppRater.app_launched(this, R.layout.rate_us_dialog, R.id.never, R.id.remindme, R.id.rate_now);
                return;
            } else {
                backPresscall();
                return;
            }
        }
        backPresscall();
    }

    public void backPresscall() {
        super.onBackPressed();
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_preview);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);




        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() ^ 8192);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        this.btn_back = (LinearLayout) findViewById(R.id.btn_back);
        this.share = findViewById(R.id.share);
        this.preview_image = (ImageView) findViewById(R.id.preview_image);
        this.delete = findViewById(R.id.delete);
        this.rate_now = findViewById(R.id.rate_now);
        Intent intent = getIntent();
        this.save_image_path = intent.getStringExtra("save_image");
        this.from_actvity = intent.getIntExtra("from", 0);
        this.preview_image.setImageBitmap(BitmapFactory.decodeFile(this.save_image_path, new BitmapFactory.Options()));
        this.btn_back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AppUtil.isClickable()) {
                    PreviewActivity.this.onBackPressed();
                }
            }
        });
        this.share.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AppUtil.isClickable()) {
                    String string = PreviewActivity.this.getResources().getString(R.string.app_name);
                    Intent intent2 = new Intent("android.intent.action.SEND");
                    intent2.putExtra("android.intent.extra.SUBJECT", string);
                    PreviewActivity previewActivity = PreviewActivity.this;
                    Uri uriForFile = FileProvider.getUriForFile(previewActivity, PreviewActivity.this.getPackageName() + ".provider", new File(PreviewActivity.this.save_image_path));
                    intent2.setType("image/*");
                    intent2.putExtra("android.intent.extra.STREAM", uriForFile);
                    PreviewActivity.this.startActivity(Intent.createChooser(intent2, "Share image"));
                }
            }
        });
        this.delete.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AppUtil.isClickable()) {
                    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { 
                        @Override 
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i != -1) {
                                return;
                            }
                            PreviewActivity.this.deleteTmpFile(PreviewActivity.this.save_image_path);
                            PreviewActivity.this.onBackPressed();
                        }
                    };
                    new AlertDialog.Builder(PreviewActivity.this, R.style.MyDialogTheme).setMessage("Do you want to delete this photo?").setPositiveButton("Yes", onClickListener).setNegativeButton("No", onClickListener).show();
                }
            }
        });
        this.rate_now.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AppUtil.isClickable()) {
                    final Dialog dialog = new Dialog(PreviewActivity.this);
                    dialog.requestWindowFeature(1);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    Window window = dialog.getWindow();
                    window.setLayout(-2, -2);
                    window.setGravity(17);
                    View inflate = PreviewActivity.this.getLayoutInflater().inflate(R.layout.rate_us_dialog, (ViewGroup) null);
                    ((LinearLayout) inflate.findViewById(R.id.ll_later)).setVisibility(View.GONE);
                    ((TextView) inflate.findViewById(R.id.never)).setOnClickListener(new View.OnClickListener() { 
                        @Override 
                        public void onClick(View view2) {
                            dialog.dismiss();
                        }
                    });
                    ((TextView) inflate.findViewById(R.id.rate_now)).setOnClickListener(new View.OnClickListener() { 
                        @Override 
                        public void onClick(View view2) {
                            PreviewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + PreviewActivity.this.getPackageName())));
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(inflate);
                    dialog.show();
                }
            }
        });
    }

    public void deleteTmpFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
            getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_data =?", new String[]{str});
        }
    }
}
