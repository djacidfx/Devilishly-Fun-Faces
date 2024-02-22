package com.demo.neondevilsface.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.neondevilsface.AdAdmob;
import com.demo.neondevilsface.R;
import com.demo.neondevilsface.Utility.AppUtil;
import com.demo.neondevilsface.Utility.Constant;
import com.demo.neondevilsface.Utility.RealPathUtils;
import com.demo.neondevilsface.Utility.Utils;
import com.demo.neondevilsface.permission.PermissionFragment;

import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    LinearLayout camera;
    Bundle cropoptions;
    File file;
    Uri fileUri;
    LinearLayout gallery;
    ImageView more_option;
    LinearLayout my_creation;
    ProgressDialog progressDialog;
    Uri selectedImageUri;
    TextView tvNavAds;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ExitActivity.class));
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() ^ 8192);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        FindId();
    }

    public void FindId() {
        this.camera = (LinearLayout) findViewById(R.id.camera);
        this.gallery = (LinearLayout) findViewById(R.id.gallery);
        this.camera.setOnClickListener(this);
        this.gallery.setOnClickListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.more_option);
        this.more_option = imageView;
        imageView.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.my_creation);
        this.my_creation = linearLayout;
        linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:

                if (AppUtil.isClickable()) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        new PermissionFragment().CheckForPermission(false, new String[]{"android.permission.CAMERA"}, "App Need You to Grant the Permission.", new PermissionFragment.OnPermissionresult() {
                            @Override
                            public void onFail() {
                            }
                            @Override
                            public void onSuccess() {
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("title", Utils.TEMP_FILE_NAME);
                                MainActivity mainActivity = MainActivity.this;
                                mainActivity.fileUri = mainActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                                intent.putExtra("output", MainActivity.this.fileUri);
                                MainActivity.this.startActivityForResult(intent, Constant.RESULT_CODE_CAMERA);
                            }
                        }).show(getFragmentManager(), "dialogframent");
                    } else {
                        new PermissionFragment().CheckForPermission(false, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"}, "App Need You to Grant the Permission.", new PermissionFragment.OnPermissionresult() {
                            @Override
                            public void onFail() {
                            }

                            @Override
                            public void onSuccess() {
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("title", Utils.TEMP_FILE_NAME);
                                MainActivity mainActivity = MainActivity.this;
                                mainActivity.fileUri = mainActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                                intent.putExtra("output", MainActivity.this.fileUri);
                                MainActivity.this.startActivityForResult(intent, Constant.RESULT_CODE_CAMERA);
                            }
                        }).show(getFragmentManager(), "dialogframent");
                    }


                    return;
                }
                return;
            case R.id.gallery:
                if (AppUtil.isClickable()) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                        Intent intent = new Intent("android.intent.action.PICK");
                        intent.setType("image/*");
                        MainActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constant.RESULT_CODE_GALLERY);


                    } else {
                        new PermissionFragment().CheckForPermission(false, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "App Need You to Grant the Permission.", new PermissionFragment.OnPermissionresult() {
                            @Override
                            public void onFail() {
                            }

                            @Override
                            public void onSuccess() {
                                Intent intent = new Intent("android.intent.action.PICK");
                                intent.setType("image/*");
                                MainActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constant.RESULT_CODE_GALLERY);
                            }
                        }).show(getFragmentManager(), "dialogframent");
                    }


                    return;
                }
                return;
            case R.id.more_option:
                if (AppUtil.isClickable()) {
                    showOptionPopup(this.more_option);
                    return;
                }
                return;
            case R.id.my_creation:
                if (AppUtil.isClickable()) {
                    startActivity(new Intent(this, MyCreationActivity.class));
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void showOptionPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        popupMenu.setOnMenuItemClickListener(this);
        menuInflater.inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.show();
    }


    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == Constant.RESULT_CODE_GALLERY && i2 == -1) {
            if (intent != null) {
                this.selectedImageUri = intent.getData();
                String path = RealPathUtils.getPath(intent.getData(), this);
                if (path.substring(path.length() - 4).equalsIgnoreCase(".gif")) {
                    Toast.makeText(this, "Gif not supported", Toast.LENGTH_SHORT).show();
                } else if (path != null) {
                    Constant.ImageUri = this.selectedImageUri;
                    Bundle bundle = new Bundle();
                    Intent intent2 = new Intent(this, NeonDevilEditActivity.class);
                    bundle.putParcelable("ImageUri", this.selectedImageUri);
                    bundle.putInt("from", 2);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                } else {
                    Toast.makeText(this, "Image not supported", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "No photo found!", Toast.LENGTH_LONG);
            }
        }
        if (i == Constant.RESULT_CODE_CAMERA && i2 == -1) {
            Constant.ImageUri = this.fileUri;
            Bundle bundle2 = new Bundle();
            Intent intent3 = new Intent(this, NeonDevilEditActivity.class);
            bundle2.putParcelable("ImageUri", this.fileUri);
            bundle2.putInt("from", 2);
            intent3.putExtras(bundle2);
            startActivity(intent3);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_rate:
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(1);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                Window window = dialog.getWindow();
                window.setLayout(-2, -2);
                window.setGravity(17);
                View inflate = getLayoutInflater().inflate(R.layout.rate_us_dialog, (ViewGroup) null);
                ((LinearLayout) inflate.findViewById(R.id.ll_later)).setVisibility(View.GONE);
                ((TextView) inflate.findViewById(R.id.never)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                ((TextView) inflate.findViewById(R.id.rate_now)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + MainActivity.this.getPackageName())));
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(inflate);
                dialog.show();
                return true;
            case R.id.action_share:
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
                intent.putExtra("android.intent.extra.TEXT", "Download " + getResources().getString(R.string.app_name) + " app from   - https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(intent, "Share Application"));
                return true;
            case R.id.privacy_policy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                return true;
            default:
                return false;
        }
    }


    class getPathImage extends AsyncTask<Void, Void, Void> {
        String imagepath_list;

        getPathImage() {
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            MainActivity.this.progressDialog = new ProgressDialog(MainActivity.this);
            MainActivity.this.progressDialog.setMessage("Please wait...");
            MainActivity.this.progressDialog.setCancelable(false);
            MainActivity.this.progressDialog.setCanceledOnTouchOutside(false);
            MainActivity.this.progressDialog.show();
        }

        @Override
        public Void doInBackground(Void... voidArr) {
            this.imagepath_list = RealPathUtils.getPath(MainActivity.this.selectedImageUri, MainActivity.this);
            return null;
        }

        @Override
        public void onPostExecute(Void r4) {
            MainActivity.this.progressDialog.dismiss();
            String str = this.imagepath_list;
            if (!str.substring(str.length() - 4).equalsIgnoreCase(".gif")) {
                if (this.imagepath_list != null) {
                    Constant.ImageUri = MainActivity.this.selectedImageUri;
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(MainActivity.this, NeonDevilEditActivity.class);
                    bundle.putParcelable("ImageUri", MainActivity.this.selectedImageUri);
                    bundle.putInt("from", 2);
                    intent.putExtras(bundle);
                    MainActivity.this.startActivity(intent);
                    return;
                }
                Toast.makeText(MainActivity.this, "Image not supported", 1).show();
                return;
            }
            Toast.makeText(MainActivity.this, "Gif not supported", 0).show();
        }
    }
}
