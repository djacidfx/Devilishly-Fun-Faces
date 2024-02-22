package com.demo.neondevilsface.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.neondevilsface.AdAdmob;
import com.demo.neondevilsface.R;
import com.demo.neondevilsface.aynkcall.BaseTask;
import com.demo.neondevilsface.aynkcall.TaskRunner;
import com.demo.neondevilsface.adpater.CreationAdpater;
import com.demo.neondevilsface.adpater.Image;
import com.demo.neondevilsface.adpater.ListPopupWindowAdapter;
import com.demo.neondevilsface.adpater.OnItemClick;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class MyCreationActivity extends AppCompatActivity implements OnItemClick, View.OnClickListener {
    CreationAdpater CreationAdpater;
    private FrameLayout adContainerView;
    View btn_back;
    ImageView btn_close;
    private ImageView btn_delete;
    ImageView btn_remove_all;
    ImageView btn_select_all;
    private ImageView btn_share;
    private RecyclerView creation_recyclerView;
    private LinearLayout empty_layout;
    public ArrayList<Image> images_list;
    String item_image_path;
    ListPopupWindow listPopupWindow;
    TextView long_press_txt;
    TextView main_text;
    String message;
    int position_item_image;
    LinearLayout progress_layout;
    private TextView title_text;
    Boolean is_click = false;
    Boolean is_pasue = true;
    List<String> sampleData = Arrays.asList("Share", "Delete");
    String TAG = "adsLog";

    @Override 
    public void onBackPressed() {
        if (this.btn_close.getVisibility() == View.VISIBLE) {
            this.btn_close.setVisibility(View.GONE);
            this.btn_back.setVisibility(View.VISIBLE);
            this.main_text.setVisibility(View.VISIBLE);
            this.title_text.setVisibility(View.GONE);
            this.btn_delete.setVisibility(View.GONE);
            this.btn_share.setVisibility(View.GONE);
            this.btn_select_all.setVisibility(View.GONE);
            this.btn_remove_all.setVisibility(View.GONE);
            this.CreationAdpater.remove_all();
            return;
        }
        super.onBackPressed();
    }

    
    @Override 
    public void onResume() {
        super.onResume();

        this.is_click = false;
        if (this.is_pasue.booleanValue()) {
            new TaskRunner().executeAsync(new NetworkTask());
            this.is_pasue = false;
        }
    }

    @Override 
    public void onPause() {

        super.onPause();
    }

    @Override 
    public void onDestroy() {

        super.onDestroy();
    }





    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_creation);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() ^ 8192);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        this.is_pasue = false;
        FindViewByID();
        this.images_list = new ArrayList<>();
        this.btn_back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                MyCreationActivity.this.onBackPressed();
            }
        });
        new TaskRunner().executeAsync(new NetworkTask());
    }

    @Override 
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close :
                this.btn_select_all.setVisibility(View.VISIBLE);
                this.btn_remove_all.setVisibility(View.GONE);
                this.CreationAdpater.remove_all();
                return;
            case R.id.btn_delete :
                if (this.CreationAdpater.getSelected_list().size() == 1) {
                    this.message = "Do you want to delete this photo?";
                } else {
                    this.message = "Do you want to delete these photos?";
                }
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i != -1) {
                            return;
                        }
                        for (int i2 = 0; i2 < MyCreationActivity.this.CreationAdpater.getSelected_list().size(); i2++) {
                            MyCreationActivity.this.images_list.remove(MyCreationActivity.this.CreationAdpater.getSelected_list().get(i2));
                            MyCreationActivity myCreationActivity = MyCreationActivity.this;
                            myCreationActivity.deleteTmpFile(myCreationActivity.CreationAdpater.getSelected_list().get(i2).getPath());
                        }
                        MyCreationActivity.this.CreationAdpater.remove_all();
                        MyCreationActivity.this.btn_close.setVisibility(View.GONE);
                        MyCreationActivity.this.btn_back.setVisibility(View.VISIBLE);
                        MyCreationActivity.this.title_text.setVisibility(View.GONE);
                        MyCreationActivity.this.main_text.setVisibility(View.VISIBLE);
                        MyCreationActivity.this.btn_remove_all.setVisibility(View.GONE);
                        MyCreationActivity.this.btn_select_all.setVisibility(View.GONE);
                        MyCreationActivity.this.setList();
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                builder.setMessage("" + this.message).setPositiveButton("Yes", onClickListener).setNegativeButton("No", onClickListener).show();
                return;
            case R.id.btn_remove_all :
                this.btn_select_all.setVisibility(View.VISIBLE);
                this.btn_remove_all.setVisibility(View.GONE);
                this.CreationAdpater.remove_all();
                return;
            case R.id.btn_save_me :
            default:
                return;
            case R.id.btn_select_all :
                this.btn_select_all.setVisibility(View.GONE);
                this.btn_remove_all.setVisibility(View.VISIBLE);
                this.CreationAdpater.Select_all();
                return;
            case R.id.btn_share :
                if (this.CreationAdpater.getSelected_list().size() > 30) {
                    Toast.makeText(this, "Can't share more than 30 photos", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Uri> arrayList = new ArrayList<>();
                for (int i = 0; i < this.CreationAdpater.getSelected_list().size(); i++) {
                    arrayList.add(FileProvider.getUriForFile(this, getPackageName() + ".provider", new File(this.CreationAdpater.getSelected_list().get(i).getPath())));
                }
                String string = getResources().getString(R.string.app_name);
                Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
                intent.putExtra("android.intent.extra.SUBJECT", string);
                intent.setType("*/*");
                Log.e("share___", "onClick: " + arrayList.size());
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
                startActivity(Intent.createChooser(intent, "Share image"));
                return;
        }
    }

    public void setList() {
        if (this.images_list.size() > 0) {
            Log.e("setList", "setList: " + this.images_list.size());
            this.creation_recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            CreationAdpater creationAdpater = new CreationAdpater(this, this.images_list);
            this.CreationAdpater = creationAdpater;
            this.creation_recyclerView.setAdapter(creationAdpater);
            this.CreationAdpater.setClickListener(this);
            this.btn_back.setVisibility(View.VISIBLE);
            return;
        }
        SetEmptyScreen();
    }

    public ArrayList<Image> getingFile() {
        this.images_list.clear();
        File[] listFiles = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getPackageName()).listFiles();
        Collections.reverse(Collections.singletonList(listFiles));
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                new File(listFiles[i].getAbsolutePath()).listFiles();
                if (listFiles[i].getAbsolutePath().endsWith(".jpg")) {
                    File file = new File(listFiles[i].getAbsolutePath());
                    Date date = new Date(file.lastModified());
                    Log.e("getingFile", "getingFile: " + date);
                    if (file.canWrite()) {
                        this.images_list.add(new Image(listFiles[i].getAbsolutePath(), false, date));
                    }
                }
            }
            ArrayList<Image> arrayList = this.images_list;
            if (arrayList != null) {
                Collections.sort(arrayList, new Comparator<Image>() { 
                    @Override 
                    public int compare(Image image, Image image2) {
                        return image2.getDate().compareTo(image.getDate());
                    }
                });
            }
        }
        return this.images_list;
    }

    public void deleteTmpFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
            getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_data =?", new String[]{str});
        }
    }

    public void FindViewByID() {
        this.long_press_txt = (TextView) findViewById(R.id.long_press_txt);
        this.creation_recyclerView = (RecyclerView) findViewById(R.id.creation_recyclerView);
        this.empty_layout = (LinearLayout) findViewById(R.id.empty_layout);
        this.progress_layout = (LinearLayout) findViewById(R.id.progress_layout);
        this.main_text = (TextView) findViewById(R.id.main_text);
        this.btn_back = findViewById(R.id.btn_back);
        this.title_text = (TextView) findViewById(R.id.title_text);
        this.btn_back = findViewById(R.id.btn_back);
        ImageView imageView = (ImageView) findViewById(R.id.btn_close);
        this.btn_close = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.btn_share);
        this.btn_share = imageView2;
        imageView2.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.btn_delete);
        this.btn_delete = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) findViewById(R.id.btn_remove_all);
        this.btn_remove_all = imageView4;
        imageView4.setOnClickListener(this);
        ImageView imageView5 = (ImageView) findViewById(R.id.btn_select_all);
        this.btn_select_all = imageView5;
        imageView5.setOnClickListener(this);
    }

    public void SetEmptyScreen() {
        this.creation_recyclerView.setVisibility(View.GONE);
        this.empty_layout.setVisibility(View.VISIBLE);
        this.long_press_txt.setVisibility(View.GONE);
    }

    @Override 
    public void ShareItemClick(String str, int i, Boolean bool, View view) {
        listwindowShow(view);
        this.item_image_path = str;
        this.position_item_image = i;
    }

    @Override 
    public void OnItemCLICK(String str) {
        this.is_pasue = true;
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("save_image", str);
        intent.putExtra("from", 1);
        startActivity(intent);
    }

    @Override 
    public void CheckEmpty(Boolean bool) {
        if (bool.booleanValue()) {
            SetEmptyScreen();
        }
    }

    @Override 
    public void SelectedList(ArrayList<Image> arrayList) {
        Log.e("SelectedList", "SelectedList: " + arrayList.size());
        if (arrayList.size() > 0) {
            this.main_text.setVisibility(View.GONE);
            this.title_text.setVisibility(View.VISIBLE);
            TextView textView = this.title_text;
            textView.setText("" + arrayList.size() + " Selected");
            this.btn_close.setVisibility(View.VISIBLE);
            this.btn_back.setVisibility(View.GONE);
            this.btn_delete.setVisibility(View.VISIBLE);
            this.btn_share.setVisibility(View.VISIBLE);
            if (arrayList.size() == this.images_list.size()) {
                this.btn_select_all.setVisibility(View.GONE);
                this.btn_remove_all.setVisibility(View.VISIBLE);
            } else {
                this.btn_select_all.setVisibility(View.VISIBLE);
                this.btn_remove_all.setVisibility(View.GONE);
            }
        } else {
            this.title_text.setText("My Creation");
            this.btn_back.setVisibility(View.VISIBLE);
            this.btn_close.setVisibility(View.GONE);
            this.main_text.setVisibility(View.VISIBLE);
            this.title_text.setVisibility(View.GONE);
            this.btn_delete.setVisibility(View.GONE);
            this.btn_share.setVisibility(View.GONE);
            this.btn_select_all.setVisibility(View.GONE);
            this.btn_remove_all.setVisibility(View.GONE);
        }
        this.CreationAdpater.notifyDataSetChanged();
    }

    public void listwindowShow(View view) {
        ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        this.listPopupWindow = listPopupWindow;
        listPopupWindow.setAnchorView(view);
        this.listPopupWindow.setWidth(-2);
        this.listPopupWindow.setHeight(-2);
        this.listPopupWindow.setDropDownGravity(17);
        this.listPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupmenubackground));
        this.listPopupWindow.setAdapter(new ListPopupWindowAdapter(this, this.sampleData, new ListPopupWindowAdapter.OnClickDeleteButtonListener() { 
            @Override 
            public void onClickDeleteButton(int i) {
                MyCreationActivity.this.listPopupWindow.dismiss();
                if (i != 0) {
                    if (i == 1) {
                        if (new File(MyCreationActivity.this.item_image_path).exists()) {
                            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { 
                                @Override 
                                public void onClick(DialogInterface dialogInterface, int i2) {
                                    if (i2 != -1) {
                                        return;
                                    }
                                    MyCreationActivity.this.deleteTmpFile(MyCreationActivity.this.item_image_path);
                                    MyCreationActivity.this.images_list.remove(MyCreationActivity.this.position_item_image);
                                    MyCreationActivity.this.CreationAdpater.notifyDataSetChanged();
                                    if (MyCreationActivity.this.images_list.size() == 0) {
                                        MyCreationActivity.this.SetEmptyScreen();
                                    }
                                }
                            };
                            new AlertDialog.Builder(MyCreationActivity.this, R.style.MyDialogTheme).setMessage("Do you want to delete this photo?").setPositiveButton("Yes", onClickListener).setNegativeButton("No", onClickListener).show();
                            return;
                        }
                        Toast.makeText(MyCreationActivity.this, "Photo doesn't exit", 0).show();
                        MyCreationActivity.this.onBackPressed();
                    }
                } else if (new File(MyCreationActivity.this.item_image_path).exists()) {
                    String string = MyCreationActivity.this.getResources().getString(R.string.app_name);
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.putExtra("android.intent.extra.SUBJECT", string);
                    MyCreationActivity myCreationActivity = MyCreationActivity.this;
                    Uri uriForFile = FileProvider.getUriForFile(myCreationActivity, MyCreationActivity.this.getPackageName() + ".provider", new File(MyCreationActivity.this.item_image_path));
                    intent.setType("image/*");
                    intent.putExtra("android.intent.extra.STREAM", uriForFile);
                    MyCreationActivity.this.startActivity(Intent.createChooser(intent, "Share image"));
                } else {
                    Toast.makeText(MyCreationActivity.this, "Photo doesn't exit", 0).show();
                    MyCreationActivity.this.onBackPressed();
                }
            }
        }));
        this.listPopupWindow.show();
    }

    
    public class NetworkTask extends BaseTask {
        public NetworkTask() {
        }

        @Override 
        public void setUiForLoading() {
            Log.e("Image_list", "setUiForLoading: ");
            MyCreationActivity.this.progress_layout.setVisibility(View.VISIBLE);
            MyCreationActivity.this.creation_recyclerView.setVisibility(View.GONE);
        }

        @Override 
        public ArrayList<Image> call() throws Exception {
            Log.e("Image_list", "call: ");
            return MyCreationActivity.this.getingFile();
        }

        @Override 
        public void setDataAfterLoading(Object obj) {
            MyCreationActivity.this.progress_layout.setVisibility(View.GONE);
            MyCreationActivity.this.creation_recyclerView.setVisibility(View.VISIBLE);
            Log.e("Image_list", "setDataAfterLoading: ");
            if (((ArrayList) obj).size() > 0) {
                MyCreationActivity.this.setList();
            } else {
                MyCreationActivity.this.SetEmptyScreen();
            }
            super.setDataAfterLoading(obj);
        }
    }
}
