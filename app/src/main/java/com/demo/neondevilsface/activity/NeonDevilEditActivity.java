package com.demo.neondevilsface.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.InputDeviceCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.neondevilsface.AdAdmob;
import com.demo.neondevilsface.R;

import com.demo.neondevilsface.Utility.AdapterPositionUtils;
import com.demo.neondevilsface.Utility.AppUtil;
import com.demo.neondevilsface.Utility.Constant;
import com.demo.neondevilsface.Utility.RealPathUtils;
import com.demo.neondevilsface.Utility.StorageUtills;
import com.demo.neondevilsface.colorshader.MenuDialog;
import com.demo.neondevilsface.colorshader.MyApplication;
import com.demo.neondevilsface.colorshader.MyMotionEvent;
import com.demo.neondevilsface.colorshader.MyPointF;
import com.demo.neondevilsface.colorshader.ambilwarna.AmbilWarnaDialog;
import com.demo.neondevilsface.fragment.TextEditorDialogFragment;
import com.demo.neondevilsface.glitchfilter.DynamicOptionsCallback;
import com.demo.neondevilsface.glitchfilter.GPUImage;
import com.demo.neondevilsface.glitchfilter.GPUImageBulgeDistortionFilter;
import com.demo.neondevilsface.glitchfilter.GPUImageColorInvertFilter;
import com.demo.neondevilsface.glitchfilter.GPUImageEmbossFilter;
import com.demo.neondevilsface.glitchfilter.GPUImagePixelationFilter;
import com.demo.neondevilsface.glitchfilter.GPUImageSepiaToneFilter;
import com.demo.neondevilsface.glitchfilter.GPUImageSwirlFilter;
import com.demo.neondevilsface.glitchfilter.GlitchFilters;
import com.demo.neondevilsface.interfac.ArtFilterItemClick;
import com.demo.neondevilsface.interfac.FontInterface;
import com.demo.neondevilsface.interfac.IOnBackPressed;
import com.demo.neondevilsface.interfac.SetStickerListener;
import com.demo.neondevilsface.interfac.StickerPackListener;
import com.demo.neondevilsface.textmodule.AutofitTextRel;
import com.demo.neondevilsface.textmodule.TextInfo;
import com.isseiaoki.simplecropview.CropImageView;
import com.demo.neondevilsface.adpater.ArtFilterAdpater;
import com.demo.neondevilsface.adpater.ColorPickerAdapter;
import com.demo.neondevilsface.adpater.EffectAdapter;
import com.demo.neondevilsface.adpater.FilterStickerAdapter;
import com.demo.neondevilsface.adpater.FontAdapter;
import com.demo.neondevilsface.adpater.PatternAdpater;
import com.demo.neondevilsface.adpater.StickerAdapter;
import com.demo.neondevilsface.effect.BrushImageView;
import com.demo.neondevilsface.effect.GPUImageZoomBlurFilter;
import com.demo.neondevilsface.effect.TouchImageView;
import com.demo.neondevilsface.filter.ThumbnailCallback;
import com.demo.neondevilsface.filter.ThumbnailItem;
import com.demo.neondevilsface.filter.ThumbnailsAdapter;
import com.demo.neondevilsface.filter.ThumbnailsManager;
import com.demo.neondevilsface.model.ComponentInfo;
import com.demo.neondevilsface.view.ImageViewTouch;
import com.demo.neondevilsface.view.ImageViewTouchBase;
import com.demo.neondevilsface.view.ResizableStickerView;
import com.demo.neondevilsface.view.RotateImageView;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import io.reactivex.disposables.CompositeDisposable;


public class NeonDevilEditActivity extends AppCompatActivity implements View.OnClickListener, ResizableStickerView.TouchEventListener, AutofitTextRel.TouchEventListener, ArtFilterItemClick {
    static final int FLIP_HORIZONTAL = 2;
    static final int FLIP_VERTICAL = 1;
    private static final int RIGHT_ANGLE = 90;
    public static int image_angle = 0;
    public static Bitmap main_bitmap = null;
    public static Canvas mcanvas = null;
    public static MyView myView = null;
    public static Bitmap originalbmp = null;
    static int rectSize = 100;
    public static float screenHeight;
    public static float screenWidth;
    public static SeekBar seekBar_cartoon;
    public static Bitmap selectedphoto;
    static int type;
    Bitmap GPuBitmap;
    Uri ImageUri;
    int MAX_STROKE_WIDTH;
    public int MODE;
    ArrayList<String> MainStickerList;
    View Motion_blur_Button;
    public Bitmap OriginalBitmap;
    View Scale16to9;
    View Scale1to1;
    View Scale2to3;
    View Scale3to2;
    View Scale3to4;
    View Scale3to5;
    View Scale4to3;
    View Scale5to3;
    View Scale5to7;
    View Scale7to5;
    View Scale9to16;
    Activity activity;
    ThumbnailsAdapter adapter;
    TextView apply;
    SeekBar art_filter_seekbar;
    RecyclerView art_recyview;
    SeekBar art_seekbar;
    ArrayList<Bitmap> bitmapList;
    public Bitmap bitmapMaster;
    BlurEffect blurEffect;
    View bottom_option;
    Bitmap brushButtonBgOrg;
    public BrushImageView brushImageView;
    private float brushSize;
    private Vector<Integer> brushSizes;
    private SeekBar brush_size_seekbar;
    LinearLayout btn_back;
    private Canvas canvasMaster;
    ImageView close_btn_filter;
    LinearLayout colorButton;
    Bitmap colorButtonBgOrg;
    ArrayList<Integer> colorList;
    ColorPickerAdapter colorPickerAdapter;
    public RelativeLayout color_effect_main_layout;
    Context context;
    CropImageView cropImageView;
    public float currentx;
    public float currenty;
    public Path drawingPath;
    EffectAdapter effectAdapter;
    View eraseButton;
    View erase_btn;
    SeekBar erase_size_blur_seekbar;
    FilterAndAdjustmentTask f3370ft;
    ProgressDialog f_ProgressDialog;
    LinearLayout filterSeekLay;
    FilterStickerAdapter filterStickerAdapter;
    SeekBar filter_opacity_seek;
    TextView filter_opacity_text;
    TextView filter_txt;
    ArrayList<Integer> filterstickerList;
    View fl_alignment;
    View fl_beauty;
    View fl_color;
    View fl_overlay;
    View fl_pattern;
    View fl_style;
    FocalZoom focalZoom;
    LinearLayout focul_zoom;
    SeekBar focul_zoom_seekbar;
    private View focusedView;
    FontAdapter fontAdapter;
    View freeScale;
    GPUImage gPUImage;
    private GlitchFilters gli;
    private Paint grayPaint;
    FrameLayout ho_filp;
    int horizontalListItemNewCount;
    public int imageViewHeight;
    public int imageViewWidth;
    ImageView img_art;
    ImageView img_blur;
    ImageView img_brush;
    ImageView img_color_pallete;
    ImageView img_crop;
    ImageView img_erase;
    ImageView img_filter;
    ImageView img_original;
    ImageView img_paint;
    ImageView img_rotation;
    ImageView img_sticker;
    ImageView img_text;
    public int initialDrawingCount;
    public boolean isMultipleTouchErasing;
    public boolean isTouchOnBitmap;
    ImageView ivSelected;
    ImageView ivStickerAddDone;
    View ivTextStickerAddDone;
    View iv_alignCenter;
    View iv_alignLeft;
    View iv_alignRight;
    View iv_alignment;
    TouchImageView iv_backgroundimg;
    ImageView iv_beauty;
    View iv_center_select;
    View iv_color;
    ImageView iv_flip_left_select;
    ImageView iv_flip_right_select;
    ImageView iv_flip_up_down_select;
    ImageView iv_left_right_select;
    View iv_left_select;
    ImageView iv_overlay;
    ImageView iv_pattern;
    View iv_right_select;
    View iv_style;
    private Bitmap lastEditedBitmap;
    LinearLayout llFace_option;
    View llFilterReset;
    LinearLayout llFilter_option;
    LinearLayout llSticker_edit_option;
    LinearLayout ll_Art_Filter_layout;
    LinearLayout ll_alignment;
    LinearLayout ll_art_filter;
    LinearLayout ll_blur_effect;
    LinearLayout ll_blur_layout;
    LinearLayout ll_color_effect_layout;
    LinearLayout ll_crop;
    LinearLayout ll_crop_option;
    LinearLayout ll_erase_selection;
    LinearLayout ll_filter;
    LinearLayout ll_filter_opactity;
    LinearLayout ll_paint_color;
    LinearLayout ll_rotation;
    LinearLayout ll_rotation_layout;
    LinearLayout ll_sticker;
    LinearLayout ll_sticker_layout;
    LinearLayout ll_text_add;
    LinearLayout lltext_option;
    private MaskFilter mBlur;
    Context mContext;
    private MaskFilter mEmboss;
    public Paint mPaint;
    String mainPath;
    public Point mainViewSize;
    Bitmap main_bitmap_filter;
    LinearLayout main_layout;
    Context mcontext;
    MenuDialog menuDialog;
    View middle_ly;
    MyApplication myApplication;
    public SizePickerView mySizePickerView;
    NeonDevilEditActivity neonDevilEditActivity;
    Bitmap oi;
    float oldZoom;
    DynamicOptionsCallback optionsCallback;
    public Bitmap originalBitmap;
    View paintButton;
    public Paint paintSource;
    LinearLayout paletteButton;
    public ArrayList<Path> paths;
    PatternAdpater patternAdpater;
    Bitmap pattern_bmp;
    ImageView pattern_img;
    SeekBar pattern_opacity_seek;
    ProgressDialog progressDialog;
    ProgressDialog progressDialogeffect;
    View progress_circular;
    TextView r_art;
    TextView r_blur;
    TextView r_brush;
    TextView r_color_pallete;
    TextView r_crop;
    TextView r_erase_color;
    TextView r_filter;
    TextView r_original_color;
    TextView r_paint;
    TextView r_rotation;
    TextView r_sticker;
    TextView r_text;
    RadioButton radioButtonBlur;
    RadioButton radioButtonEmboss;
    RadioButton[] radioButtonList;
    RadioButton radioButtonNormal;
    RadioButton radioButtonTransparent;
    private Bitmap resizedBitmap;
    public RelativeLayout rlImageViewContainer;
    RelativeLayout rl_blur;
    RotateImageView roatate_imageview;
    LinearLayout root;
    FrameLayout rotation_left;
    FrameLayout rotation_right;
    RecyclerView rvEmoji;
    RecyclerView rvFilter;
    RecyclerView rvFilterSticker;
    RecyclerView rvText;
    Bitmap sImagem;
    FrameLayout save_bitmap_layout;
    TextView save_image;
    double screenInches;
    Bitmap selectbi;
    int selectedBrushItemFromBrushDialog;
    View selectedView;
    private Paint sepiaPaint;
    StickerAdapter stickerAdapter;
    View stickerFilter;
    View stickerPack1;
    View stickerPack10;
    View stickerPack11;
    View stickerPack12;
    View stickerPack13;
    View stickerPack14;
    View stickerPack15;
    View stickerPack2;
    View stickerPack3;
    View stickerPack4;
    View stickerPack5;
    View stickerPack6;
    View stickerPack7;
    View stickerPack8;
    View stickerPack9;
    TextView sticker_progress;
    RelativeLayout sticker_stkr_rel;
    ArrayList<String> stickerlist;
    int surroundColor;
    ImageViewTouch test_check;
    TextView textViewX3D;
    TextView textViewY3D;
    private Typeface thisfinal_font_ttf;
    int thumbSize;
    TextView title_text;
    Toolbar toolbar;
    public TouchImageView touchImageView;
    ImageView trans_img;
    View transparent;
    LinearLayout transparentSeekLay;
    TextView transparentTxt;
    SeekBar transparent_seek;
    TextView transparent_txt;
    View txt_3D;
    TextView txt_3D_txt;
    LinearLayout txt_3d_SeekLay;
    RelativeLayout txt_stkr_rel;
    private int updatedBrushSize;
    FrameLayout ver_filp;
    ViewFlipper viewFlipper;
    SeekBar x_3d_seek;
    SeekBar y_3d_seek;
    SeekBar zoomSeekBar;
    int flag = 1;
    private int stickerpackid = 10;
    View focusedCopy = null;
    private int rvHeight = 0;
    String fontName = "";
    int tColor = Color.parseColor("#000000");
    String txtGravity = "C";
    private boolean editMode = false;
    boolean touchChange = false;
    int no_filter = 0;
    boolean filter_check = false;
    private int heightFilter2 = 0;
    int effectapply = 0;
    boolean flip = false;
    boolean horizontal = false;
    boolean vertical = false;
    Boolean is_crop = false;
    Boolean is_gpu_Image = false;
    Boolean is_rotation = false;
    public ArrayList<Bitmap> artfilterbitmapList = new ArrayList<>();
    List<String> artfilternameList = Arrays.asList("Normal", "r", "g", "b", "rr", "gg", "bb", "Glitch", "Nagative", "Swirl", "Pixel", "Emboss", "Sepia", "Bulge");
    List<String> artfilternameListName = Arrays.asList("Normal", "R", "G", "B", "RR", "GG", "BB", "Glitch", "Nagative", "Swirl", "Pixel", "Emboss", "Sepia", "Bulge");
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    int ButtonState = 2;
    float DEFAULT_STROKE_WIDTH = 60.0f;
    public float MAX_ZOOM = 5.0f;
    public float MIN_ZOOM = 1.0f;
    int PAINT_STATE = 0;
    int SAVED_STATE = 0;
    Context alertContext = this;
    int bottomHeightOffset = 0;
    Object data = null;
    boolean inFilterAndAdjustment = false;
    int initialYPosition = 0;
    boolean isInClearState = false;
    Boolean isMasked = false;
    boolean isScreenLayoutXlarge = false;
    boolean isSession = false;
    int leftWidthOffset = 0;
    public ArrayList<Paint> paintList = new ArrayList<>();
    public boolean panZoom = false;
    int rightWidthOffset = 0;
    int selectedColor = 0xffff0000;
    float strokeWidth = this.DEFAULT_STROKE_WIDTH;
    int topHeightOffset = 0;
    List<Integer> colorHistoryStack = new ArrayList();
    int MAX_SIZE = 50;
    float currentSize = 20.0f;
    private float mInitialSize = 20.0f;
    int selectedBrushType = 0;
    float[] values = new float[9];
    public String filterType = "r";
    public float intensity = 0.0f;
    public float rgbShift = 0.032f;
    public float thickness = 5.0f;
    public int waveChoosen = 0;
    Boolean is_color_effect = false;
    Boolean is_blur_effect = false;
    public int blur_click = 0;
    private int initialDrawingCountLimit = 20;
    boolean iserase = false;
    private int offset = 0;
    private int undoLimit = 10;
    Boolean is_focal_zoom = false;
    Boolean is_apply_option = false;
    String TAG = "adsLog";
    IOnBackPressed onBackPressed = new IOnBackPressed() {
        @Override 
        public void onBackPressed() {
            NeonDevilEditActivity.this.title_text.setText(NeonDevilEditActivity.this.getResources().getString(R.string.title_text));
            NeonDevilEditActivity.this.lltext_option.setVisibility(View.GONE);
        }
    };
    StickerPackListener stickerPackListener = new StickerPackListener() {
        @Override 
        public void SelectStickerPack(int i) {
            NeonDevilEditActivity.this.stickerpackid = i;
            if (i == 0) {
                NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                neonDevilEditActivity.addlistFromAssest(neonDevilEditActivity.stickerlist, "emoji");
            } else if (i == 2) {
                NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                neonDevilEditActivity2.addlistFromAssest(neonDevilEditActivity2.stickerlist, "rainbow");
            } else if (i == 3) {
                NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this;
                neonDevilEditActivity3.addlistFromAssest(neonDevilEditActivity3.stickerlist, "others");
            } else {
                switch (i) {
                    case 5:
                        NeonDevilEditActivity neonDevilEditActivity4 = NeonDevilEditActivity.this;
                        neonDevilEditActivity4.addlistFromAssest(neonDevilEditActivity4.stickerlist, "party");
                        break;
                    case 6:
                        NeonDevilEditActivity neonDevilEditActivity5 = NeonDevilEditActivity.this;
                        neonDevilEditActivity5.addlistFromAssest(neonDevilEditActivity5.stickerlist, "summer");
                        break;
                    case 7:
                        NeonDevilEditActivity neonDevilEditActivity6 = NeonDevilEditActivity.this;
                        neonDevilEditActivity6.addlistFromAssest(neonDevilEditActivity6.stickerlist, "message_text");
                        break;
                    case 8:
                        NeonDevilEditActivity neonDevilEditActivity7 = NeonDevilEditActivity.this;
                        neonDevilEditActivity7.addlistFromAssest(neonDevilEditActivity7.stickerlist, "cheeks");
                        break;
                    case 9:
                        NeonDevilEditActivity neonDevilEditActivity8 = NeonDevilEditActivity.this;
                        neonDevilEditActivity8.addlistFromAssest(neonDevilEditActivity8.stickerlist, "heartcrown");
                        break;
                    case 10:
                        NeonDevilEditActivity neonDevilEditActivity9 = NeonDevilEditActivity.this;
                        neonDevilEditActivity9.addlistFromAssest(neonDevilEditActivity9.stickerlist, "horns");
                        break;
                    case 11:
                        NeonDevilEditActivity neonDevilEditActivity10 = NeonDevilEditActivity.this;
                        neonDevilEditActivity10.addlistFromAssest(neonDevilEditActivity10.stickerlist, "frames");
                        break;
                    case 12:
                        NeonDevilEditActivity neonDevilEditActivity11 = NeonDevilEditActivity.this;
                        neonDevilEditActivity11.addlistFromAssest(neonDevilEditActivity11.stickerlist, "glow");
                        break;
                    case 13:
                        NeonDevilEditActivity neonDevilEditActivity12 = NeonDevilEditActivity.this;
                        neonDevilEditActivity12.addlistFromAssest(neonDevilEditActivity12.stickerlist, "neoncrown");
                        break;
                    case 14:
                        NeonDevilEditActivity neonDevilEditActivity13 = NeonDevilEditActivity.this;
                        neonDevilEditActivity13.addlistFromAssest(neonDevilEditActivity13.stickerlist, "neonwing");
                        break;
                }
            }
            new StickersLoad().execute(new Void[0]);
        }
    };
    View.OnClickListener radioButtonHandler = new View.OnClickListener() { 
        @Override 
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.radio_normal) {
                NeonDevilEditActivity.this.radioButtonNormal.setSelected(true);
                NeonDevilEditActivity.this.radioButtonBlur.setSelected(false);
                NeonDevilEditActivity.this.radioButtonEmboss.setSelected(false);
                NeonDevilEditActivity.this.radioButtonTransparent.setSelected(false);
                NeonDevilEditActivity.this.selectedBrushType = 0;
                NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                neonDevilEditActivity.selectedBrushItemFromBrushDialog = neonDevilEditActivity.getSelectedBrushType();
                NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                neonDevilEditActivity2.selectedBrushTyheChanged(neonDevilEditActivity2.selectedBrushItemFromBrushDialog);
                NeonDevilEditActivity.this.strokeWidth = NeonDevilEditActivity.this.getSize();
            } else if (id == R.id.radio_blur) {
                NeonDevilEditActivity.this.radioButtonNormal.setSelected(false);
                NeonDevilEditActivity.this.radioButtonBlur.setSelected(true);
                NeonDevilEditActivity.this.radioButtonEmboss.setSelected(false);
                NeonDevilEditActivity.this.radioButtonTransparent.setSelected(false);
                NeonDevilEditActivity.this.selectedBrushType = 1;
                NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this;
                neonDevilEditActivity3.selectedBrushItemFromBrushDialog = neonDevilEditActivity3.getSelectedBrushType();
                NeonDevilEditActivity neonDevilEditActivity4 = NeonDevilEditActivity.this;
                neonDevilEditActivity4.selectedBrushTyheChanged(neonDevilEditActivity4.selectedBrushItemFromBrushDialog);
                NeonDevilEditActivity.this.strokeWidth = NeonDevilEditActivity.this.getSize();
            } else if (id == R.id.radio_emboss) {
                NeonDevilEditActivity.this.selectedBrushType = 2;
                NeonDevilEditActivity.this.radioButtonNormal.setSelected(false);
                NeonDevilEditActivity.this.radioButtonBlur.setSelected(false);
                NeonDevilEditActivity.this.radioButtonEmboss.setSelected(true);
                NeonDevilEditActivity.this.radioButtonTransparent.setSelected(false);
                NeonDevilEditActivity neonDevilEditActivity5 = NeonDevilEditActivity.this;
                neonDevilEditActivity5.selectedBrushItemFromBrushDialog = neonDevilEditActivity5.getSelectedBrushType();
                NeonDevilEditActivity neonDevilEditActivity6 = NeonDevilEditActivity.this;
                neonDevilEditActivity6.selectedBrushTyheChanged(neonDevilEditActivity6.selectedBrushItemFromBrushDialog);
                NeonDevilEditActivity.this.strokeWidth = NeonDevilEditActivity.this.getSize();
            } else if (id == R.id.radio_transparent) {
                NeonDevilEditActivity.this.selectedBrushType = 3;
                NeonDevilEditActivity.this.radioButtonNormal.setSelected(false);
                NeonDevilEditActivity.this.radioButtonBlur.setSelected(false);
                NeonDevilEditActivity.this.radioButtonEmboss.setSelected(false);
                NeonDevilEditActivity.this.radioButtonTransparent.setSelected(true);
                NeonDevilEditActivity neonDevilEditActivity7 = NeonDevilEditActivity.this;
                neonDevilEditActivity7.selectedBrushItemFromBrushDialog = neonDevilEditActivity7.getSelectedBrushType();
                NeonDevilEditActivity neonDevilEditActivity8 = NeonDevilEditActivity.this;
                neonDevilEditActivity8.selectedBrushTyheChanged(neonDevilEditActivity8.selectedBrushItemFromBrushDialog);
                NeonDevilEditActivity.this.strokeWidth = NeonDevilEditActivity.this.getSize();
            }
            NeonDevilEditActivity neonDevilEditActivity9 = NeonDevilEditActivity.this;
            neonDevilEditActivity9.setRadioButtonsCheckState(neonDevilEditActivity9.selectedBrushType);
        }
    };

    private void touchMove(View view) {
    }

    @Override 
    public void onCenterX(View view) {
    }

    @Override 
    public void onCenterXY(View view) {
    }

    @Override 
    public void onCenterY(View view) {
    }

    @Override 
    public void onDoubleTap() {
    }

    @Override 
    public void onEdit(View view, Uri uri) {
    }

    @Override 
    public void onOtherXY(View view) {
    }

    static {
        System.loadLibrary("NativeImageProcessor");
        mcanvas = null;
        originalbmp = null;
    }

    @Override 
    public void onResume() {
        super.onResume();
    }

    
    class getBitmapImage extends AsyncTask<Void, Void, Void> {
        String imagepath_list;

        getBitmapImage() {
        }

        @Override 
        public void onPreExecute() {
            super.onPreExecute();
            NeonDevilEditActivity.this.save_image.setVisibility(View.GONE);
            NeonDevilEditActivity.this.progress_circular.setVisibility(View.VISIBLE);
        }

        @Override 
        public Void doInBackground(Void... voidArr) {
            try {
                NeonDevilEditActivity.main_bitmap = MediaStore.Images.Media.getBitmap(NeonDevilEditActivity.this.getContentResolver(), NeonDevilEditActivity.this.ImageUri);
                if (NeonDevilEditActivity.main_bitmap.getWidth() > NeonDevilEditActivity.main_bitmap.getHeight()) {
                    if (NeonDevilEditActivity.main_bitmap.getWidth() > Constant.MaxWidth) {
                        NeonDevilEditActivity.main_bitmap = Constant.getResizedBitmap(NeonDevilEditActivity.main_bitmap, (int) Constant.MaxWidth, (int) Constant.MaxHeight);
                    }
                } else if (NeonDevilEditActivity.main_bitmap.getHeight() > NeonDevilEditActivity.main_bitmap.getWidth()) {
                    if (NeonDevilEditActivity.main_bitmap.getHeight() > Constant.MaxWidth) {
                        NeonDevilEditActivity.main_bitmap = Constant.getResizedBitmap(NeonDevilEditActivity.main_bitmap, (int) Constant.MaxHeight, (int) Constant.MaxWidth);
                    }
                } else if (NeonDevilEditActivity.main_bitmap.getHeight() == NeonDevilEditActivity.main_bitmap.getWidth() && NeonDevilEditActivity.main_bitmap.getWidth() > Constant.MaxHeight) {
                    NeonDevilEditActivity.main_bitmap = Constant.getResizedBitmap(NeonDevilEditActivity.main_bitmap, (int) Constant.MaxHeight, (int) Constant.MaxHeight);
                }
                try {
                    NeonDevilEditActivity.main_bitmap = Constant.rotateBitmap(NeonDevilEditActivity.main_bitmap, new ExifInterface(RealPathUtils.getPath(NeonDevilEditActivity.this.ImageUri, NeonDevilEditActivity.this)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 0));
                } catch (Exception unused) {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override 
        public void onPostExecute(Void r4) {
            NeonDevilEditActivity.this.save_image.setVisibility(View.VISIBLE);
            NeonDevilEditActivity.this.progress_circular.setVisibility(View.GONE);
            NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
            neonDevilEditActivity.test_check = (ImageViewTouch) neonDevilEditActivity.findViewById(R.id.test_check);
            NeonDevilEditActivity.this.cropImageView.setVisibility(View.GONE);
            NeonDevilEditActivity.this.test_check.setVisibility(View.VISIBLE);
            NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.main_bitmap);
            NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
            NeonDevilEditActivity.this.test_check.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { 
                @Override 
                public boolean onPreDraw() {
                    NeonDevilEditActivity.this.test_check.getViewTreeObserver().removeOnPreDrawListener(this);
                    Constant.out_bitmap_width = NeonDevilEditActivity.this.test_check.getWidth();
                    Constant.out_bitmap_height = NeonDevilEditActivity.this.test_check.getHeight();
                    return true;
                }
            });
        }
    }


    @Override 
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_neon_devil_edit);



        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.FullscreenAd_Counter(this);




        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() ^ 8192);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Intent intent = getIntent();
        this.ImageUri = (Uri) intent.getParcelableExtra("ImageUri");
        this.flag = intent.getIntExtra("from", 1);
        FindId();
        this.mcontext = getApplicationContext();
        ProgressDialog progressDialog = new ProgressDialog(this.alertContext);
        this.f_ProgressDialog = progressDialog;
        progressDialog.setMessage("Please wait...");
        this.f_ProgressDialog.setCancelable(false);
        seekbarChangeListener();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels - dpToPx(getApplicationContext(), 105);
        this.ImageUri = Constant.ImageUri;
        Constant.MaxHeight = screenHeight;
        Constant.MaxWidth = screenWidth;
        new getBitmapImage().execute(new Void[0]);
        this.save_image.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                new FinalImageSave().execute(new Void[0]);
            }
        });
        this.art_filter_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                float f = i - 100;
                NeonDevilEditActivity.this.rgbShift = f / 1000.0f;
                if (NeonDevilEditActivity.this.filterType.equalsIgnoreCase("glitch")) {
                    if (i > 100) {
                        NeonDevilEditActivity.this.rgbShift = f;
                    } else {
                        NeonDevilEditActivity.this.rgbShift = i;
                    }
                    NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                    neonDevilEditActivity.onDynamicOptionsChanged(neonDevilEditActivity.filterType, NeonDevilEditActivity.this.waveChoosen, 0.0f, NeonDevilEditActivity.this.rgbShift, NeonDevilEditActivity.this.thickness);
                    return;
                }
                NeonDevilEditActivity.this.applyChange();
            }
        });
        this.art_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                NeonDevilEditActivity.this.is_gpu_Image = true;
                float f = i;
                float f2 = f / 100.0f;
                if (NeonDevilEditActivity.this.effectapply == 1) {
                    NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                    neonDevilEditActivity.gPUImage = new GPUImage(neonDevilEditActivity);
                    NeonDevilEditActivity.this.gPUImage.setImage(NeonDevilEditActivity.main_bitmap);
                    NeonDevilEditActivity.this.gPUImage.setFilter(new GPUImageSwirlFilter(f2, 1.0f, new PointF(0.5f, 0.5f)));
                    NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.this.gPUImage.getBitmapWithFilterApplied());
                    NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                } else if (NeonDevilEditActivity.this.effectapply == 2) {
                    if (i < 5) {
                        GPUImagePixelationFilter gPUImagePixelationFilter = new GPUImagePixelationFilter();
                        gPUImagePixelationFilter.setPixel(1.0f);
                        NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                        neonDevilEditActivity2.gPUImage = new GPUImage(neonDevilEditActivity2);
                        NeonDevilEditActivity.this.gPUImage.setImage(NeonDevilEditActivity.main_bitmap);
                        NeonDevilEditActivity.this.gPUImage.setFilter(gPUImagePixelationFilter);
                        NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.this.gPUImage.getBitmapWithFilterApplied());
                        NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                        return;
                    }
                    GPUImagePixelationFilter gPUImagePixelationFilter2 = new GPUImagePixelationFilter();
                    gPUImagePixelationFilter2.setPixel(f);
                    NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this;
                    neonDevilEditActivity3.gPUImage = new GPUImage(neonDevilEditActivity3);
                    NeonDevilEditActivity.this.gPUImage.setImage(NeonDevilEditActivity.main_bitmap);
                    NeonDevilEditActivity.this.gPUImage.setFilter(gPUImagePixelationFilter2);
                    NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.this.gPUImage.getBitmapWithFilterApplied());
                    NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                } else if (NeonDevilEditActivity.this.effectapply == 3) {
                    GPUImageEmbossFilter gPUImageEmbossFilter = new GPUImageEmbossFilter();
                    gPUImageEmbossFilter.setIntensity(f2);
                    NeonDevilEditActivity neonDevilEditActivity4 = NeonDevilEditActivity.this;
                    neonDevilEditActivity4.gPUImage = new GPUImage(neonDevilEditActivity4);
                    NeonDevilEditActivity.this.gPUImage.setImage(NeonDevilEditActivity.main_bitmap);
                    NeonDevilEditActivity.this.gPUImage.setFilter(gPUImageEmbossFilter);
                    NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.this.gPUImage.getBitmapWithFilterApplied());
                    NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                } else if (NeonDevilEditActivity.this.effectapply == 4) {
                    GPUImageSepiaToneFilter gPUImageSepiaToneFilter = new GPUImageSepiaToneFilter(f2);
                    NeonDevilEditActivity neonDevilEditActivity5 = NeonDevilEditActivity.this;
                    neonDevilEditActivity5.gPUImage = new GPUImage(neonDevilEditActivity5);
                    NeonDevilEditActivity.this.gPUImage.setImage(NeonDevilEditActivity.main_bitmap);
                    NeonDevilEditActivity.this.gPUImage.setFilter(gPUImageSepiaToneFilter);
                    NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.this.gPUImage.getBitmapWithFilterApplied());
                    NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                } else if (NeonDevilEditActivity.this.effectapply == 5) {
                    NeonDevilEditActivity neonDevilEditActivity6 = NeonDevilEditActivity.this;
                    neonDevilEditActivity6.gPUImage = new GPUImage(neonDevilEditActivity6);
                    NeonDevilEditActivity.this.gPUImage.setImage(NeonDevilEditActivity.main_bitmap);
                    NeonDevilEditActivity.this.gPUImage.setFilter(new GPUImageBulgeDistortionFilter(f2, f2, new PointF(0.5f, 0.5f)));
                    NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.this.gPUImage.getBitmapWithFilterApplied());
                    NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                }
            }
        });
        this.apply.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                NeonDevilEditActivity.this.removeImageViewControll();
                NeonDevilEditActivity.this.llFace_option.setVisibility(View.GONE);
                NeonDevilEditActivity.this.ll_sticker_layout.setVisibility(View.GONE);
                NeonDevilEditActivity.this.llSticker_edit_option.setVisibility(View.GONE);
                if (NeonDevilEditActivity.this.mySizePickerView != null) {
                    NeonDevilEditActivity.this.mySizePickerView.setVisibility(View.GONE);
                }
                new ApplyImageData().execute(new Void[0]);
            }
        });
    }

    @Override 
    public void onBackPressed() {
        this.title_text.setText(getResources().getString(R.string.title_text));
        this.bottom_option.setVisibility(View.VISIBLE);
        AllBottomMainOptionVisi();
        this.test_check.setVisibility(View.VISIBLE);
        this.test_check.setImageBitmap(main_bitmap);
        this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        if (this.llFace_option.getVisibility() == View.VISIBLE) {
            this.llSticker_edit_option.setVisibility(View.GONE);
            this.llFace_option.setVisibility(View.GONE);
            this.ll_sticker_layout.setVisibility(View.GONE);
            this.sticker_stkr_rel.removeAllViews();
        } else if (this.llSticker_edit_option.getVisibility() == View.VISIBLE) {
            this.ivStickerAddDone.performClick();
        } else if (this.is_gpu_Image.booleanValue()) {
            this.ll_Art_Filter_layout.setVisibility(View.GONE);
            this.is_gpu_Image = false;
        } else if (this.lltext_option.getVisibility() == View.VISIBLE) {
            removeImageViewControll();
            this.txt_stkr_rel.removeAllViews();
            this.lltext_option.setVisibility(View.GONE);
            saveapply();
        } else if (this.llFilter_option.getVisibility() == View.VISIBLE) {
            AdapterPositionUtils.effectPosition = -1;
            AdapterPositionUtils.patternPosition = -1;
            AdapterPositionUtils.beautyPosition = 0;
            this.llFilter_option.setVisibility(View.GONE);
            this.close_btn_filter.performClick();
            saveapply();
        } else if (this.ll_rotation_layout.getVisibility() == View.VISIBLE) {
            this.roatate_imageview.setVisibility(View.GONE);
            this.ll_rotation_layout.setVisibility(View.GONE);
            saveapply();
        } else if (this.is_crop.booleanValue()) {
            this.is_crop = false;
            this.bottom_option.setVisibility(View.VISIBLE);
            this.cropImageView.setVisibility(View.GONE);
            this.test_check.setVisibility(View.VISIBLE);
            this.ll_crop_option.setVisibility(View.GONE);
        } else if (this.is_rotation.booleanValue()) {
            this.is_rotation = false;
            this.ll_rotation_layout.setVisibility(View.GONE);
            this.roatate_imageview.setVisibility(View.GONE);
        } else if (this.is_color_effect.booleanValue()) {
            SizePickerView sizePickerView = this.mySizePickerView;
            if (sizePickerView != null) {
                sizePickerView.setVisibility(View.GONE);
            }
            this.color_effect_main_layout.setVisibility(View.GONE);
            this.ll_color_effect_layout.setVisibility(View.GONE);
            this.is_color_effect = false;
        } else if (this.is_blur_effect.booleanValue()) {
            this.is_blur_effect = false;
            this.rl_blur.setVisibility(View.GONE);
            this.ll_blur_layout.setVisibility(View.GONE);
        } else if (this.is_apply_option.booleanValue()) {
            new AlertDialog.Builder(this).setMessage("Edited photo is not saved.Do you want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialogInterface, int i) {
                    NeonDevilEditActivity.this.superOnbck();
                }
            }).setNegativeButton("No", (DialogInterface.OnClickListener) null).show();
        } else {
            super.onBackPressed();
        }
        this.save_image.setVisibility(View.VISIBLE);
        this.apply.setVisibility(View.GONE);
    }

    public void superOnbck() {
        super.onBackPressed();
    }

    public void onDynamicOptionsChanged(String str, int i, float f, float f2, float f3) {
        this.art_seekbar.setVisibility(View.GONE);
        this.art_filter_seekbar.setVisibility(View.VISIBLE);
        this.gli.glitchRshiftFilters.changeParam(str, i, f, f2, f3);
        this.gli.build();
        GPUImage gPUImage = new GPUImage(this);
        this.gPUImage = gPUImage;
        gPUImage.setImage(main_bitmap);
        this.gPUImage.setFilter(this.gli.group);
        this.test_check.setImageBitmap(this.gPUImage.getBitmapWithFilterApplied());
        this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
    }

    public void applyChange() {
        if (this.filterType.equalsIgnoreCase("glitch")) {
            onDynamicOptionsChanged(this.filterType, this.waveChoosen, 0.0f, 10.0f, this.thickness);
        } else {
            onDynamicOptionsChanged(this.filterType, this.waveChoosen, this.rgbShift, this.intensity, this.thickness);
        }
    }

    @Override 
    public void ArtFilterClick(String str, int i) {
        if (i == 0) {
            this.art_seekbar.setVisibility(View.GONE);
            this.art_filter_seekbar.setVisibility(View.GONE);
            this.test_check.setImageBitmap(main_bitmap);
            this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        } else if (i == 1 || i <= 7) {
            this.art_seekbar.setVisibility(View.GONE);
            this.art_filter_seekbar.setVisibility(View.VISIBLE);
            this.filterType = str;
            if (str.equalsIgnoreCase("glitch")) {
                new GlitchFilters(this, false);
            } else {
                new GlitchFilters(this, true);
                this.rgbShift = 0.032f;
            }
            applyChange();
        } else if (i == 8) {
            this.effectapply = 0;
            this.art_seekbar.setVisibility(View.GONE);
            this.art_filter_seekbar.setVisibility(View.GONE);
            this.is_gpu_Image = true;
            GPUImage gPUImage = new GPUImage(this);
            this.gPUImage = gPUImage;
            gPUImage.setImage(main_bitmap);
            this.gPUImage.setFilter(new GPUImageColorInvertFilter());
            this.test_check.setImageBitmap(this.gPUImage.getBitmapWithFilterApplied());
            this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        } else if (i == 9) {
            visiset();
            this.is_gpu_Image = true;
            this.effectapply = 1;
            this.art_seekbar.setProgress(50);
            this.art_seekbar.setMax(100);
            GPUImage gPUImage2 = new GPUImage(this);
            this.gPUImage = gPUImage2;
            gPUImage2.setImage(main_bitmap);
            this.gPUImage.setFilter(new GPUImageSwirlFilter(0.5f, 1.0f, new PointF(0.5f, 0.5f)));
            this.test_check.setImageBitmap(this.gPUImage.getBitmapWithFilterApplied());
            this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        } else if (i == 10) {
            this.effectapply = 2;
            visiset();
            GPUImagePixelationFilter gPUImagePixelationFilter = new GPUImagePixelationFilter();
            gPUImagePixelationFilter.setPixel(10.0f);
            this.is_gpu_Image = true;
            GPUImage gPUImage3 = new GPUImage(this);
            this.gPUImage = gPUImage3;
            gPUImage3.setImage(main_bitmap);
            this.gPUImage.setFilter(gPUImagePixelationFilter);
            this.test_check.setImageBitmap(this.gPUImage.getBitmapWithFilterApplied());
            this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
            this.art_seekbar.setProgress(10);
            this.art_seekbar.setMax(70);
        } else if (i == 11) {
            this.effectapply = 3;
            visiset();
            this.is_gpu_Image = true;
            GPUImageEmbossFilter gPUImageEmbossFilter = new GPUImageEmbossFilter();
            gPUImageEmbossFilter.setIntensity(0.5f);
            GPUImage gPUImage4 = new GPUImage(this);
            this.gPUImage = gPUImage4;
            gPUImage4.setImage(main_bitmap);
            this.gPUImage.setFilter(gPUImageEmbossFilter);
            this.test_check.setImageBitmap(this.gPUImage.getBitmapWithFilterApplied());
            this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
            this.art_seekbar.setProgress(50);
            this.art_seekbar.setMax(100);
        } else if (i == 12) {
            this.effectapply = 4;
            visiset();
            this.is_gpu_Image = true;
            GPUImageSepiaToneFilter gPUImageSepiaToneFilter = new GPUImageSepiaToneFilter(0.5f);
            GPUImage gPUImage5 = new GPUImage(this);
            this.gPUImage = gPUImage5;
            gPUImage5.setImage(main_bitmap);
            this.gPUImage.setFilter(gPUImageSepiaToneFilter);
            this.test_check.setImageBitmap(this.gPUImage.getBitmapWithFilterApplied());
            this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
            this.art_seekbar.setProgress(50);
            this.art_seekbar.setMax(100);
        } else if (i == 13) {
            this.effectapply = 5;
            visiset();
            this.art_seekbar.setProgress(25);
            this.art_seekbar.setMax(100);
            this.is_gpu_Image = true;
            GPUImage gPUImage6 = new GPUImage(this);
            this.gPUImage = gPUImage6;
            gPUImage6.setImage(main_bitmap);
            this.gPUImage.setFilter(new GPUImageBulgeDistortionFilter(0.25f, 0.5f, new PointF(0.5f, 0.5f)));
            this.test_check.setImageBitmap(this.gPUImage.getBitmapWithFilterApplied());
            this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        }
    }

    
    class FinalImageSave extends AsyncTask<Void, Void, Void> {
        File save_images;

        FinalImageSave() {
        }

        @Override 
        public void onPreExecute() {
            super.onPreExecute();
            NeonDevilEditActivity.this.progressDialog = new ProgressDialog(NeonDevilEditActivity.this);
            NeonDevilEditActivity.this.progressDialog.setMessage("Saving Image ...");
            NeonDevilEditActivity.this.progressDialog.setCancelable(false);
            NeonDevilEditActivity.this.progressDialog.setCanceledOnTouchOutside(false);
            NeonDevilEditActivity.this.progressDialog.show();
        }

        @Override 
        public Void doInBackground(Void... voidArr) {
            this.save_images = Constant.saveToInternalStorageFile(NeonDevilEditActivity.main_bitmap, NeonDevilEditActivity.this);
            return null;
        }

        @Override 
        public void onPostExecute(Void r3) {
            NeonDevilEditActivity.this.progressDialog.dismiss();
            Intent intent = new Intent(NeonDevilEditActivity.this, PreviewActivity.class);
            intent.putExtra("save_image", this.save_images.getAbsolutePath());
            intent.putExtra("from", 0);
            NeonDevilEditActivity.this.startActivity(intent);
            NeonDevilEditActivity.this.finish();
        }
    }

    
    class ApplyImageData extends AsyncTask<Void, Void, Void> {
        Bitmap bitmap1;
        String resultPath;

        ApplyImageData() {
        }

        @Override 
        public void onPreExecute() {
            super.onPreExecute();
            NeonDevilEditActivity.this.progressDialog = new ProgressDialog(NeonDevilEditActivity.this);
            NeonDevilEditActivity.this.progressDialog.setMessage("Saving Image ...");
            NeonDevilEditActivity.this.progressDialog.setCancelable(false);
            NeonDevilEditActivity.this.progressDialog.setCanceledOnTouchOutside(false);
            NeonDevilEditActivity.this.progressDialog.show();
        }

        @Override 
        public Void doInBackground(Void... voidArr) {
            if (NeonDevilEditActivity.this.is_crop.booleanValue()) {
                NeonDevilEditActivity.main_bitmap = NeonDevilEditActivity.this.cropImageView.getCroppedBitmap();
                NeonDevilEditActivity.this.is_crop = false;
            } else if (NeonDevilEditActivity.this.is_gpu_Image.booleanValue()) {
                this.bitmap1 = NeonDevilEditActivity.this.gPUImage.getBitmapWithFilterApplied();
                NeonDevilEditActivity.main_bitmap = NeonDevilEditActivity.this.gPUImage.getBitmapWithFilterApplied();
                NeonDevilEditActivity.this.is_gpu_Image = false;
            } else if (NeonDevilEditActivity.this.sticker_stkr_rel.getChildCount() > 0) {
                NeonDevilEditActivity.main_bitmap = Constant.overlay123(NeonDevilEditActivity.main_bitmap, Constant.viewToBitmap(NeonDevilEditActivity.this.sticker_stkr_rel, NeonDevilEditActivity.this.sticker_stkr_rel.getWidth(), NeonDevilEditActivity.this.sticker_stkr_rel.getHeight()));
            } else if (NeonDevilEditActivity.this.txt_stkr_rel.getChildCount() > 0) {
                NeonDevilEditActivity.main_bitmap = Constant.overlay123(NeonDevilEditActivity.main_bitmap, Constant.viewToBitmap(NeonDevilEditActivity.this.txt_stkr_rel, NeonDevilEditActivity.this.txt_stkr_rel.getWidth(), NeonDevilEditActivity.this.txt_stkr_rel.getHeight()));
            } else if (NeonDevilEditActivity.this.pattern_bmp != null) {
                if (AdapterPositionUtils.beautyPosition != 0) {
                    NeonDevilEditActivity.main_bitmap = NeonDevilEditActivity.this.main_bitmap_filter;
                }
                NeonDevilEditActivity.main_bitmap = Constant.overlay123(NeonDevilEditActivity.main_bitmap, Constant.makeTransparent(NeonDevilEditActivity.this.pattern_bmp, NeonDevilEditActivity.this.pattern_opacity_seek.getProgress() + 30));
            } else if (NeonDevilEditActivity.this.oi != null) {
                if (AdapterPositionUtils.beautyPosition != 0) {
                    NeonDevilEditActivity.main_bitmap = NeonDevilEditActivity.this.main_bitmap_filter;
                }
                NeonDevilEditActivity.main_bitmap = Constant.overlay123(NeonDevilEditActivity.main_bitmap, Constant.makeTransparent(NeonDevilEditActivity.this.oi, NeonDevilEditActivity.this.filter_opacity_seek.getProgress() + 30));
            } else if (AdapterPositionUtils.beautyPosition != 0) {
                NeonDevilEditActivity.main_bitmap = NeonDevilEditActivity.this.main_bitmap_filter;
            } else if (NeonDevilEditActivity.this.is_color_effect.booleanValue()) {
                NeonDevilEditActivity.this.is_color_effect = false;
                Canvas canvas = new Canvas(NeonDevilEditActivity.myView.colorSourceBtm);
                Paint paint = new Paint();
                Paint paint2 = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint2.setStyle(Paint.Style.FILL);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
                canvas.drawBitmap(NeonDevilEditActivity.myView.filterBitmap, 0.0f, 0.0f, paint);
                try {
                    this.resultPath = NeonDevilEditActivity.this.getFilesDir().getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream fileOutputStream = new FileOutputStream(this.resultPath);
                    NeonDevilEditActivity.myView.colorSourceBtm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    NeonDevilEditActivity.main_bitmap = BitmapFactory.decodeFile(this.resultPath, new BitmapFactory.Options());
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else if (NeonDevilEditActivity.this.is_blur_effect.booleanValue()) {
                NeonDevilEditActivity.this.runOnUiThread(new Runnable() { 
                    @Override 
                    public void run() {
                        NeonDevilEditActivity.this.is_blur_effect = false;
                        NeonDevilEditActivity.this.brushImageView.setVisibility(View.GONE);
                        NeonDevilEditActivity.this.save_bitmap_layout.postInvalidate();
                        NeonDevilEditActivity.this.save_bitmap_layout.setDrawingCacheEnabled(true);
                        NeonDevilEditActivity.this.save_bitmap_layout.buildDrawingCache();
                        NeonDevilEditActivity.this.sImagem = Bitmap.createScaledBitmap(NeonDevilEditActivity.this.save_bitmap_layout.getDrawingCache(), NeonDevilEditActivity.this.save_bitmap_layout.getWidth(), NeonDevilEditActivity.this.save_bitmap_layout.getHeight(), false);
                        NeonDevilEditActivity.this.iv_backgroundimg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { 
                            @Override 
                            public boolean onPreDraw() {
                                NeonDevilEditActivity.this.iv_backgroundimg.getViewTreeObserver().removeOnPreDrawListener(this);
                                return true;
                            }
                        });
                        NeonDevilEditActivity.main_bitmap = NeonDevilEditActivity.this.sImagem;
                    }
                });
            }
            return null;
        }

        @Override 
        public void onPostExecute(Void r5) {
            NeonDevilEditActivity.this.title_text.setText(NeonDevilEditActivity.this.getResources().getString(R.string.title_text));
            NeonDevilEditActivity.this.saveapply();
            NeonDevilEditActivity.this.bottom_option.setVisibility(View.VISIBLE);
            NeonDevilEditActivity.this.pattern_img.setImageBitmap(null);
            NeonDevilEditActivity.this.trans_img.setImageBitmap(null);
            NeonDevilEditActivity.this.oi = null;
            NeonDevilEditActivity.this.pattern_bmp = null;
            NeonDevilEditActivity.this.mainBottomItemClick();
            if (NeonDevilEditActivity.this.is_rotation.booleanValue()) {
                NeonDevilEditActivity.this.progressDialog.dismiss();
                NeonDevilEditActivity.main_bitmap = Constant.rotrate_bitmap;
                NeonDevilEditActivity.this.is_rotation = false;
                NeonDevilEditActivity.this.ll_rotation_layout.setVisibility(View.GONE);
                NeonDevilEditActivity.this.test_check.setVisibility(View.VISIBLE);
                NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.main_bitmap);
                NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                NeonDevilEditActivity.this.compositeDisposable.clear();
            } else {
                NeonDevilEditActivity.this.test_check.setVisibility(View.VISIBLE);
                NeonDevilEditActivity.this.ll_rotation_layout.setVisibility(View.GONE);
                NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.main_bitmap);
                NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                NeonDevilEditActivity.this.progressDialog.dismiss();
            }
            NeonDevilEditActivity.this.is_blur_effect = false;
            NeonDevilEditActivity.this.is_rotation = false;
            NeonDevilEditActivity.this.is_color_effect = false;
            NeonDevilEditActivity.this.filter_check = false;
            NeonDevilEditActivity.this.is_gpu_Image = false;
            NeonDevilEditActivity.this.is_crop = false;
            NeonDevilEditActivity.this.is_apply_option = true;
            NeonDevilEditActivity.this.AllBottomMainOptionVisi();
            AdapterPositionUtils.effectPosition = -1;
            AdapterPositionUtils.patternPosition = -1;
            AdapterPositionUtils.beautyPosition = 0;
        }
    }

    public void applysave() {
        this.apply.setVisibility(View.VISIBLE);
        this.save_image.setVisibility(View.GONE);
    }

    public void saveapply() {
        this.apply.setVisibility(View.GONE);
        this.save_image.setVisibility(View.VISIBLE);
    }

    public void visiset() {
        this.art_seekbar.setVisibility(View.VISIBLE);
        this.art_filter_seekbar.setVisibility(View.GONE);
    }

    public void FindId() {
        this.progress_circular = findViewById(R.id.progress_circular);
        this.title_text = (TextView) findViewById(R.id.title_text);
        this.erase_size_blur_seekbar = (SeekBar) findViewById(R.id.erase_size_blur_seekbar);
        this.rl_blur = (RelativeLayout) findViewById(R.id.rl_blur);
        this.ll_blur_layout = (LinearLayout) findViewById(R.id.ll_blur_layout);
        SeekBar seekBar = (SeekBar) findViewById(R.id.blur_progress);
        this.focul_zoom_seekbar = seekBar;
        seekBar.setMax(100);
        this.focul_zoom = (LinearLayout) findViewById(R.id.focul_zoom);
        this.focul_zoom_seekbar.setProgress(50);
        this.root = (LinearLayout) findViewById(R.id.root);
        this.save_bitmap_layout = (FrameLayout) findViewById(R.id.save_bitmap_layout);
        this.context = getBaseContext();
        this.Motion_blur_Button = findViewById(R.id.motionblur_button);
        this.touchImageView = (TouchImageView) findViewById(R.id.drawingImageView);
        this.brushImageView = (BrushImageView) findViewById(R.id.brushContainingView);
        this.rlImageViewContainer = (RelativeLayout) findViewById(R.id.rl_image_view_container);
        this.iv_backgroundimg = (TouchImageView) findViewById(R.id.iv_backgroundimg);
        seekBar_cartoon = (SeekBar) findViewById(R.id.seekBar_motionblur);
        this.erase_btn = findViewById(R.id.erase_btn);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.color_effect_main_layout = (RelativeLayout) findViewById(R.id.color_effect_main_layout);
        this.ll_erase_selection = (LinearLayout) findViewById(R.id.ll_erase_selection);
        this.img_original = (ImageView) findViewById(R.id.img_original);
        this.img_erase = (ImageView) findViewById(R.id.img_erase);
        this.img_brush = (ImageView) findViewById(R.id.img_brush);
        this.img_color_pallete = (ImageView) findViewById(R.id.img_color_pallete);
        this.r_original_color = (TextView) findViewById(R.id.r_original_color);
        this.r_erase_color = (TextView) findViewById(R.id.r_erase_color);
        this.r_brush = (TextView) findViewById(R.id.r_brush);
        this.r_color_pallete = (TextView) findViewById(R.id.r_color_pallete);
        this.main_layout = (LinearLayout) findViewById(R.id.main_layout);
        this.ll_color_effect_layout = (LinearLayout) findViewById(R.id.ll_color_effect_layout);
        this.radioButtonNormal = (RadioButton) findViewById(R.id.radio_normal);
        this.radioButtonBlur = (RadioButton) findViewById(R.id.radio_blur);
        this.radioButtonEmboss = (RadioButton) findViewById(R.id.radio_emboss);
        this.radioButtonTransparent = (RadioButton) findViewById(R.id.radio_transparent);
        this.radioButtonNormal.setOnClickListener(this.radioButtonHandler);
        this.radioButtonBlur.setOnClickListener(this.radioButtonHandler);
        this.radioButtonEmboss.setOnClickListener(this.radioButtonHandler);
        this.radioButtonTransparent.setOnClickListener(this.radioButtonHandler);

        RadioButton[] radioButtonArr = {this.radioButtonNormal, this.radioButtonBlur, this.radioButtonEmboss, this.radioButtonTransparent};
        this.radioButtonList = radioButtonArr;
        setRadioButtonsCheckState(this.selectedBrushType);
        this.art_recyview = (RecyclerView) findViewById(R.id.art_recyview);
        this.bottom_option = findViewById(R.id.bottom_option);
        this.img_crop = (ImageView) findViewById(R.id.img_crop);
        this.r_crop = (TextView) findViewById(R.id.r_crop);
        this.img_sticker = (ImageView) findViewById(R.id.img_sticker);
        this.r_sticker = (TextView) findViewById(R.id.r_sticker);
        this.img_art = (ImageView) findViewById(R.id.img_art);
        this.r_art = (TextView) findViewById(R.id.r_art);
        this.img_text = (ImageView) findViewById(R.id.img_text);
        this.r_text = (TextView) findViewById(R.id.r_text);
        this.img_filter = (ImageView) findViewById(R.id.img_filter);
        this.r_filter = (TextView) findViewById(R.id.r_filter);
        this.img_rotation = (ImageView) findViewById(R.id.img_rotation);
        this.r_rotation = (TextView) findViewById(R.id.r_rotation);
        this.img_paint = (ImageView) findViewById(R.id.img_paint);
        this.r_paint = (TextView) findViewById(R.id.r_paint);
        this.img_blur = (ImageView) findViewById(R.id.img_blur);
        this.r_blur = (TextView) findViewById(R.id.r_blur);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.btn_back);
        this.btn_back = linearLayout;
        linearLayout.setOnClickListener(this);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.ll_blur_effect);
        this.ll_blur_effect = linearLayout2;
        linearLayout2.setOnClickListener(this);
        this.cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        this.ll_crop_option = (LinearLayout) findViewById(R.id.ll_crop_option);
        this.freeScale = findViewById(R.id.free_bottom);
        this.Scale1to1 = findViewById(R.id.scale_1to1);
        this.Scale4to3 = findViewById(R.id.scale_4to3);
        this.Scale3to4 = findViewById(R.id.scale_3to4);
        this.Scale5to7 = findViewById(R.id.scale_5to7);
        this.Scale7to5 = findViewById(R.id.scale_7to5);
        this.Scale2to3 = findViewById(R.id.scale_2to3);
        this.Scale3to2 = findViewById(R.id.scale_3to2);
        this.Scale3to5 = findViewById(R.id.scale_3to5);
        this.Scale5to3 = findViewById(R.id.scale_5to3);
        this.Scale9to16 = findViewById(R.id.scale_9to16);
        this.Scale16to9 = findViewById(R.id.scale_16to9);
        this.freeScale.setOnClickListener(this);
        this.Scale1to1.setOnClickListener(this);
        this.Scale4to3.setOnClickListener(this);
        this.Scale3to4.setOnClickListener(this);
        this.Scale5to7.setOnClickListener(this);
        this.Scale7to5.setOnClickListener(this);
        this.Scale2to3.setOnClickListener(this);
        this.Scale3to2.setOnClickListener(this);
        this.Scale3to5.setOnClickListener(this);
        this.Scale5to3.setOnClickListener(this);
        this.Scale9to16.setOnClickListener(this);
        this.Scale16to9.setOnClickListener(this);
        this.roatate_imageview = (RotateImageView) findViewById(R.id.roatate_imageview);
        this.art_filter_seekbar = (SeekBar) findViewById(R.id.art_filter_seekbar);
        this.art_seekbar = (SeekBar) findViewById(R.id.art_seekbar);
        this.art_filter_seekbar = (SeekBar) findViewById(R.id.art_filter_seekbar);
        this.rotation_left = (FrameLayout) findViewById(R.id.rotation_left);
        this.rotation_right = (FrameLayout) findViewById(R.id.rotation_right);
        this.ho_filp = (FrameLayout) findViewById(R.id.ho_filp);
        this.ver_filp = (FrameLayout) findViewById(R.id.ver_filp);
        this.rotation_left.setOnClickListener(this);
        this.rotation_right.setOnClickListener(this);
        this.ho_filp.setOnClickListener(this);
        this.ver_filp.setOnClickListener(this);
        this.ll_rotation = (LinearLayout) findViewById(R.id.ll_rotation);
        this.ll_rotation_layout = (LinearLayout) findViewById(R.id.ll_rotation_layout);
        this.ll_rotation.setOnClickListener(this);
        this.iv_flip_left_select = (ImageView) findViewById(R.id.iv_flip_left_select);
        this.iv_flip_right_select = (ImageView) findViewById(R.id.iv_flip_right_select);
        this.iv_left_right_select = (ImageView) findViewById(R.id.iv_left_right_select);
        this.iv_flip_up_down_select = (ImageView) findViewById(R.id.iv_flip_up_down_select);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.ll_paint_color);
        this.ll_paint_color = linearLayout3;
        linearLayout3.setOnClickListener(this);
        this.save_image = (TextView) findViewById(R.id.save_image);
        this.apply = (TextView) findViewById(R.id.apply);
        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.banner_flipper);
        this.viewFlipper = viewFlipper;
        viewFlipper.setInAnimation(this, R.anim.in_bottom_to_top);
        this.viewFlipper.setOutAnimation(this, R.anim.out_bottom_to_top);
        this.middle_ly = findViewById(R.id.middle_ly);
        this.filterstickerList = new ArrayList<>();
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.llFace_option);
        this.llFace_option = linearLayout4;
        linearLayout4.setVisibility(View.GONE);
        this.ll_sticker_layout = (LinearLayout) findViewById(R.id.ll_sticker_layout);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.ll_crop);
        this.ll_crop = linearLayout5;
        linearLayout5.setOnClickListener(this);
        LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.ll_sticker);
        this.ll_sticker = linearLayout6;
        linearLayout6.setOnClickListener(this);
        LinearLayout linearLayout7 = (LinearLayout) findViewById(R.id.ll_filter);
        this.ll_filter = linearLayout7;
        linearLayout7.setOnClickListener(this);
        this.llFilter_option = (LinearLayout) findViewById(R.id.llFilter_option);
        this.fl_beauty = findViewById(R.id.fl_beauty);
        this.fl_overlay = findViewById(R.id.fl_overlay);
        this.fl_pattern = findViewById(R.id.fl_pattern);
        this.fl_beauty.setOnClickListener(this);
        this.fl_overlay.setOnClickListener(this);
        this.fl_pattern.setOnClickListener(this);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.filter_opacity_seek);
        this.filter_opacity_seek = seekBar2;
        seekBar2.setProgress(50);
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.pattern_opacity_seek);
        this.pattern_opacity_seek = seekBar3;
        seekBar3.setProgress(50);
        this.filter_opacity_text = (TextView) findViewById(R.id.filter_opacity_text);
        this.iv_overlay = (ImageView) findViewById(R.id.iv_overlay);
        this.iv_beauty = (ImageView) findViewById(R.id.iv_beauty);
        ImageView imageView = (ImageView) findViewById(R.id.close_btn_filter);
        this.close_btn_filter = imageView;
        imageView.setOnClickListener(this);
        this.rvFilter = (RecyclerView) findViewById(R.id.rvFilter);
        this.rvFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.trans_img = (ImageView) findViewById(R.id.trans_img);
        this.pattern_img = (ImageView) findViewById(R.id.pattern_img);
        this.ll_filter_opactity = (LinearLayout) findViewById(R.id.ll_filter_opactity);
        View findViewById = findViewById(R.id.llFilterReset);
        this.llFilterReset = findViewById;
        findViewById.setOnClickListener(this);
        this.ivSelected = (ImageView) findViewById(R.id.ivSelected);
        this.iv_pattern = (ImageView) findViewById(R.id.iv_pattern);
        LinearLayout linearLayout8 = (LinearLayout) findViewById(R.id.ll_text_add);
        this.ll_text_add = linearLayout8;
        linearLayout8.setOnClickListener(this);
        this.lltext_option = (LinearLayout) findViewById(R.id.lltext_option);
        this.rvText = (RecyclerView) findViewById(R.id.add_text_recycler_view);
        this.fl_style = findViewById(R.id.fl_style);
        this.fl_color = findViewById(R.id.fl_color);
        this.fl_alignment = findViewById(R.id.fl_alignment);
        this.fl_style.setOnClickListener(this);
        this.fl_color.setOnClickListener(this);
        this.fl_alignment.setOnClickListener(this);
        this.fl_style = findViewById(R.id.fl_style);
        this.iv_style = findViewById(R.id.iv_style);
        this.fl_color = findViewById(R.id.fl_color);
        this.iv_color = findViewById(R.id.iv_color);
        this.fl_style = findViewById(R.id.fl_style);
        this.fl_alignment = findViewById(R.id.fl_alignment);
        this.iv_alignment = findViewById(R.id.iv_alignment);
        this.ll_alignment = (LinearLayout) findViewById(R.id.ll_alignment);
        this.fl_style.setOnClickListener(this);
        this.fl_color.setOnClickListener(this);
        this.fl_alignment.setOnClickListener(this);
        this.iv_alignLeft = findViewById(R.id.iv_alignLeft);
        this.iv_alignCenter = findViewById(R.id.iv_alignCenter);
        this.iv_alignRight = findViewById(R.id.iv_alignRight);
        this.colorList = new ArrayList<>();
        View findViewById2 = findViewById(R.id.ivTextStickerAddDone);
        this.ivTextStickerAddDone = findViewById2;
        findViewById2.setOnClickListener(this);
        this.iv_left_select = findViewById(R.id.iv_left_select);
        this.iv_center_select = findViewById(R.id.iv_center_select);
        this.iv_right_select = findViewById(R.id.iv_right_select);
        this.iv_alignLeft.setOnClickListener(this);
        this.iv_alignCenter.setOnClickListener(this);
        this.iv_alignRight.setOnClickListener(this);
        AdapterPositionUtils.effectPosition = -1;
        AdapterPositionUtils.beautyPosition = 0;
        AdapterPositionUtils.textColorPosition = 5;
        AdapterPositionUtils.textFontPosition = 0;
        this.art_filter_seekbar = (SeekBar) findViewById(R.id.art_filter_seekbar);
        LinearLayout linearLayout9 = (LinearLayout) findViewById(R.id.ll_art_filter);
        this.ll_art_filter = linearLayout9;
        linearLayout9.setOnClickListener(this);
        this.ll_Art_Filter_layout = (LinearLayout) findViewById(R.id.ll_Art_Filter_layout);
        ImageView imageView2 = (ImageView) findViewById(R.id.ivStickerAddDone);
        this.ivStickerAddDone = imageView2;
        imageView2.setOnClickListener(this);
        this.rvFilterSticker = (RecyclerView) findViewById(R.id.rvFilterSticker);
        this.transparent_seek = (SeekBar) findViewById(R.id.transparent_seek);
        this.y_3d_seek = (SeekBar) findViewById(R.id.y_3d_seek);
        this.x_3d_seek = (SeekBar) findViewById(R.id.x_3d_seek);
        this.txt_3D = findViewById(R.id.txt_3D);
        this.stickerFilter = findViewById(R.id.filter1);
        this.transparent = findViewById(R.id.transparent);
        this.txt_3D.setOnClickListener(this);
        this.stickerFilter.setOnClickListener(this);
        this.transparent.setOnClickListener(this);
        this.txt_3D_txt = (TextView) findViewById(R.id.txt_3D_txt);
        this.filter_txt = (TextView) findViewById(R.id.filter_txt2);
        this.transparent_txt = (TextView) findViewById(R.id.transparent_txt);
        this.txt_3d_SeekLay = (LinearLayout) findViewById(R.id.txt_3d_SeekLay);
        this.filterSeekLay = (LinearLayout) findViewById(R.id.filterSeekLay);
        this.transparentSeekLay = (LinearLayout) findViewById(R.id.transparentSeekLay);
        this.transparentTxt = (TextView) findViewById(R.id.transparentTxt);
        this.textViewX3D = (TextView) findViewById(R.id.textViewX3D);
        this.textViewY3D = (TextView) findViewById(R.id.textViewY3D);
        LinearLayout linearLayout10 = (LinearLayout) findViewById(R.id.llSticker_edit_option);
        this.llSticker_edit_option = linearLayout10;
        linearLayout10.setVisibility(View.GONE);
        this.txt_stkr_rel = (RelativeLayout) findViewById(R.id.txt_stkr_rel);
        this.sticker_stkr_rel = (RelativeLayout) findViewById(R.id.sticker_stkr_rel);
        this.stickerlist = new ArrayList<>();
        this.MainStickerList = new ArrayList<>();
        this.sticker_progress = (TextView) findViewById(R.id.sticker_progress);
        this.rvEmoji = (RecyclerView) findViewById(R.id.rvEmoji);
        this.stickerPack1 = findViewById(R.id.stickerPack1);
        this.stickerPack2 = findViewById(R.id.stickerPack2);
        this.stickerPack3 = findViewById(R.id.stickerPack3);
        this.stickerPack4 = findViewById(R.id.stickerPack4);
        this.stickerPack5 = findViewById(R.id.stickerPack5);
        this.stickerPack6 = findViewById(R.id.stickerPack6);
        this.stickerPack7 = findViewById(R.id.stickerPack7);
        this.stickerPack8 = findViewById(R.id.stickerPack8);
        this.stickerPack9 = findViewById(R.id.stickerPack9);
        this.stickerPack10 = findViewById(R.id.stickerPack10);
        this.stickerPack11 = findViewById(R.id.stickerPack11);
        this.stickerPack12 = findViewById(R.id.stickerPack12);
        this.stickerPack13 = findViewById(R.id.stickerPack13);
        this.stickerPack14 = findViewById(R.id.stickerPack14);
        this.stickerPack15 = findViewById(R.id.stickerPack15);
        this.stickerPack1.setOnClickListener(this);
        this.stickerPack2.setOnClickListener(this);
        this.stickerPack3.setOnClickListener(this);
        this.stickerPack4.setOnClickListener(this);
        this.stickerPack5.setOnClickListener(this);
        this.stickerPack6.setOnClickListener(this);
        this.stickerPack7.setOnClickListener(this);
        this.stickerPack8.setOnClickListener(this);
        this.stickerPack9.setOnClickListener(this);
        this.stickerPack10.setOnClickListener(this);
        this.stickerPack11.setOnClickListener(this);
        this.stickerPack12.setOnClickListener(this);
        this.stickerPack13.setOnClickListener(this);
        this.stickerPack14.setOnClickListener(this);
        this.stickerPack15.setOnClickListener(this);
        addlistFromAssest(this.stickerlist, "horns");
    }

    void onDynamicApplyOptionsChanged(String str, int i, float f, float f2, float f3, int i2, Bitmap bitmap) {
        if (i2 == 0) {
            this.artfilterbitmapList.add(bitmap);
        }
        if (i2 == 1) {
            shiftingfilteradd("r", i, f, f2, f3, bitmap);
        } else if (i2 == 2) {
            shiftingfilteradd("g", i, f, f2, f3, bitmap);
        } else if (i2 == 3) {
            shiftingfilteradd("b", i, f, f2, f3, bitmap);
        } else if (i2 == 4) {
            shiftingfilteradd("rr", i, f, f2, f3, bitmap);
        } else if (i2 == 5) {
            shiftingfilteradd("gg", i, f, f2, f3, bitmap);
        } else if (i2 == 6) {
            shiftingfilteradd("bb", i, f, f2, f3, bitmap);
        } else if (i2 == 7) {
            new GlitchFilters(this, false);
            GlitchFilters glitchFilters = new GlitchFilters(this, false);
            this.gli = glitchFilters;
            glitchFilters.glitchRshiftFilters.changeParam(this.filterType, this.waveChoosen, 0.0f, 10.0f, this.thickness);
            this.gli.build();
            GPUImage gPUImage = new GPUImage(this);
            this.gPUImage = gPUImage;
            gPUImage.setImage(bitmap);
            this.gPUImage.setFilter(this.gli.group);
            this.artfilterbitmapList.add(this.gPUImage.getBitmapWithFilterApplied());
        } else if (i2 == 8) {
            GPUImage gPUImage2 = new GPUImage(this);
            this.gPUImage = gPUImage2;
            gPUImage2.setImage(bitmap);
            this.gPUImage.setFilter(new GPUImageColorInvertFilter());
            this.artfilterbitmapList.add(this.gPUImage.getBitmapWithFilterApplied());
        } else if (i2 == 9) {
            GPUImage gPUImage3 = new GPUImage(this);
            this.gPUImage = gPUImage3;
            gPUImage3.setImage(bitmap);
            this.gPUImage.setFilter(new GPUImageSwirlFilter(0.5f, 1.0f, new PointF(0.5f, 0.5f)));
            this.artfilterbitmapList.add(this.gPUImage.getBitmapWithFilterApplied());
        } else if (i2 == 10) {
            GPUImagePixelationFilter gPUImagePixelationFilter = new GPUImagePixelationFilter();
            gPUImagePixelationFilter.setPixel(10.0f);
            this.is_gpu_Image = true;
            GPUImage gPUImage4 = new GPUImage(this);
            this.gPUImage = gPUImage4;
            gPUImage4.setImage(bitmap);
            this.gPUImage.setFilter(gPUImagePixelationFilter);
            this.artfilterbitmapList.add(this.gPUImage.getBitmapWithFilterApplied());
        } else if (i2 == 11) {
            GPUImageEmbossFilter gPUImageEmbossFilter = new GPUImageEmbossFilter();
            gPUImageEmbossFilter.setIntensity(0.5f);
            GPUImage gPUImage5 = new GPUImage(this);
            this.gPUImage = gPUImage5;
            gPUImage5.setImage(bitmap);
            this.gPUImage.setFilter(gPUImageEmbossFilter);
            this.artfilterbitmapList.add(this.gPUImage.getBitmapWithFilterApplied());
        } else if (i2 == 12) {
            GPUImageSepiaToneFilter gPUImageSepiaToneFilter = new GPUImageSepiaToneFilter(0.5f);
            GPUImage gPUImage6 = new GPUImage(this);
            this.gPUImage = gPUImage6;
            gPUImage6.setImage(bitmap);
            this.gPUImage.setFilter(gPUImageSepiaToneFilter);
            this.artfilterbitmapList.add(this.gPUImage.getBitmapWithFilterApplied());
        } else if (i2 == 13) {
            GPUImage gPUImage7 = new GPUImage(this);
            this.gPUImage = gPUImage7;
            gPUImage7.setImage(bitmap);
            this.gPUImage.setFilter(new GPUImageBulgeDistortionFilter(0.25f, 0.5f, new PointF(0.5f, 0.5f)));
            this.artfilterbitmapList.add(this.gPUImage.getBitmapWithFilterApplied());
        }
    }

    public void shiftingfilteradd(String str, int i, float f, float f2, float f3, Bitmap bitmap) {
        new GlitchFilters(this, true);
        GlitchFilters glitchFilters = new GlitchFilters(this, false);
        this.gli = glitchFilters;
        glitchFilters.glitchRshiftFilters.changeParam(str, i, f, f2, f3);
        this.gli.build();
        GPUImage gPUImage = new GPUImage(this);
        this.gPUImage = gPUImage;
        gPUImage.setImage(bitmap);
        this.gPUImage.setFilter(this.gli.group);
        this.artfilterbitmapList.add(this.gPUImage.getBitmapWithFilterApplied());
    }

    @Override 
    public void onClick(View view) {

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int id = view.getId();
        switch (id) {
            case R.id.btn_back :
                onBackPressed();
                return;
            case R.id.close_btn_filter :
                this.filter_check = false;
                this.trans_img.setImageBitmap(null);
                this.pattern_img.setImageBitmap(null);
                this.llFilter_option.setVisibility(View.GONE);
                this.test_check.setImageBitmap(main_bitmap);
                this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                return;
            case R.id.filter1 :
                StickerItemViewAllView();
                this.stickerFilter.setSelected(true);
                this.txt_3d_SeekLay.setVisibility(View.GONE);
                this.filterSeekLay.setVisibility(View.VISIBLE);
                this.transparentSeekLay.setVisibility(View.GONE);
                this.rvFilterSticker.setVisibility(View.VISIBLE);
                filterStickerAdapterSetup();
                return;
            case R.id.free_bottom :
                CropAllViewSet();
                this.freeScale.setSelected(true);
                this.cropImageView.setCropMode(CropImageView.CropMode.FREE);
                return;
            case R.id.ho_filp :
                this.rotation_left.setSelected(false);
                this.rotation_right.setSelected(false);
                this.ho_filp.setSelected(true);
                this.ver_filp.setSelected(false);
                this.is_rotation = true;
                applysave();
                this.flip = true;
                type = 2;
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                Bitmap createBitmap = Bitmap.createBitmap(Constant.rotrate_bitmap, 0, 0, Constant.rotrate_bitmap.getWidth(), Constant.rotrate_bitmap.getHeight(), matrix, true);
                this.test_check.setImageBitmap(createBitmap);
                Constant.rotrate_bitmap = createBitmap;
                ImageViewTouch imageViewTouch = this.test_check;
                imageViewTouch.rotateImage(imageViewTouch.getRotateAngle());
                this.horizontal = true;
                return;
            case R.id.ivStickerAddDone :
                AllBottomMainOptionVisi();
                removeImageViewControll();
                this.txt_stkr_rel.removeAllViews();
                this.sticker_stkr_rel.removeAllViews();
                this.llSticker_edit_option.setVisibility(View.GONE);
                this.llFace_option.setVisibility(View.GONE);
                this.ll_crop_option.setVisibility(View.GONE);
                this.cropImageView.setVisibility(View.GONE);
                this.ll_sticker_layout.setVisibility(View.GONE);
                saveapply();
                return;
            case R.id.llFilterReset :
                this.ivSelected.setVisibility(View.VISIBLE);
                int i = this.no_filter;
                if (i == 0) {
                    this.trans_img.setImageBitmap(null);
                    AdapterPositionUtils.effectPosition = -1;
                } else if (i == 1) {
                    this.test_check.setImageBitmap(main_bitmap);
                    this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                } else if (i == 2) {
                    this.pattern_img.setImageBitmap(null);
                    AdapterPositionUtils.patternPosition = -1;
                }
                EffectAdapter effectAdapter = this.effectAdapter;
                if (effectAdapter != null) {
                    effectAdapter.notifyDataSetChanged();
                }
                PatternAdpater patternAdpater = this.patternAdpater;
                if (patternAdpater != null) {
                    patternAdpater.notifyDataSetChanged();
                }
                ThumbnailsAdapter thumbnailsAdapter = this.adapter;
                if (thumbnailsAdapter != null) {
                    thumbnailsAdapter.notifyDataSetChanged();
                }
                this.ll_filter_opactity.setVisibility(View.GONE);
                return;
            case R.id.ll_crop :
                this.title_text.setText(getResources().getString(R.string.crop));
                AllBottomMainOptionVisi();
                this.ll_crop.setSelected(true);
                this.img_crop.setVisibility(View.GONE);
                this.r_crop.setVisibility(View.VISIBLE);
                CropAllViewSet();
                this.freeScale.setSelected(true);
                removeImageViewControll();
                mainBottomItemClick();
                this.ll_crop_option.setVisibility(View.VISIBLE);
                this.cropImageView.setVisibility(View.VISIBLE);
                this.cropImageView.setImageBitmap(main_bitmap);
                this.test_check.setVisibility(View.GONE);
                applysave();
                this.is_crop = true;
                this.is_gpu_Image = false;
                this.filter_check = false;
                this.is_rotation = false;
                this.is_color_effect = false;
                this.is_blur_effect = false;
                this.cropImageView.setCropMode(CropImageView.CropMode.FREE);
                return;
            case R.id.ll_filter :
                this.title_text.setText(getResources().getString(R.string.filter));
                this.no_filter = 0;
                mainBottomItemClick();
                AdapterPositionUtils.effectPosition = -1;
                AdapterPositionUtils.patternPosition = -1;
                AdapterPositionUtils.beautyPosition = 0;
                this.fl_overlay.setSelected(true);
                this.fl_beauty.setSelected(false);
                this.fl_pattern.setSelected(false);
                AllBottomMainOptionVisi();
                this.ll_filter.setSelected(true);
                this.img_filter.setVisibility(View.GONE);
                this.r_filter.setVisibility(View.VISIBLE);
                this.test_check.setVisibility(View.VISIBLE);
                this.llFilter_option.setVisibility(View.VISIBLE);
                overLayAdapterSetup();
                this.iv_overlay.setVisibility(View.VISIBLE);
                if (AdapterPositionUtils.effectPosition == -1) {
                    this.ll_filter_opactity.setVisibility(View.GONE);
                    this.ivSelected.setVisibility(View.VISIBLE);
                } else {
                    this.ll_filter_opactity.setVisibility(View.VISIBLE);
                    this.ivSelected.setVisibility(View.GONE);
                }
                this.filter_check = true;
                this.is_gpu_Image = false;
                this.is_crop = false;
                this.is_rotation = false;
                this.is_color_effect = false;
                this.is_blur_effect = false;
                applysave();
                return;
            case R.id.ll_sticker :
                if (this.ll_sticker_layout.getVisibility() == View.VISIBLE) {
                    this.title_text.setText(getResources().getString(R.string.title_text));
                    AllBottomMainOptionVisi();
                    mainBottomItemClick();
                    this.apply.setVisibility(View.GONE);
                    this.save_image.setVisibility(View.VISIBLE);
                    return;
                }
                this.title_text.setText(getResources().getString(R.string.add_sticker));
                AllBottomMainOptionVisi();
                mainBottomItemClick();
                this.ll_sticker.setSelected(true);
                this.img_sticker.setVisibility(View.GONE);
                this.r_sticker.setVisibility(View.VISIBLE);
                this.stickerPack11.performClick();
                this.sticker_stkr_rel.getLayoutParams().height = (int) this.test_check.getBitmapRect().height();
                this.sticker_stkr_rel.getLayoutParams().width = (int) this.test_check.getBitmapRect().width();
                Constant.sticker_layout_height = (int) this.test_check.getBitmapRect().height();
                Constant.sticker_layout_height = (int) this.test_check.getBitmapRect().width();
                this.ll_sticker_layout.setVisibility(View.VISIBLE);
                this.llFace_option.setVisibility(View.VISIBLE);
                this.test_check.setVisibility(View.VISIBLE);
                this.apply.setVisibility(View.VISIBLE);
                this.save_image.setVisibility(View.GONE);
                new StickersLoad().execute(new Void[0]);
                this.test_check.setImageBitmap(main_bitmap);
                this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                this.is_crop = false;
                this.is_gpu_Image = false;
                this.filter_check = false;
                this.is_rotation = false;
                this.is_color_effect = false;
                this.is_blur_effect = false;
                return;
            case R.id.ll_text_add :
                AllBottomMainOptionVisi();
                mainBottomItemClick();
                this.is_gpu_Image = false;
                this.is_crop = false;
                this.filter_check = false;
                this.is_rotation = false;
                this.is_color_effect = false;
                this.is_blur_effect = false;
                this.ll_text_add.setSelected(true);
                this.img_text.setVisibility(View.GONE);
                this.r_text.setVisibility(View.VISIBLE);
                this.txt_stkr_rel.getLayoutParams().height = (int) this.test_check.getBitmapRect().height();
                this.txt_stkr_rel.getLayoutParams().width = (int) this.test_check.getBitmapRect().width();
                Constant.sticker_layout_height = (int) this.test_check.getBitmapRect().height();
                Constant.sticker_layout_height = (int) this.test_check.getBitmapRect().width();
                this.test_check.setVisibility(View.VISIBLE);
                this.lltext_option.setVisibility(View.VISIBLE);
                applysave();
                addedtextDialog();
                return;
            case R.id.stickerPack11 :
                StickerAllPackSet();
                this.stickerPack11.setSelected(true);
                this.stickerPackListener.SelectStickerPack(10);
                return;
            case R.id.stickerPack12 :
                StickerAllPackSet();
                this.stickerPack12.setSelected(true);
                this.stickerPackListener.SelectStickerPack(11);
                return;
            case R.id.stickerPack13 :
                StickerAllPackSet();
                this.stickerPack13.setSelected(true);
                this.stickerPackListener.SelectStickerPack(12);
                return;
            case R.id.stickerPack14 :
                StickerAllPackSet();
                this.stickerPack14.setSelected(true);
                this.stickerPackListener.SelectStickerPack(13);
                return;
            case R.id.stickerPack15 :
                StickerAllPackSet();
                this.stickerPack15.setSelected(true);
                this.stickerPackListener.SelectStickerPack(14);
                return;
            case R.id.stickerPack2 :
                StickerAllPackSet();
                this.stickerPack2.setSelected(true);
                this.stickerPackListener.SelectStickerPack(1);
                return;
            case R.id.stickerPack3 :
                StickerAllPackSet();
                this.stickerPack3.setSelected(true);
                this.stickerPackListener.SelectStickerPack(2);
                return;
            case R.id.stickerPack4 :
                StickerAllPackSet();
                this.stickerPack4.setSelected(true);
                this.stickerPackListener.SelectStickerPack(3);
                return;
            case R.id.stickerPack6 :
                StickerAllPackSet();
                this.stickerPack6.setSelected(true);
                this.stickerPackListener.SelectStickerPack(5);
                return;
            case R.id.stickerPack7 :
                StickerAllPackSet();
                this.stickerPack7.setSelected(true);
                this.stickerPackListener.SelectStickerPack(6);
                return;
            case R.id.stickerPack8 :
                StickerAllPackSet();
                this.stickerPack8.setSelected(true);
                this.stickerPackListener.SelectStickerPack(7);
                return;
            case R.id.stickerPack9 :
                StickerAllPackSet();
                this.stickerPack9.setSelected(true);
                this.stickerPackListener.SelectStickerPack(8);
                return;
            case R.id.transparent :
                StickerItemViewAllView();
                this.transparent.setSelected(true);
                this.txt_3d_SeekLay.setVisibility(View.GONE);
                this.filterSeekLay.setVisibility(View.GONE);
                this.transparentSeekLay.setVisibility(View.VISIBLE);
                this.rvFilterSticker.setVisibility(View.GONE);
                return;
            case R.id.txt_3D :
                StickerItemViewAllView();
                this.txt_3D.setSelected(true);
                this.txt_3d_SeekLay.setVisibility(View.VISIBLE);
                this.filterSeekLay.setVisibility(View.GONE);
                this.transparentSeekLay.setVisibility(View.GONE);
                this.rvFilterSticker.setVisibility(View.GONE);
                return;
            case R.id.ver_filp :
                this.rotation_left.setSelected(false);
                this.rotation_right.setSelected(false);
                this.ho_filp.setSelected(false);
                this.ver_filp.setSelected(true);
                applysave();
                this.is_rotation = true;
                this.flip = true;
                Matrix matrix2 = new Matrix();
                matrix2.preScale(1.0f, -1.0f);
                Bitmap createBitmap2 = Bitmap.createBitmap(Constant.rotrate_bitmap, 0, 0, Constant.rotrate_bitmap.getWidth(), Constant.rotrate_bitmap.getHeight(), matrix2, true);
                this.test_check.setImageBitmap(createBitmap2);
                Constant.rotrate_bitmap = createBitmap2;
                ImageViewTouch imageViewTouch2 = this.test_check;
                imageViewTouch2.rotateImage(imageViewTouch2.getRotateAngle());
                type = 1;
                return;
            default:
                switch (id) {
                    case R.id.fl_alignment :
                        this.fl_style.setSelected(false);
                        this.fl_color.setSelected(false);
                        this.fl_alignment.setSelected(true);
                        this.ll_alignment.setVisibility(View.VISIBLE);
                        this.rvText.setVisibility(View.GONE);
                        return;
                    case R.id.fl_beauty :
                        this.no_filter = 1;
                        this.fl_overlay.setSelected(false);
                        this.fl_beauty.setSelected(true);
                        this.fl_pattern.setSelected(false);
                        beautyAdapterSetup();
                        this.ll_filter_opactity.setVisibility(View.GONE);
                        this.llFilterReset.setVisibility(View.GONE);
                        return;
                    case R.id.fl_color :
                        this.fl_style.setSelected(false);
                        this.fl_color.setSelected(true);
                        this.fl_alignment.setSelected(false);
                        colorAdapterSetup();
                        this.ll_alignment.setVisibility(View.GONE);
                        this.rvText.setVisibility(View.VISIBLE);
                        return;
                    case R.id.fl_overlay :
                        this.no_filter = 0;
                        this.fl_overlay.setSelected(true);
                        this.fl_beauty.setSelected(false);
                        this.fl_pattern.setSelected(false);
                        this.pattern_opacity_seek.setVisibility(View.GONE);
                        this.filter_opacity_seek.setVisibility(View.VISIBLE);
                        overLayAdapterSetup();
                        if (AdapterPositionUtils.effectPosition == -1) {
                            this.ll_filter_opactity.setVisibility(View.GONE);
                            this.ivSelected.setVisibility(View.VISIBLE);
                        } else {
                            this.ll_filter_opactity.setVisibility(View.VISIBLE);
                            this.ivSelected.setVisibility(View.GONE);
                        }
                        this.llFilterReset.setVisibility(View.VISIBLE);
                        return;
                    case R.id.fl_pattern :
                        this.no_filter = 2;
                        this.fl_overlay.setSelected(false);
                        this.fl_beauty.setSelected(false);
                        this.fl_pattern.setSelected(true);
                        this.pattern_opacity_seek.setVisibility(View.VISIBLE);
                        this.filter_opacity_seek.setVisibility(View.GONE);
                        overPatternAdapterSetup();
                        if (AdapterPositionUtils.patternPosition == -1) {
                            this.ll_filter_opactity.setVisibility(View.GONE);
                            this.ivSelected.setVisibility(View.VISIBLE);
                        } else {
                            this.ll_filter_opactity.setVisibility(View.VISIBLE);
                            this.ivSelected.setVisibility(View.GONE);
                        }
                        this.llFilterReset.setVisibility(View.VISIBLE);
                        return;
                    case R.id.fl_style :
                        this.fl_style.setSelected(true);
                        this.fl_color.setSelected(false);
                        this.fl_alignment.setSelected(false);
                        fontAdapterSetup();
                        this.ll_alignment.setVisibility(View.GONE);
                        this.rvText.setVisibility(View.VISIBLE);
                        return;
                    default:
                        switch (id) {
                            case R.id.iv_alignCenter :
                                setGravityText("C");
                                this.iv_alignLeft.setSelected(false);
                                this.iv_alignCenter.setSelected(true);
                                this.iv_alignRight.setSelected(false);
                                this.iv_left_select.setVisibility(View.INVISIBLE);
                                this.iv_center_select.setVisibility(View.VISIBLE);
                                this.iv_right_select.setVisibility(View.INVISIBLE);
                                return;
                            case R.id.iv_alignLeft :
                                setGravityText("L");
                                this.iv_alignLeft.setSelected(true);
                                this.iv_alignCenter.setSelected(false);
                                this.iv_alignRight.setSelected(false);
                                this.iv_left_select.setVisibility(View.VISIBLE);
                                this.iv_center_select.setVisibility(View.INVISIBLE);
                                this.iv_right_select.setVisibility(View.INVISIBLE);
                                return;
                            case R.id.iv_alignRight :
                                setGravityText("R");
                                this.iv_alignLeft.setSelected(false);
                                this.iv_alignCenter.setSelected(false);
                                this.iv_alignRight.setSelected(true);
                                this.iv_left_select.setVisibility(View.INVISIBLE);
                                this.iv_center_select.setVisibility(View.INVISIBLE);
                                this.iv_right_select.setVisibility(View.VISIBLE);
                                return;
                            default:
                                switch (id) {
                                    case R.id.ll_art_filter :
                                        this.title_text.setText(getResources().getString(R.string.art_filter));
                                        mainBottomItemClick();
                                        this.is_gpu_Image = true;
                                        this.is_crop = false;
                                        this.filter_check = false;
                                        this.is_rotation = false;
                                        this.is_color_effect = false;
                                        this.is_blur_effect = false;
                                        this.artfilterbitmapList.clear();
                                        new ArtFilter().execute(new Void[0]);
                                        AllBottomMainOptionVisi();
                                        this.ll_art_filter.setSelected(true);
                                        this.img_art.setVisibility(View.GONE);
                                        this.r_art.setVisibility(View.VISIBLE);
                                        applysave();
                                        mainBottomItemClick();
                                        this.test_check.setVisibility(View.VISIBLE);
                                        this.art_filter_seekbar.setVisibility(View.VISIBLE);
                                        this.ll_Art_Filter_layout.setVisibility(View.VISIBLE);
                                        this.art_seekbar.setVisibility(View.GONE);
                                        this.gli = new GlitchFilters(this, false);
                                        return;
                                    case R.id.ll_blur_effect :
                                        this.title_text.setText(getResources().getString(R.string.blur_effect));
                                        this.is_blur_effect = true;
                                        this.is_rotation = false;
                                        this.is_color_effect = false;
                                        this.filter_check = false;
                                        this.is_gpu_Image = false;
                                        this.is_crop = false;
                                        AllBottomMainOptionVisi();
                                        mainBottomItemClick();
                                        Constant.blur_bitmap_height = (int) this.test_check.getBitmapRect().height();
                                        Constant.blur_bitmap_width = (int) this.test_check.getBitmapRect().width();
                                        this.test_check.setVisibility(View.GONE);
                                        this.paths = new ArrayList<>();
                                        this.brushSizes = new Vector<>();
                                        this.MODE = 0;
                                        try {
                                            getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
                                            this.screenInches = Math.sqrt(Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2.0d) + Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2.0d));
                                            System.out.println("Screen inches : " + this.screenInches);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (this.screenInches >= 4.5d) {
                                            this.brushSize = 150.0f;
                                        } else {
                                            this.brushSize = 55.0f;
                                        }
                                        this.mainViewSize = new Point();
                                        getWindowManager().getDefaultDisplay().getSize(this.mainViewSize);
                                        this.imageViewWidth = this.mainViewSize.x;
                                        this.imageViewHeight = this.mainViewSize.y;
                                        this.OriginalBitmap = main_bitmap;
                                        try {
                                            loadImage();
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        this.save_bitmap_layout.getLayoutParams().height = Constant.blur_bitmap_height;
                                        this.save_bitmap_layout.getLayoutParams().width = Constant.blur_bitmap_width;
                                        this.save_bitmap_layout.requestLayout();
                                        FocalZoom focalZoom = new FocalZoom();
                                        this.focalZoom = focalZoom;
                                        focalZoom.execute(new Void[0]);
                                        this.focul_zoom.setOnClickListener(new View.OnClickListener() { 
                                            @Override 
                                            public void onClick(View view2) {
                                                NeonDevilEditActivity.this.blur_click = 0;
                                                if (AppUtil.isClickable()) {
                                                    NeonDevilEditActivity.this.focalZoom = new FocalZoom();
                                                    NeonDevilEditActivity.this.focalZoom.execute(new Void[0]);
                                                }
                                            }
                                        });
                                        this.focul_zoom_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
                                            @Override 
                                            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                                            }

                                            @Override 
                                            public void onStartTrackingTouch(SeekBar seekBar) {
                                            }

                                            @Override 
                                            public void onStopTrackingTouch(SeekBar seekBar) {
                                                NeonDevilEditActivity.this.progressDialogeffect = new ProgressDialog(NeonDevilEditActivity.this.alertContext);
                                                NeonDevilEditActivity.this.progressDialogeffect.setMessage("please wait");
                                                NeonDevilEditActivity.this.progressDialogeffect.setCancelable(false);
                                                NeonDevilEditActivity.this.brushImageView.setVisibility(View.GONE);
                                                if (NeonDevilEditActivity.this.is_focal_zoom.booleanValue()) {
                                                    return;
                                                }
                                                NeonDevilEditActivity.this.focalZoom = new FocalZoom();
                                                NeonDevilEditActivity.this.focalZoom.execute(new Void[0]);
                                            }
                                        });
                                        this.erase_btn.setOnClickListener(new View.OnClickListener() { 
                                            @Override 
                                            public void onClick(View view2) {
                                                if (AppUtil.isClickable()) {
                                                    try {
                                                        if (NeonDevilEditActivity.this.rlImageViewContainer.getVisibility() != View.VISIBLE) {
                                                            NeonDevilEditActivity.this.rlImageViewContainer.setVisibility(View.VISIBLE);
                                                            NeonDevilEditActivity.this.drawingPath = new Path();
                                                            NeonDevilEditActivity.this.getWindowManager().getDefaultDisplay().getSize(NeonDevilEditActivity.this.mainViewSize);
                                                            NeonDevilEditActivity.this.rlImageViewContainer.getLayoutParams().height = NeonDevilEditActivity.this.mainViewSize.y;
                                                            NeonDevilEditActivity.this.touchImageView.setOnTouchListener(new OnTouchListner());
                                                            NeonDevilEditActivity.selectedphoto = NeonDevilEditActivity.originalbmp;
                                                            NeonDevilEditActivity.this.iv_backgroundimg.setImageBitmap(NeonDevilEditActivity.selectedphoto);
                                                            NeonDevilEditActivity.this.setBitMap();
                                                            NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                                                            neonDevilEditActivity.updateBrush(neonDevilEditActivity.mainViewSize.x / 2, NeonDevilEditActivity.this.mainViewSize.y / 2);
                                                            NeonDevilEditActivity.this.iserase = true;
                                                            NeonDevilEditActivity.this.brushImageView.setVisibility(View.VISIBLE);
                                                            return;
                                                        }
                                                        NeonDevilEditActivity.this.iserase = true;
                                                        NeonDevilEditActivity.this.brushImageView.setVisibility(View.VISIBLE);
                                                        if (NeonDevilEditActivity.this.blur_click == 0) {
                                                            NeonDevilEditActivity.seekBar_cartoon.setVisibility(View.GONE);
                                                            NeonDevilEditActivity.this.focul_zoom_seekbar.setVisibility(View.VISIBLE);
                                                            return;
                                                        }
                                                        NeonDevilEditActivity.seekBar_cartoon.setVisibility(View.VISIBLE);
                                                        NeonDevilEditActivity.this.focul_zoom_seekbar.setVisibility(View.GONE);
                                                    } catch (Exception unused) {
                                                    }
                                                }
                                            }
                                        });
                                        this.Motion_blur_Button.setOnClickListener(new View.OnClickListener() { 
                                            @Override 
                                            public void onClick(View view2) {
                                                if (AppUtil.isClickable()) {
                                                    NeonDevilEditActivity.this.blur_click = 1;
                                                    NeonDevilEditActivity.this.focul_zoom.setSelected(false);
                                                    NeonDevilEditActivity.this.erase_btn.setSelected(false);
                                                    NeonDevilEditActivity.this.Motion_blur_Button.setSelected(true);
                                                    NeonDevilEditActivity.this.brushImageView.setVisibility(View.GONE);
                                                    NeonDevilEditActivity.this.focul_zoom_seekbar.setVisibility(View.GONE);
                                                    try {
                                                        NeonDevilEditActivity.this.rlImageViewContainer.setVisibility(View.VISIBLE);
                                                        NeonDevilEditActivity.this.brushImageView.setVisibility(View.GONE);
                                                        NeonDevilEditActivity.this.drawingPath = new Path();
                                                        Display defaultDisplay = NeonDevilEditActivity.this.getWindowManager().getDefaultDisplay();
                                                        NeonDevilEditActivity.this.mainViewSize = new Point();
                                                        defaultDisplay.getSize(NeonDevilEditActivity.this.mainViewSize);
                                                        NeonDevilEditActivity.this.rlImageViewContainer.getLayoutParams().height = NeonDevilEditActivity.this.mainViewSize.y;
                                                        NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                                                        neonDevilEditActivity.imageViewWidth = neonDevilEditActivity.mainViewSize.x;
                                                        NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                                                        neonDevilEditActivity2.imageViewHeight = neonDevilEditActivity2.rlImageViewContainer.getLayoutParams().height;
                                                        NeonDevilEditActivity.this.touchImageView.setOnTouchListener(new OnTouchListner());
                                                        NeonDevilEditActivity.selectedphoto = NeonDevilEditActivity.originalbmp;
                                                        NeonDevilEditActivity.this.originalBitmap = NeonDevilEditActivity.doRadialBlur(NeonDevilEditActivity.selectedphoto, 2, 2, 4);
                                                        NeonDevilEditActivity.this.iv_backgroundimg.setImageBitmap(NeonDevilEditActivity.selectedphoto);
                                                        NeonDevilEditActivity.this.setBitMap();
                                                        NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this;
                                                        neonDevilEditActivity3.updateBrush(neonDevilEditActivity3.mainViewSize.x / 2, NeonDevilEditActivity.this.mainViewSize.y / 2);
                                                        NeonDevilEditActivity.this.iserase = false;
                                                        NeonDevilEditActivity.selectedphoto = NeonDevilEditActivity.originalbmp;
                                                        NeonDevilEditActivity.seekBar_cartoon.setProgress(4);
                                                        NeonDevilEditActivity.seekBar_cartoon.setVisibility(View.VISIBLE);
                                                    } catch (Exception unused) {
                                                    }
                                                }
                                            }
                                        });
                                        seekBar_cartoon.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
                                            @Override 
                                            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                                            }

                                            @Override 
                                            public void onStartTrackingTouch(SeekBar seekBar) {
                                            }

                                            @Override 
                                            public void onStopTrackingTouch(SeekBar seekBar) {
                                                NeonDevilEditActivity.this.drawingPath = new Path();
                                                Display defaultDisplay = NeonDevilEditActivity.this.getWindowManager().getDefaultDisplay();
                                                NeonDevilEditActivity.this.mainViewSize = new Point();
                                                defaultDisplay.getSize(NeonDevilEditActivity.this.mainViewSize);
                                                NeonDevilEditActivity.this.rlImageViewContainer.getLayoutParams().height = NeonDevilEditActivity.this.mainViewSize.y;
                                                NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                                                neonDevilEditActivity.imageViewWidth = neonDevilEditActivity.mainViewSize.x;
                                                NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                                                neonDevilEditActivity2.imageViewHeight = neonDevilEditActivity2.rlImageViewContainer.getLayoutParams().height;
                                                NeonDevilEditActivity.this.touchImageView.setOnTouchListener(new OnTouchListner());
                                                NeonDevilEditActivity.this.originalBitmap = NeonDevilEditActivity.doRadialBlur(NeonDevilEditActivity.selectedphoto, 2, 2, NeonDevilEditActivity.seekBar_cartoon.getProgress());
                                                NeonDevilEditActivity.this.iv_backgroundimg.setImageBitmap(NeonDevilEditActivity.selectedphoto);
                                                NeonDevilEditActivity.this.setBitMap();
                                                NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this;
                                                neonDevilEditActivity3.updateBrush(neonDevilEditActivity3.mainViewSize.x / 2, NeonDevilEditActivity.this.mainViewSize.y / 2);
                                                NeonDevilEditActivity.this.brushImageView.setVisibility(View.GONE);
                                            }
                                        });
                                        return;
                                    default:
                                        switch (id) {
                                            case R.id.ll_paint_color :
                                                this.title_text.setText(getResources().getString(R.string.color_effect));
                                                this.topHeightOffset = 0;
                                                this.bottomHeightOffset = 0;
                                                this.leftWidthOffset = 0;
                                                this.rightWidthOffset = 0;
                                                this.is_rotation = false;
                                                this.is_color_effect = true;
                                                this.filter_check = false;
                                                this.is_gpu_Image = false;
                                                this.is_crop = false;
                                                this.is_blur_effect = false;
                                                AllBottomMainOptionVisi();
                                                mainBottomItemClick();
                                                this.ll_paint_color.setSelected(true);
                                                this.img_paint.setVisibility(View.GONE);
                                                this.r_paint.setVisibility(View.VISIBLE);
                                                this.color_effect_main_layout.setVisibility(View.VISIBLE);
                                                this.ll_color_effect_layout.setVisibility(View.VISIBLE);
                                                this.bottom_option.setVisibility(View.GONE);
                                                Constant.color_bitmap = main_bitmap;
                                                applysave();
                                                this.myApplication = (MyApplication) getApplication();
                                                rectSize = 100;
                                                float f = (100 * 2) / 5;
                                                this.DEFAULT_STROKE_WIDTH = f;
                                                this.MAX_STROKE_WIDTH = 100;
                                                this.strokeWidth = f;
                                                this.leftWidthOffset = (int) screenWidth;
                                                this.bottomHeightOffset = (int) screenHeight;
                                                this.thumbSize = (int) getResources().getDimension(R.dimen.session_image_dimen);
                                                this.data = getLastNonConfigurationInstance();
                                                setProgressDialog();
                                                this.activity = this;
                                                Paint paint = new Paint();
                                                this.mPaint = paint;
                                                paint.setAntiAlias(true);
                                                this.mPaint.setDither(true);
                                                this.mPaint.setStyle(Paint.Style.STROKE);
                                                this.mPaint.setStrokeJoin(Paint.Join.ROUND);
                                                this.mPaint.setStrokeCap(Paint.Cap.ROUND);
                                                this.sepiaPaint = new Paint();
                                                ColorMatrix colorMatrix = new ColorMatrix();
                                                colorMatrix.set(new float[]{0.393f, 0.769f, 0.189f, 0.0f, 0.0f, 0.349f, 0.686f, 0.168f, 0.0f, 0.0f, 0.272f, 0.534f, 0.131f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
                                                this.sepiaPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                                                this.grayPaint = new Paint();
                                                ColorMatrix colorMatrix2 = new ColorMatrix();
                                                colorMatrix2.setSaturation(0.0f);
                                                this.grayPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
                                                this.mEmboss = new EmbossMaskFilter(new float[]{1.0f, 1.0f, 1.0f}, 0.4f, 6.0f, 3.5f);
                                                this.mBlur = new BlurMaskFilter(20.0f, BlurMaskFilter.Blur.SOLID);
                                                this.color_effect_main_layout.setGravity(48);
                                                MyView myView2 = new MyView(this);
                                                myView = myView2;
                                                this.color_effect_main_layout.addView(myView2);
                                                try {
                                                    Method method = myView.getClass().getMethod("setLayerType", Integer.TYPE, Paint.class);
                                                    MyView myView3 = myView;
                                                    Object[] objArr = new Object[2];
                                                    objArr[0] = 1;
                                                    method.invoke(myView3, objArr);
                                                } catch (IllegalAccessException e3) {
                                                    e3.printStackTrace();
                                                } catch (IllegalArgumentException e4) {
                                                    e4.printStackTrace();
                                                } catch (NoSuchMethodException unused) {
                                                } catch (InvocationTargetException e5) {
                                                    e5.printStackTrace();
                                                }
                                                this.paintButton = findViewById(R.id.paint_button);
                                                this.eraseButton = findViewById(R.id.erase_button);
                                                this.colorButton = (LinearLayout) findViewById(R.id.button_color_paint);
                                                this.paletteButton = (LinearLayout) findViewById(R.id.button_color_pallete);
                                                this.colorButtonBgOrg = BitmapFactory.decodeResource(getResources(), R.drawable.paint_bucket);
                                                this.brushButtonBgOrg = BitmapFactory.decodeResource(getResources(), R.drawable.brush_color);
                                                execQueue();
                                                this.selectedBrushType = this.selectedBrushItemFromBrushDialog;
                                                this.MAX_SIZE = this.MAX_STROKE_WIDTH;
                                                this.radioButtonNormal.setSelected(true);
                                                this.radioButtonBlur.setSelected(false);
                                                this.radioButtonEmboss.setSelected(false);
                                                this.radioButtonTransparent.setSelected(false);
                                                AllColorEffectBootomView();
                                                this.paintButton.setSelected(true);
                                                this.img_original.setVisibility(View.GONE);
                                                this.r_original_color.setVisibility(View.VISIBLE);
                                                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { 
                                                    @Override 
                                                    public void onStartTrackingTouch(SeekBar seekBar) {
                                                    }

                                                    @Override 
                                                    public void onStopTrackingTouch(SeekBar seekBar) {
                                                    }

                                                    @Override 
                                                    public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                                                        if (NeonDevilEditActivity.this.mySizePickerView != null) {
                                                            NeonDevilEditActivity.this.mySizePickerView.setVisibility(View.VISIBLE);
                                                        }
                                                        NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                                                        neonDevilEditActivity.currentSize = neonDevilEditActivity.getSeekBarValue();
                                                        NeonDevilEditActivity.this.main_layout.setVisibility(View.VISIBLE);
                                                        NeonDevilEditActivity.this.mySizePickerView.updateSize(NeonDevilEditActivity.this.currentSize);
                                                        NeonDevilEditActivity.this.mySizePickerView.invalidate();
                                                        NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                                                        neonDevilEditActivity2.selectedBrushItemFromBrushDialog = neonDevilEditActivity2.getSelectedBrushType();
                                                        NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this;
                                                        neonDevilEditActivity3.selectedBrushTyheChanged(neonDevilEditActivity3.selectedBrushItemFromBrushDialog);
                                                        float size = NeonDevilEditActivity.this.getSize();
                                                        Log.e("localStrokeWidth___", "onProgressChanged: " + size);
                                                        NeonDevilEditActivity.this.strokeWidth = size;
                                                    }
                                                };
                                                SeekBar seekBar = (SeekBar) findViewById(R.id.brush_size_seekbar);
                                                this.brush_size_seekbar = seekBar;
                                                seekBar.setMax(this.MAX_SIZE);
                                                this.brush_size_seekbar.setOnSeekBarChangeListener(onSeekBarChangeListener);
                                                this.mySizePickerView = new SizePickerView(this, this.currentSize);
                                                this.brush_size_seekbar.setProgress((int) this.mInitialSize);
                                                this.zoomSeekBar = (SeekBar) findViewById(R.id.zoom_seekbar);
                                                myView.canvasMatrix.getValues(this.values);
                                                SeekBar seekBar2 = this.zoomSeekBar;
                                                float f2 = this.values[0];
                                                float f3 = this.MIN_ZOOM;
                                                seekBar2.setProgress((int) (((f2 - f3) / (this.MAX_ZOOM - f3)) * 100.0f));
                                                this.zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
                                                    @Override 
                                                    public void onStartTrackingTouch(SeekBar seekBar3) {
                                                    }

                                                    @Override 
                                                    public void onStopTrackingTouch(SeekBar seekBar3) {
                                                    }

                                                    @Override 
                                                    public void onProgressChanged(SeekBar seekBar3, int i2, boolean z) {
                                                        float f4 = NeonDevilEditActivity.this.MIN_ZOOM + ((i2 * (NeonDevilEditActivity.this.MAX_ZOOM - NeonDevilEditActivity.this.MIN_ZOOM)) / 100.0f);
                                                        NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this.neonDevilEditActivity;
                                                        NeonDevilEditActivity.myView.canvasMatrix.getValues(NeonDevilEditActivity.this.values);
                                                        NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                                                        neonDevilEditActivity2.oldZoom = neonDevilEditActivity2.values[0];
                                                        float f5 = f4 / NeonDevilEditActivity.this.oldZoom;
                                                        NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this.neonDevilEditActivity;
                                                        NeonDevilEditActivity.myView.canvasMatrix.postScale(f5, f5, NeonDevilEditActivity.screenWidth / 2.0f, NeonDevilEditActivity.screenHeight / 2.0f);
                                                        NeonDevilEditActivity neonDevilEditActivity4 = NeonDevilEditActivity.this.neonDevilEditActivity;
                                                        NeonDevilEditActivity.myView.invalidate();
                                                    }
                                                });
                                                int childCount = this.main_layout.getChildCount();
                                                if (childCount > 0) {
                                                    childCount--;
                                                }
                                                this.main_layout.removeAllViews();
                                                this.main_layout.addView(this.mySizePickerView, childCount);
                                                this.main_layout.setVisibility(View.GONE);
                                                return;
                                            case R.id.ll_rotation :
                                                this.title_text.setText(getResources().getString(R.string.title_text));
                                                mainBottomItemClick();
                                                AllBottomMainOptionVisi();
                                                this.ll_rotation.setSelected(true);
                                                this.img_rotation.setVisibility(View.GONE);
                                                this.r_rotation.setVisibility(View.VISIBLE);
                                                this.ll_rotation_layout.setVisibility(View.VISIBLE);
                                                this.roatate_imageview.addBit(main_bitmap, this.test_check.getBitmapRect());
                                                this.test_check.setVisibility(View.VISIBLE);
                                                this.roatate_imageview.setVisibility(View.GONE);
                                                this.test_check.rotateImage(0);
                                                Constant.rotrate_bitmap = main_bitmap;
                                                this.is_rotation = true;
                                                this.filter_check = false;
                                                this.is_gpu_Image = false;
                                                this.is_crop = false;
                                                this.is_color_effect = false;
                                                this.is_blur_effect = false;
                                                this.rotation_left.setSelected(false);
                                                this.rotation_right.setSelected(false);
                                                this.ho_filp.setSelected(false);
                                                this.ver_filp.setSelected(false);
                                                return;
                                            default:
                                                switch (id) {
                                                    case R.id.rotation_left :
                                                        this.rotation_left.setSelected(true);
                                                        this.rotation_right.setSelected(false);
                                                        this.ho_filp.setSelected(false);
                                                        this.ver_filp.setSelected(false);
                                                        this.is_rotation = true;
                                                        this.flip = false;
                                                        type = 0;
                                                        int rotateAngle = this.test_check.getRotateAngle() - 90;
                                                        image_angle = rotateAngle;
                                                        this.test_check.rotateImage(rotateAngle);
                                                        Log.e("angle__", "rotation_left: " + image_angle);
                                                        Bitmap RotateBitmap = Constant.RotateBitmap(main_bitmap, (float) image_angle);
                                                        this.test_check.setImageBitmap(RotateBitmap);
                                                        Constant.rotrate_bitmap = RotateBitmap;
                                                        applysave();
                                                        return;
                                                    case R.id.rotation_right :
                                                        this.rotation_left.setSelected(false);
                                                        this.rotation_right.setSelected(true);
                                                        this.ho_filp.setSelected(false);
                                                        this.ver_filp.setSelected(false);
                                                        this.is_rotation = true;
                                                        applysave();
                                                        this.flip = false;
                                                        type = 0;
                                                        int rotateAngle2 = this.test_check.getRotateAngle() + 90;
                                                        image_angle = rotateAngle2;
                                                        this.test_check.rotateImage(rotateAngle2);
                                                        Bitmap RotateBitmap2 = Constant.RotateBitmap(main_bitmap, image_angle);
                                                        this.test_check.setImageBitmap(RotateBitmap2);
                                                        Constant.rotrate_bitmap = RotateBitmap2;
                                                        applysave();
                                                        return;
                                                    default:
                                                        switch (id) {
                                                            case R.id.scale_16to9 :
                                                                CropAllViewSet();
                                                                this.Scale16to9.setSelected(true);
                                                                this.cropImageView.setCropMode(CropImageView.CropMode.RATIO_16_9);
                                                                return;
                                                            case R.id.scale_1to1 :
                                                                CropAllViewSet();
                                                                this.Scale1to1.setSelected(true);
                                                                this.cropImageView.setCustomRatio(1, 1);
                                                                return;
                                                            case R.id.scale_2to3 :
                                                                CropAllViewSet();
                                                                this.Scale2to3.setSelected(true);
                                                                this.cropImageView.setCustomRatio(2, 3);
                                                                return;
                                                            case R.id.scale_3to2 :
                                                                CropAllViewSet();
                                                                this.Scale3to2.setSelected(true);
                                                                this.cropImageView.setCustomRatio(3, 2);
                                                                return;
                                                            case R.id.scale_3to4 :
                                                                CropAllViewSet();
                                                                this.Scale3to4.setSelected(true);
                                                                this.cropImageView.setCropMode(CropImageView.CropMode.RATIO_3_4);
                                                                return;
                                                            case R.id.scale_3to5 :
                                                                CropAllViewSet();
                                                                this.Scale3to5.setSelected(true);
                                                                this.cropImageView.setCustomRatio(3, 5);
                                                                return;
                                                            case R.id.scale_4to3 :
                                                                CropAllViewSet();
                                                                this.Scale4to3.setSelected(true);
                                                                this.cropImageView.setCropMode(CropImageView.CropMode.RATIO_4_3);
                                                                return;
                                                            case R.id.scale_5to3 :
                                                                CropAllViewSet();
                                                                this.Scale5to3.setSelected(true);
                                                                this.cropImageView.setCustomRatio(5, 3);
                                                                return;
                                                            case R.id.scale_5to7 :
                                                                CropAllViewSet();
                                                                this.Scale5to7.setSelected(true);
                                                                this.cropImageView.setCustomRatio(5, 7);
                                                                return;
                                                            case R.id.scale_7to5 :
                                                                CropAllViewSet();
                                                                this.Scale7to5.setSelected(true);
                                                                this.cropImageView.setCustomRatio(7, 5);
                                                                return;
                                                            case R.id.scale_9to16 :
                                                                CropAllViewSet();
                                                                this.Scale9to16.setSelected(true);
                                                                this.cropImageView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                                                                return;
                                                            default:
                                                                switch (id) {
                                                                    case R.id.stickerPack1 :
                                                                        StickerAllPackSet();
                                                                        this.stickerPack1.setSelected(true);
                                                                        this.stickerPackListener.SelectStickerPack(0);
                                                                        return;
                                                                    case R.id.stickerPack10 :
                                                                        StickerAllPackSet();
                                                                        this.stickerPack10.setSelected(true);
                                                                        this.stickerPackListener.SelectStickerPack(9);
                                                                        return;
                                                                    default:
                                                                        return;
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public void mainBottomItemClick() {
        this.rl_blur.setVisibility(View.GONE);
        this.bottom_option.setVisibility(View.VISIBLE);
        this.color_effect_main_layout.setVisibility(View.GONE);
        this.ll_color_effect_layout.setVisibility(View.GONE);
        removeImageViewControll();
        this.txt_stkr_rel.removeAllViews();
        this.sticker_stkr_rel.removeAllViews();
        this.ll_blur_layout.setVisibility(View.GONE);
        this.rl_blur.setVisibility(View.GONE);
        this.pattern_img.setImageBitmap(null);
        this.trans_img.setImageBitmap(null);
        this.test_check.setVisibility(View.VISIBLE);
        this.test_check.setImageBitmap(main_bitmap);
        this.ll_sticker_layout.setVisibility(View.GONE);
        this.lltext_option.setVisibility(View.GONE);
        this.llSticker_edit_option.setVisibility(View.GONE);
        this.llFace_option.setVisibility(View.GONE);
        this.ll_crop_option.setVisibility(View.GONE);
        this.cropImageView.setVisibility(View.GONE);
        this.ll_Art_Filter_layout.setVisibility(View.GONE);
        this.ll_rotation_layout.setVisibility(View.GONE);
        this.llFilter_option.setVisibility(View.GONE);
        this.roatate_imageview.setVisibility(View.GONE);
    }

    public void AllBottomMainOptionVisi() {
        this.ll_crop.setSelected(false);
        this.img_crop.setVisibility(View.VISIBLE);
        this.r_crop.setVisibility(View.GONE);
        this.ll_sticker.setSelected(false);
        this.img_sticker.setVisibility(View.VISIBLE);
        this.r_sticker.setVisibility(View.GONE);
        this.ll_art_filter.setSelected(false);
        this.img_art.setVisibility(View.VISIBLE);
        this.r_art.setVisibility(View.GONE);
        this.ll_text_add.setSelected(false);
        this.img_text.setVisibility(View.VISIBLE);
        this.r_text.setVisibility(View.GONE);
        this.ll_filter.setSelected(false);
        this.img_filter.setVisibility(View.VISIBLE);
        this.r_filter.setVisibility(View.GONE);
        this.ll_rotation.setSelected(false);
        this.img_rotation.setVisibility(View.VISIBLE);
        this.r_rotation.setVisibility(View.GONE);
        this.ll_paint_color.setSelected(false);
        this.img_paint.setVisibility(View.VISIBLE);
        this.r_paint.setVisibility(View.GONE);
        this.ll_blur_effect.setSelected(false);
        this.img_blur.setVisibility(View.VISIBLE);
        this.r_blur.setVisibility(View.GONE);
    }

    public void StickerItemViewAllView() {
        this.txt_3D.setSelected(false);
        this.stickerFilter.setSelected(false);
        this.transparent.setSelected(false);
    }

    public void StickerAllPackSet() {
        this.stickerPack1.setSelected(false);
        this.stickerPack2.setSelected(false);
        this.stickerPack3.setSelected(false);
        this.stickerPack4.setSelected(false);
        this.stickerPack6.setSelected(false);
        this.stickerPack7.setSelected(false);
        this.stickerPack8.setSelected(false);
        this.stickerPack9.setSelected(false);
        this.stickerPack10.setSelected(false);
        this.stickerPack11.setSelected(false);
        this.stickerPack12.setSelected(false);
        this.stickerPack13.setSelected(false);
        this.stickerPack14.setSelected(false);
        this.stickerPack15.setSelected(false);
    }

    public void CropAllViewSet() {
        this.freeScale.setSelected(false);
        this.Scale1to1.setSelected(false);
        this.Scale4to3.setSelected(false);
        this.Scale3to4.setSelected(false);
        this.Scale5to7.setSelected(false);
        this.Scale7to5.setSelected(false);
        this.Scale2to3.setSelected(false);
        this.Scale3to2.setSelected(false);
        this.Scale3to5.setSelected(false);
        this.Scale5to3.setSelected(false);
        this.Scale9to16.setSelected(false);
        this.Scale16to9.setSelected(false);
    }

    private void beautyAdapterSetup() {
        bindDataToAdapter();
    }

    private void bindDataToAdapter() {
        new Handler().post(new AnonymousClass15());
    }

    
    
    
    public class AnonymousClass15 implements Runnable {
        AnonymousClass15() {
        }

        @Override 
        public void run() {
            final Bitmap bitmap = NeonDevilEditActivity.main_bitmap;
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() - 1, bitmap.getHeight() - 1, false);
            ThumbnailsManager.clearThumbs();
            for (Filter filter : FilterPack.getFilterPack(NeonDevilEditActivity.this.getApplicationContext())) {
                ThumbnailItem thumbnailItem = new ThumbnailItem();
                thumbnailItem.image = createScaledBitmap;
                thumbnailItem.filter = filter;
                ThumbnailsManager.addThumb(thumbnailItem);
            }
            final List<ThumbnailItem> processThumbs = ThumbnailsManager.processThumbs(NeonDevilEditActivity.this);
            NeonDevilEditActivity.this.rvFilter.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { 
                @Override 
                public boolean onPreDraw() {
                    if (NeonDevilEditActivity.this.rvFilter.getMeasuredHeight() > 0) {
                        NeonDevilEditActivity.this.rvFilter.getViewTreeObserver().removeOnPreDrawListener(this);
                        NeonDevilEditActivity.this.heightFilter2 = NeonDevilEditActivity.this.rvFilter.getMeasuredHeight();
                        NeonDevilEditActivity.this.adapter = new ThumbnailsAdapter(processThumbs, new ThumbnailCallback() { 
                            @Override 
                            public void onThumbnailClick(Filter filter2, int i) {
                                NeonDevilEditActivity.this.ivSelected.setVisibility(View.GONE);
                                File storeToDirectory = new StorageUtills(NeonDevilEditActivity.this).storeToDirectory(NeonDevilEditActivity.this.getResources().getString(R.string.image), NeonDevilEditActivity.this.getResources().getString(R.string.bgname));
                                NeonDevilEditActivity.this.mainPath = storeToDirectory.getAbsolutePath();
                                NeonDevilEditActivity.this.main_bitmap_filter = filter2.processFilter(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() - 10, bitmap.getHeight() - 10, false));
                                NeonDevilEditActivity.this.test_check.setImageBitmap(NeonDevilEditActivity.this.main_bitmap_filter);
                                NeonDevilEditActivity.this.test_check.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
                            }
                        }, NeonDevilEditActivity.this.heightFilter2);
                        NeonDevilEditActivity.this.rvFilter.setAdapter(NeonDevilEditActivity.this.adapter);
                        NeonDevilEditActivity.this.adapter.notifyDataSetChanged();
                        return true;
                    }
                    return true;
                }
            });
        }
    }

    private void overPatternAdapterSetup() {
        this.rvFilter.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { 
            @Override 
            public boolean onPreDraw() {
                if (NeonDevilEditActivity.this.rvFilter.getMeasuredHeight() > 0) {
                    NeonDevilEditActivity.this.rvFilter.getViewTreeObserver().removeOnPreDrawListener(this);
                    NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                    neonDevilEditActivity.heightFilter2 = neonDevilEditActivity.rvFilter.getMeasuredHeight();
                    final ArrayList arrayList = new ArrayList();
                    arrayList.addAll(Arrays.asList(NeonDevilEditActivity.this.getNames("pattern")));
                    NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                    neonDevilEditActivity2.patternAdpater = new PatternAdpater(neonDevilEditActivity2, arrayList, new PatternAdpater.PatterenSelect() { 
                        @Override 
                        public void patternclick(int i) {
                            InputStream inputStream;
                            NeonDevilEditActivity.this.ivSelected.setVisibility(View.GONE);
                            NeonDevilEditActivity.this.ll_filter_opactity.setVisibility(View.VISIBLE);
                            try {
                                inputStream = NeonDevilEditActivity.this.mcontext.getAssets().open((String) arrayList.get(i));
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(NeonDevilEditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                inputStream = null;
                            }
                            NeonDevilEditActivity.this.pattern_bmp = BitmapFactory.decodeStream(inputStream);
                            NeonDevilEditActivity.this.pattern_bmp = Bitmap.createScaledBitmap(NeonDevilEditActivity.this.pattern_bmp, (int) NeonDevilEditActivity.this.test_check.getBitmapRect().width(), (int) NeonDevilEditActivity.this.test_check.getBitmapRect().height(), false);
                            NeonDevilEditActivity.this.pattern_img.setImageBitmap(NeonDevilEditActivity.this.pattern_bmp);
                            NeonDevilEditActivity.this.pattern_img.setAlpha(NeonDevilEditActivity.this.pattern_opacity_seek.getProgress() + 30);
                        }
                    }, NeonDevilEditActivity.this.heightFilter2);
                    NeonDevilEditActivity.this.rvFilter.setAdapter(NeonDevilEditActivity.this.patternAdpater);
                    return true;
                }
                return true;
            }
        });
    }

    private void overLayAdapterSetup() {
        this.rvFilter.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { 
            @Override 
            public boolean onPreDraw() {
                if (NeonDevilEditActivity.this.rvFilter.getMeasuredHeight() > 0) {
                    NeonDevilEditActivity.this.rvFilter.getViewTreeObserver().removeOnPreDrawListener(this);
                    NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                    neonDevilEditActivity.heightFilter2 = neonDevilEditActivity.rvFilter.getMeasuredHeight();
                    final ArrayList arrayList = new ArrayList();
                    arrayList.addAll(Arrays.asList(NeonDevilEditActivity.this.getNames("overlay")));
                    NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                    neonDevilEditActivity2.effectAdapter = new EffectAdapter(neonDevilEditActivity2, arrayList, new EffectAdapter.EffectSelect() {
                        @Override 
                        public void effectClick(int i) {

                            Log.e("cccccc","fff="+i);

                            InputStream inputStream;
                            NeonDevilEditActivity.this.ivSelected.setVisibility(View.GONE);
                            NeonDevilEditActivity.this.ll_filter_opactity.setVisibility(View.VISIBLE);
                            try {
                                inputStream = NeonDevilEditActivity.this.mcontext.getAssets().open((String) arrayList.get(i));
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(NeonDevilEditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                inputStream = null;
                            }
                            NeonDevilEditActivity.this.oi = BitmapFactory.decodeStream(inputStream);
                            NeonDevilEditActivity.this.oi = Bitmap.createScaledBitmap(NeonDevilEditActivity.this.oi, (int) NeonDevilEditActivity.this.test_check.getBitmapRect().width(), (int) NeonDevilEditActivity.this.test_check.getBitmapRect().height(), false);
                            NeonDevilEditActivity.this.trans_img.setImageBitmap(NeonDevilEditActivity.this.oi);
                            NeonDevilEditActivity.this.trans_img.setAlpha(NeonDevilEditActivity.this.filter_opacity_seek.getProgress() + 30);
                        }
                    }, NeonDevilEditActivity.this.heightFilter2);
                    NeonDevilEditActivity.this.rvFilter.setAdapter(NeonDevilEditActivity.this.effectAdapter);
                    return true;
                }
                return true;
            }
        });
    }

    private void colorAdapterSetup() {
        this.colorList = getDefaultColors(this);
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(this, getResources().getColor(R.color.black), 0, this.colorList);
        this.colorPickerAdapter = colorPickerAdapter;
        colorPickerAdapter.setOnColorPickerClickListener(new ColorPickerAdapter.OnColorPickerClickListener() { 
            @Override 
            public void onColorPickerClickListener(int i, int i2, ColorPickerAdapter.ViewHolder viewHolder) {
                if (i != 0) {
                    NeonDevilEditActivity.this.updateColor(i2);
                    return;
                }
                NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                new AmbilWarnaDialog(neonDevilEditActivity, neonDevilEditActivity.getResources().getColor(R.color.blue_color_picker), new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override 
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                    }

                    @Override 
                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i3, boolean z) {
                        NeonDevilEditActivity.this.colorList.remove(0);
                        NeonDevilEditActivity.this.colorList.add(0, Integer.valueOf(i3));
                        NeonDevilEditActivity.this.updateColor(i3);
                        NeonDevilEditActivity.this.colorPickerAdapter.notifyDataSetChanged();
                    }
                }, NeonDevilEditActivity.this.colorList).show();
            }
        });
        this.rvText.setHasFixedSize(true);
        this.rvText.setLayoutManager(new GridLayoutManager((Context) this, 2, RecyclerView.HORIZONTAL, false));
        this.rvText.setAdapter(this.colorPickerAdapter);
    }

    
    public void updateColor(int i) {
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = this.txt_stkr_rel.getChildAt(i2);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextColor(i);
                    this.tColor = i;
                }
            }
            if (childAt instanceof ResizableStickerView) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                if (resizableStickerView.getBorderVisbilty()) {
                    resizableStickerView.setColorType("white");
                    resizableStickerView.setColor(i);
                }
            }
        }
    }

    public static ArrayList<Integer> getDefaultColors(Context context) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.blue_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.brown_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.green_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.orange_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.red_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.black)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.red_orange_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.sky_blue_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.violet_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.white)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.yellow_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.yellow_green_color_picker)));
        return arrayList;
    }

    private void setGravityText(String str) {
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.txt_stkr_rel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextGravity(str);
                    this.txtGravity = str;
                }
            }
        }
    }

    private void addedtextDialog() {
        TextEditorDialogFragment show = TextEditorDialogFragment.show(this, getSupportFragmentManager(), Layout.Alignment.ALIGN_CENTER, this.onBackPressed);
        show.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() { 
            @Override 
            public void onDone(String str, int i, Typeface typeface, Layout.Alignment alignment) {
                NeonDevilEditActivity.this.textAdd(str.trim());
                NeonDevilEditActivity.this.fl_style.setSelected(true);
                NeonDevilEditActivity.this.fl_color.setSelected(false);
                NeonDevilEditActivity.this.fl_alignment.setSelected(false);
                NeonDevilEditActivity.this.fontAdapterSetup();
                NeonDevilEditActivity.this.ll_alignment.setVisibility(View.GONE);
                NeonDevilEditActivity.this.rvText.setVisibility(View.VISIBLE);
            }
        });
        show.setOnCloseTextEditorListener(new TextEditorDialogFragment.TextEditorClose() { 
            @Override 
            public void onClose() {
                NeonDevilEditActivity.this.title_text.setText(NeonDevilEditActivity.this.getResources().getString(R.string.title_text));
                NeonDevilEditActivity.this.AllBottomMainOptionVisi();
                NeonDevilEditActivity.this.lltext_option.setVisibility(View.GONE);
            }
        });
    }

    public void textAdd(String str) {
        if (str.length() > 0) {
            TextInfo textInfo = new TextInfo();
            textInfo.setPOS_X(Constant.sticker_layout_width / 2);
            textInfo.setPOS_Y(Constant.sticker_layout_height / 2);
            textInfo.setWIDTH(dpToPx(this, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            textInfo.setHEIGHT(dpToPx(this, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
            textInfo.setTEXT(str.replace("\n", " "));
            textInfo.setFONT_NAME(this.fontName);
            textInfo.setTEXT_COLOR(this.tColor);
            textInfo.setTEXT_GRAVITY(this.txtGravity);
            if (this.editMode) {
                this.touchChange = false;
                RelativeLayout relativeLayout = this.txt_stkr_rel;
                textInfo.setXRotateProg(((AutofitTextRel) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).getXRotateProg());
                RelativeLayout relativeLayout2 = this.txt_stkr_rel;
                textInfo.setYRotateProg(((AutofitTextRel) relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1)).getYRotateProg());
                RelativeLayout relativeLayout3 = this.txt_stkr_rel;
                textInfo.setZRotateProg(((AutofitTextRel) relativeLayout3.getChildAt(relativeLayout3.getChildCount() - 1)).getZRotateProg());
                RelativeLayout relativeLayout4 = this.txt_stkr_rel;
                textInfo.setCurveRotateProg(((AutofitTextRel) relativeLayout4.getChildAt(relativeLayout4.getChildCount() - 1)).getCurveRotateProg());
                RelativeLayout relativeLayout5 = this.txt_stkr_rel;
                ((AutofitTextRel) relativeLayout5.getChildAt(relativeLayout5.getChildCount() - 1)).setTextInfo(textInfo, false);
                int childCount = this.txt_stkr_rel.getChildCount();
                if (childCount != 0) {
                    View childAt = this.txt_stkr_rel.getChildAt(childCount - 1);
                    if (childAt instanceof AutofitTextRel) {
                        AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                        autofitTextRel.setBorderVisibility(false);
                        if (!autofitTextRel.getBorderVisibility()) {
                            autofitTextRel.setBorderVisibility(true);
                        }
                    }
                } else {
                    Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show();
                }
                this.editMode = false;
            } else {
                this.touchChange = true;
                textInfo.setXRotateProg(45);
                textInfo.setYRotateProg(45);
                textInfo.setZRotateProg(Constant.ORIENTATION_180);
                textInfo.setCurveRotateProg(0);
                AutofitTextRel autofitTextRel2 = new AutofitTextRel(this);
                this.txt_stkr_rel.addView(autofitTextRel2);
                autofitTextRel2.setTextInfo(textInfo, false);
                autofitTextRel2.setId(View.generateViewId());
                autofitTextRel2.setMainLayoutWH(this.middle_ly.getWidth(), this.middle_ly.getHeight());
                autofitTextRel2.setOnTouchCallbackListener(this);
                autofitTextRel2.setBorderVisibility(false);
                int childCount2 = this.txt_stkr_rel.getChildCount();
                if (childCount2 != 0) {
                    View childAt2 = this.txt_stkr_rel.getChildAt(childCount2 - 1);
                    if (childAt2 instanceof AutofitTextRel) {
                        AutofitTextRel autofitTextRel3 = (AutofitTextRel) childAt2;
                        if (!autofitTextRel3.getBorderVisibility()) {
                            autofitTextRel3.setBorderVisibility(true);
                        }
                    }
                }
            }
            fontAdapterSetup();
            return;
        }
        Toast.makeText(this, "Please enter text.", Toast.LENGTH_SHORT).show();
    }

    
    public void fontAdapterSetup() {
        String[] stringArray = getResources().getStringArray(R.array.txtfont_array);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(stringArray));
        this.fontAdapter = new FontAdapter(this, new FontInterface() {
            @Override 
            public void fontclicked(String str, int i) {

                if (i == 0) {
                    NeonDevilEditActivity.this.setTextFonts(str);
                    return;
                }
                NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                neonDevilEditActivity.setTextFonts("fonts/" + str);
            }
        }, this.thisfinal_font_ttf, arrayList);
        this.rvText.setHasFixedSize(true);
        this.rvText.setLayoutManager(new GridLayoutManager((Context) this, 2, RecyclerView.HORIZONTAL, false));
        this.rvText.setAdapter(this.fontAdapter);
    }

    
    public void setTextFonts(String str) {
        this.fontName = str;
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.txt_stkr_rel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextFont(str);
                }
            }
        }
    }

    @Override 
    public void onEditText() {
        this.editMode = true;
        Text_selection_Dialog();
    }

    private void Text_selection_Dialog() {
        if (this.editMode) {
            RelativeLayout relativeLayout = this.txt_stkr_rel;
            TextInfo textInfo = ((AutofitTextRel) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).getTextInfo();
            textInfo.setPOS_X(textInfo.getPOS_X());
            textInfo.setPOS_Y(textInfo.getPOS_Y());
            textInfo.setWIDTH(textInfo.getWIDTH());
            textInfo.setHEIGHT(textInfo.getHEIGHT());
            textInfo.setTEXT(textInfo.getTEXT());
            textInfo.setFONT_NAME(textInfo.getFONT_NAME());
            textInfo.setTEXT_COLOR(textInfo.getTEXT_COLOR());
            textInfo.setTEXT_ALPHA(textInfo.getTEXT_ALPHA());
            textInfo.setSHADOW_COLOR(textInfo.getSHADOW_COLOR());
            textInfo.setSHADOW_PROG(textInfo.getSHADOW_PROG());
            textInfo.setBG_COLOR(textInfo.getBG_COLOR());
            textInfo.setBG_DRAWABLE(textInfo.getBG_DRAWABLE());
            textInfo.setBG_ALPHA(textInfo.getBG_ALPHA());
            textInfo.setROTATION(textInfo.getROTATION());
            textInfo.setTEXT_GRAVITY(textInfo.getTEXT_GRAVITY());
            TextEditorDialogFragment show = TextEditorDialogFragment.show(this.mcontext, getSupportFragmentManager(), textInfo.getTEXT(), getResources().getColor(R.color.white), null, Layout.Alignment.ALIGN_CENTER, this.onBackPressed);
            show.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() { 
                @Override 
                public void onDone(String str, int i, Typeface typeface, Layout.Alignment alignment) {
                    NeonDevilEditActivity.this.textAdd(str.trim());
                }
            });
            show.setOnCloseTextEditorListener(new TextEditorDialogFragment.TextEditorClose() { 
                @Override 
                public void onClose() {
                    NeonDevilEditActivity.this.lltext_option.setVisibility(View.GONE);
                }
            });
        }
    }

    private void filterStickerAdapterSetup() {
        this.rvFilterSticker.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { 
            @Override 
            public boolean onPreDraw() {
                if (NeonDevilEditActivity.this.rvFilterSticker.getMeasuredHeight() > 0) {
                    NeonDevilEditActivity.this.rvFilterSticker.getViewTreeObserver().removeOnPreDrawListener(this);
                    NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                    neonDevilEditActivity.rvHeight = neonDevilEditActivity.rvFilterSticker.getMeasuredHeight();
                    NeonDevilEditActivity.this.filterAdapterSetup();
                    return true;
                }
                return true;
            }
        });
    }

    
    public void filterAdapterSetup() {
        this.filterstickerList = getDefaultStickerColors(getApplicationContext());
        FilterStickerAdapter filterStickerAdapter = new FilterStickerAdapter(getApplicationContext(), getResources().getColor(R.color.white), 0, this.filterstickerList, this.rvHeight);
        this.filterStickerAdapter = filterStickerAdapter;
        filterStickerAdapter.setOnColorPickerClickListener(new FilterStickerAdapter.OnColorPickerClickListener() { 
            @Override 
            public void onColorPickerClickListener(int i, int i2, FilterStickerAdapter.ViewHolder viewHolder) {
                NeonDevilEditActivity.this.updateStickerColor(i2);
            }
        });
        this.rvFilterSticker.setHasFixedSize(true);
        this.rvFilterSticker.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, RecyclerView.HORIZONTAL, false));
        this.rvFilterSticker.setAdapter(this.filterStickerAdapter);
    }

    public static ArrayList<Integer> getDefaultStickerColors(Context context) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.blue_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.brown_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.green_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.orange_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.red_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.black)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.red_orange_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.sky_blue_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.violet_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.white)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.yellow_color_picker)));
        arrayList.add(Integer.valueOf(ContextCompat.getColor(context, R.color.yellow_green_color_picker)));
        return arrayList;
    }

    
    public void updateStickerColor(int i) {
        int childCount = this.sticker_stkr_rel.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = this.sticker_stkr_rel.getChildAt(i2);
            if (childAt instanceof ResizableStickerView) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                if (resizableStickerView.getBorderVisbilty()) {
                    resizableStickerView.setColorType("white");
                    resizableStickerView.setColor(ColorUtils.setAlphaComponent(i, 50));
                }
            }
        }
    }

    private void seekbarChangeListener() {
        this.pattern_opacity_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = i + 30;
                NeonDevilEditActivity.this.filter_opacity_text.setText(String.valueOf(i2));
                NeonDevilEditActivity.this.pattern_img.setImageBitmap(NeonDevilEditActivity.this.pattern_bmp);
                NeonDevilEditActivity.this.pattern_img.setAlpha(i2);
            }
        });
        this.filter_opacity_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = i + 30;
                NeonDevilEditActivity.this.filter_opacity_text.setText(String.valueOf(i2));
                NeonDevilEditActivity.this.trans_img.setImageBitmap(NeonDevilEditActivity.this.oi);
                NeonDevilEditActivity.this.trans_img.setAlpha(i2);
            }
        });
        this.art_filter_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = i / 10;
            }
        });
        this.x_3d_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                NeonDevilEditActivity.this.textViewX3D.setText(String.valueOf(i));
                if (i != 0) {
                    int childCount = NeonDevilEditActivity.this.sticker_stkr_rel.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = NeonDevilEditActivity.this.sticker_stkr_rel.getChildAt(i2);
                        if (childAt instanceof ResizableStickerView) {
                            ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                            if (resizableStickerView.getBorderVisbilty()) {
                                if (i < 42 || i > 48) {
                                    resizableStickerView.setStickerRotateProg(45 - i, 45 - NeonDevilEditActivity.this.y_3d_seek.getProgress(), 0, NeonDevilEditActivity.this.x_3d_seek.getProgress(), NeonDevilEditActivity.this.y_3d_seek.getProgress(), Constant.ORIENTATION_180);
                                } else {
                                    NeonDevilEditActivity.this.x_3d_seek.setProgress(45);
                                    resizableStickerView.setStickerRotateProg(0, 45 - NeonDevilEditActivity.this.y_3d_seek.getProgress(), 0, NeonDevilEditActivity.this.x_3d_seek.getProgress(), NeonDevilEditActivity.this.y_3d_seek.getProgress(), Constant.ORIENTATION_180);
                                }
                            }
                        }
                    }
                }
            }
        });
        this.y_3d_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                NeonDevilEditActivity.this.textViewY3D.setText(String.valueOf(i));
                if (i != 0) {
                    int childCount = NeonDevilEditActivity.this.sticker_stkr_rel.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = NeonDevilEditActivity.this.sticker_stkr_rel.getChildAt(i2);
                        if (childAt instanceof ResizableStickerView) {
                            ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                            if (resizableStickerView.getBorderVisbilty()) {
                                if (i < 42 || i > 48) {
                                    resizableStickerView.setStickerRotateProg(45 - NeonDevilEditActivity.this.x_3d_seek.getProgress(), 45 - i, 0, NeonDevilEditActivity.this.x_3d_seek.getProgress(), NeonDevilEditActivity.this.y_3d_seek.getProgress(), Constant.ORIENTATION_180);
                                } else {
                                    NeonDevilEditActivity.this.y_3d_seek.setProgress(45);
                                    resizableStickerView.setStickerRotateProg(45 - NeonDevilEditActivity.this.x_3d_seek.getProgress(), 0, 0, NeonDevilEditActivity.this.x_3d_seek.getProgress(), NeonDevilEditActivity.this.y_3d_seek.getProgress(), Constant.ORIENTATION_180);
                                }
                            }
                        }
                    }
                }
            }
        });
        this.transparent_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                NeonDevilEditActivity.this.transparentTxt.setText(String.valueOf(i));
                int childCount = NeonDevilEditActivity.this.sticker_stkr_rel.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = NeonDevilEditActivity.this.sticker_stkr_rel.getChildAt(i2);
                    if (childAt instanceof ResizableStickerView) {
                        ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                        if (resizableStickerView.getBorderVisbilty()) {
                            resizableStickerView.setAlphaProg(i);
                        }
                    }
                }
            }
        });
    }

    
    class ArtFilter extends AsyncTask<Void, Void, Void> {
        ArtFilter() {
        }

        @Override 
        protected void onPreExecute() {
            super.onPreExecute();
        }

        
        @Override 
        public Void doInBackground(Void... voidArr) {
            Bitmap resizedBitmap = Constant.getResizedBitmap(NeonDevilEditActivity.main_bitmap, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            for (int i = 0; i < NeonDevilEditActivity.this.artfilternameList.size(); i++) {
                NeonDevilEditActivity.this.rgbShift = 0.065f;
                NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                neonDevilEditActivity.onDynamicApplyOptionsChanged("r", neonDevilEditActivity.waveChoosen, NeonDevilEditActivity.this.rgbShift, NeonDevilEditActivity.this.intensity, NeonDevilEditActivity.this.thickness, i, resizedBitmap);
            }
            return null;
        }

        
        @Override 
        public void onPostExecute(Void r3) {
            super.onPostExecute(r3);
            NeonDevilEditActivity.this.artFilterLoad();
            NeonDevilEditActivity.this.art_seekbar.setVisibility(View.GONE);
            NeonDevilEditActivity.this.art_filter_seekbar.setVisibility(View.VISIBLE);
            NeonDevilEditActivity.this.filterType = "r";
            new GlitchFilters(NeonDevilEditActivity.this, true);
            NeonDevilEditActivity.this.rgbShift = 0.032f;
            NeonDevilEditActivity.this.applyChange();
        }
    }

    public void artFilterLoad() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        ArtFilterAdpater artFilterAdpater = new ArtFilterAdpater(this, this.artfilterbitmapList, this.artfilternameList, this.artfilternameListName);
        this.art_recyview.setLayoutManager(linearLayoutManager);
        this.art_recyview.setAdapter(artFilterAdpater);
        artFilterAdpater.setClickListener(this);
    }

    
    class StickersLoad extends AsyncTask<Void, Void, Void> {
        StickersLoad() {
        }

        @Override 
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("CHECKALL", "ASYN");
            NeonDevilEditActivity.this.rvEmoji.setVisibility(View.GONE);
            NeonDevilEditActivity.this.sticker_progress.setVisibility(View.VISIBLE);
        }

        
        @Override 
        public Void doInBackground(Void... voidArr) {
            NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
            neonDevilEditActivity.MainStickerList = neonDevilEditActivity.stickerlist;
            return null;
        }

        
        @Override 
        public void onPostExecute(Void r6) {
            super.onPostExecute( r6);
            NeonDevilEditActivity.this.rvEmoji.setVisibility(View.VISIBLE);
            NeonDevilEditActivity.this.sticker_progress.setVisibility(View.GONE);
            NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
            neonDevilEditActivity.stickerAdapter = new StickerAdapter(neonDevilEditActivity.MainStickerList, new SetStickerListener() {
                @Override 
                public void SelectSticker(String str, String str2) {
                    NeonDevilEditActivity.this.x_3d_seek.setProgress(45);
                    NeonDevilEditActivity.this.y_3d_seek.setProgress(45);
                    NeonDevilEditActivity.this.addSticker(str, "");
                }
            }, NeonDevilEditActivity.this.getApplicationContext());
            NeonDevilEditActivity.this.rvEmoji.setLayoutManager(new GridLayoutManager(NeonDevilEditActivity.this.getApplicationContext(), 2, RecyclerView.HORIZONTAL, false));
            NeonDevilEditActivity.this.rvEmoji.setAdapter(NeonDevilEditActivity.this.stickerAdapter);
        }
    }

    public void addlistFromAssest(ArrayList<String> arrayList, String str) {
        arrayList.clear();
        arrayList.addAll(Arrays.asList(getNames(str)));
        Collections.reverse(arrayList);
    }

    
    public String[] getNames(String str) {
        String[] strArr;
        try {
            strArr = getResources().getAssets().list(str);
        } catch (IOException e) {
            e.printStackTrace();
            strArr = null;
        }
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = str + "/" + strArr[i];
        }
        return strArr;
    }

    
    public void addSticker(String str, String str2) {
        this.transparent_seek.setProgress(100);
        ComponentInfo componentInfo = new ComponentInfo();
        componentInfo.setPOS_X(Constant.sticker_layout_width / 2);
        componentInfo.setPOS_X(Constant.sticker_layout_width / 2);
        componentInfo.setWIDTH(dpToPx(getApplicationContext(), ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        componentInfo.setHEIGHT(dpToPx(getApplicationContext(), ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION));
        componentInfo.setROTATION(0.0f);
        componentInfo.setRES_ID(str);
        componentInfo.setBITMAP(null);
        componentInfo.setCOLORTYPE("colored");
        componentInfo.setTYPE("STICKER");
        componentInfo.setSTC_OPACITY(100);
        componentInfo.setSTC_COLOR(0);
        componentInfo.setSTKR_PATH(str2);
        componentInfo.setSTC_HUE(1);
        componentInfo.setFIELD_TWO("0,0");
        componentInfo.setXRotateProg(45);
        componentInfo.setYRotateProg(45);
        componentInfo.setZRotateProg(Constant.ORIENTATION_180);
        componentInfo.setScaleProg(10);
        ResizableStickerView resizableStickerView = new ResizableStickerView(getApplicationContext());
        resizableStickerView.setOnTouchCallbackListener(this);
        resizableStickerView.optimizeScreen(screenWidth, screenHeight);
        resizableStickerView.setComponentInfo(componentInfo);
        resizableStickerView.setId(View.generateViewId());
        resizableStickerView.setMainLayoutWH(this.middle_ly.getWidth(), this.middle_ly.getHeight());
        this.sticker_stkr_rel.addView(resizableStickerView);
        resizableStickerView.setBorderVisibility(true);
        removeImageViewControll();
        new Handler().postDelayed(new Runnable() { 
            @Override 
            public void run() {
            }
        }, 2000L);
    }

    public void removeImageViewControll() {
        RelativeLayout relativeLayout = this.txt_stkr_rel;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.txt_stkr_rel.getChildAt(i);
                if (childAt instanceof ResizableStickerView) {
                    ((ResizableStickerView) childAt).setBorderVisibility(false);
                }
                if (childAt instanceof AutofitTextRel) {
                    ((AutofitTextRel) childAt).setBorderVisibility(false);
                }
            }
        }
        RelativeLayout relativeLayout2 = this.sticker_stkr_rel;
        if (relativeLayout2 != null) {
            int childCount2 = relativeLayout2.getChildCount();
            for (int i2 = 0; i2 < childCount2; i2++) {
                View childAt2 = this.sticker_stkr_rel.getChildAt(i2);
                if (childAt2 instanceof ResizableStickerView) {
                    ((ResizableStickerView) childAt2).setBorderVisibility(false);
                }
                if (childAt2 instanceof AutofitTextRel) {
                    ((AutofitTextRel) childAt2).setBorderVisibility(false);
                }
            }
        }
    }

    public static int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override 
    public byte[] getResBytes(Context context, String str) {
        try {
            Bitmap bitmap = ((BitmapDrawable) Drawable.createFromStream(getResources().getAssets().open(str), null)).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception unused) {
            return null;
        }
    }

    @Override 
    public void onDelete() {
        this.llFace_option.setVisibility(View.VISIBLE);
        this.llSticker_edit_option.setVisibility(View.GONE);
        if (this.lltext_option.getVisibility() == View.VISIBLE) {
            AllBottomMainOptionVisi();
            this.lltext_option.setVisibility(View.GONE);
        }
    }

    @Override 
    public void onRotateDown(View view) {
        touchDown(view, "viewboder");
    }

    private void touchDown(View view, String str) {
        this.focusedView = view;
        if (str.equals("hideboder")) {
            removeImageViewControll();
        }
        if (view instanceof ResizableStickerView) {
            applysave();
            this.llFace_option.setVisibility(View.GONE);
            this.llSticker_edit_option.setVisibility(View.VISIBLE);
            this.ll_sticker_layout.setVisibility(View.VISIBLE);
            this.lltext_option.setVisibility(View.GONE);
            this.ll_rotation_layout.setVisibility(View.GONE);
            StickerItemViewAllView();
            this.txt_3D.setSelected(true);
            ResizableStickerView resizableStickerView = (ResizableStickerView) view;
            this.transparent_seek.setProgress(resizableStickerView.getAlphaProg());
            this.x_3d_seek.setProgress(resizableStickerView.getXRotateProg());
            this.y_3d_seek.setProgress(resizableStickerView.getYRotateProg());
        } else if (view instanceof AutofitTextRel) {
            this.lltext_option.setVisibility(View.VISIBLE);
            this.llFace_option.setVisibility(View.GONE);
            this.llSticker_edit_option.setVisibility(View.GONE);
            this.ll_Art_Filter_layout.setVisibility(View.GONE);
            this.ll_sticker_layout.setVisibility(View.GONE);
            this.ll_rotation_layout.setVisibility(View.GONE);
        }
    }

    @Override 
    public void onRotateMove(View view) {
        touchMove(view);
    }

    @Override 
    public void onRotateUp(View view) {
        touchUp(view);
    }

    private void touchUp(View view) {
        if (this.focusedCopy != this.focusedView) {
            applysave();
            this.llSticker_edit_option.setVisibility(View.VISIBLE);
        }
        if ((view instanceof ResizableStickerView) && this.llSticker_edit_option.getVisibility() == View.GONE) {
            applysave();
            this.llSticker_edit_option.setVisibility(View.VISIBLE);
            this.llFace_option.setVisibility(View.GONE);
        }
        this.selectedView = view;
    }

    @Override 
    public void onScaleDown(View view) {
        touchDown(view, "viewboder");
    }

    @Override 
    public void onScaleMove(View view) {
        touchMove(view);
    }

    @Override 
    public void onScaleUp(View view) {
        touchUp(view);
    }

    @Override 
    public void onTouchDown(View view) {
        touchDown(view, "hideboder");
    }

    @Override 
    public void onTouchMove(View view) {
        touchMove(view);
    }

    @Override 
    public void onTouchUp(View view) {
        touchUp(view);
    }

    
    public class FilterAndAdjustmentTask extends AsyncTask<Void, Void, Void> {
        boolean INBG = false;
        ProgressDialog progressDialog;

        FilterAndAdjustmentTask() {
        }

        @Override 
        public Void doInBackground(Void... voidArr) {
            this.INBG = true;
            NeonDevilEditActivity.this.myApplication.isFasynRunning = true;
            if (NeonDevilEditActivity.myView.filterBitmap == null) {
                NeonDevilEditActivity.myView.filterBitmap = NeonDevilEditActivity.myView.graySourceBtm.copy(Bitmap.Config.ARGB_8888, true);
            } else {
                new Canvas(NeonDevilEditActivity.myView.filterBitmap).drawBitmap(NeonDevilEditActivity.myView.graySourceBtm, 0.0f, 0.0f, new Paint());
            }
            NeonDevilEditActivity.this.setFilterColor(NeonDevilEditActivity.myView.filterBitmap, false);
            return null;
        }

        @Override 
        public void onPostExecute(Void r3) {
            this.INBG = false;
            NeonDevilEditActivity.this.myApplication.isFasynRunning = false;
            NeonDevilEditActivity.this.inFilterAndAdjustment = false;
            NeonDevilEditActivity.this.f_ProgressDialog.dismiss();
            try {
                NeonDevilEditActivity.myView.postInvalidate();
            } catch (Exception unused) {
            }
        }

        @Override 
        public void onPreExecute() {
            NeonDevilEditActivity.this.myApplication.isFasynRunning = true;
            NeonDevilEditActivity.this.inFilterAndAdjustment = true;
            NeonDevilEditActivity.this.f_ProgressDialog.show();
        }
    }

    public void setFilterColor(Bitmap bitmap, boolean z) {
        filterGray(bitmap);
    }

    private void filterSepia(Bitmap bitmap) {
        new Canvas(bitmap).drawBitmap(bitmap, 0.0f, 0.0f, this.sepiaPaint);
    }

    private void filterGray(Bitmap bitmap) {
        new Canvas(bitmap).drawBitmap(bitmap, 0.0f, 0.0f, this.grayPaint);
    }

    public void setProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(this.alertContext);
        this.progressDialog = progressDialog;
        progressDialog.setMessage(getString(R.string.saving_session_data));
        this.progressDialog.setCancelable(false);
    }

    @Override 
    public void onPause() {
        super.onPause();
    }

    public void execQueue() {
        FilterAndAdjustmentTask filterAndAdjustmentTask = this.f3370ft;
        if (filterAndAdjustmentTask == null || filterAndAdjustmentTask.getStatus() != AsyncTask.Status.RUNNING) {
            FilterAndAdjustmentTask filterAndAdjustmentTask2 = new FilterAndAdjustmentTask();
            this.f3370ft = filterAndAdjustmentTask2;
            try {
                filterAndAdjustmentTask2.execute(new Void[0]);
            } catch (Exception unused) {
            }
        }
    }

    @Override 
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        if (id == R.id.paint_button) {
            this.ll_erase_selection.setVisibility(View.VISIBLE);
            SizePickerView sizePickerView = this.mySizePickerView;
            if (sizePickerView != null) {
                sizePickerView.setVisibility(View.GONE);
            }
            checkBoxButtonStateChanger(2);
        } else if (id == R.id.erase_button) {
            this.ll_erase_selection.setVisibility(View.GONE);
            SizePickerView sizePickerView2 = this.mySizePickerView;
            if (sizePickerView2 != null) {
                sizePickerView2.setVisibility(View.GONE);
            }
            checkBoxButtonStateChanger(3);
        } else if (id != R.id.button_color_paint) {
            if (id == R.id.button_color_pallete) {
                this.ll_erase_selection.setVisibility(View.VISIBLE);
                SizePickerView sizePickerView3 = this.mySizePickerView;
                if (sizePickerView3 != null) {
                    sizePickerView3.setVisibility(View.GONE);
                }
                new AmbilWarnaDialog(this, this.selectedColor, new AmbilWarnaDialog.OnAmbilWarnaListener() { 
                    @Override 
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                    }

                    @Override 
                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i, boolean z) {
                        NeonDevilEditActivity.this.selectedColor = i;
                        NeonDevilEditActivity.this.checkBoxButtonStateChanger(4);
                        if (z) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(i);
                            Log.e("Recent c = ", sb.toString());
                            if (NeonDevilEditActivity.this.colorHistoryStack == null) {
                                NeonDevilEditActivity.this.colorHistoryStack = new ArrayList();
                            }
                            if (NeonDevilEditActivity.this.colorHistoryStack.size() > 0) {
                                for (int i2 = 0; i2 < NeonDevilEditActivity.this.colorHistoryStack.size(); i2++) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(NeonDevilEditActivity.this.colorHistoryStack.get(i2));
                                    Log.e("color = ", sb2.toString());
                                    if (i == NeonDevilEditActivity.this.colorHistoryStack.get(i2).intValue()) {
                                        NeonDevilEditActivity.this.colorHistoryStack.remove(i2);
                                    }
                                }
                            }
                            NeonDevilEditActivity.this.colorHistoryStack.add(Integer.valueOf(NeonDevilEditActivity.this.selectedColor));
                        }
                    }
                }, this.colorHistoryStack).show();
            }
        } else {
            this.ll_erase_selection.setVisibility(View.VISIBLE);
            SizePickerView sizePickerView4 = this.mySizePickerView;
            if (sizePickerView4 != null) {
                sizePickerView4.setVisibility(View.GONE);
            }
            checkBoxButtonStateChanger(4);
            int i = this.horizontalListItemNewCount;
            if (i < 22) {
                this.horizontalListItemNewCount = i + 1;
            }
        }
    }

    public int getFooterWidthOnNormalLandscapeOrientation() {
        int width = this.ll_color_effect_layout.getWidth();
        if (width > screenHeight / 3.0f) {
            width = this.ll_color_effect_layout.getHeight();
        }
        if (width > screenHeight / 3.0f) {
            return 90;
        }
        return width;
    }

    public void setPaintColor(int i, Paint paint) {
        float red = Color.red(i) / 255.0f;
        float green = Color.green(i) / 255.0f;
        float blue = Color.blue(i) / 255.0f;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{red * 0.299f, red * 0.587f, red * 0.114f, 0.0f, 0.0f, green * 0.299f, green * 0.587f, green * 0.114f, 0.0f, 0.0f, 0.299f * blue, 0.587f * blue, blue * 0.114f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    
    public void selectedBrushTyheChanged(int i) {
        if (i == 0) {
            this.mPaint.setAlpha(255);
            this.mPaint.setMaskFilter(null);
            this.PAINT_STATE = 0;
        } else if (i == 1) {
            this.mPaint.setAlpha(255);
            this.mPaint.setMaskFilter(this.mBlur);
            this.PAINT_STATE = 2;
        } else if (i == 2) {
            this.mPaint.setAlpha(255);
            this.mPaint.setMaskFilter(this.mEmboss);
            this.PAINT_STATE = 3;
        } else if (i != 3) {
        } else {
            this.mPaint.setAlpha(68);
            this.mPaint.setMaskFilter(null);
            this.PAINT_STATE = 4;
        }
    }

    public boolean isPackageColorMe() {
        return getPackageName().toLowerCase().contains("colorme");
    }

    public void checkBoxButtonStateChanger(int i) {
        this.isInClearState = false;
        this.mPaint.setXfermode(null);
        this.mPaint.setColorFilter(null);
        if (i == 1) {
            this.panZoom = true;
            AllColorEffectBootomView();
            this.ButtonState = 1;
        } else if (i != 2) {
            if (i == 3) {
                this.panZoom = false;
                this.isInClearState = true;
                this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                int i2 = this.PAINT_STATE;
                if (i2 != 1) {
                    this.SAVED_STATE = i2;
                }
                this.PAINT_STATE = 1;
                AllColorEffectBootomView();
                this.eraseButton.setSelected(true);
                this.img_erase.setVisibility(View.GONE);
                this.r_erase_color.setVisibility(View.VISIBLE);
                this.ButtonState = 3;
                return;
            } else if (i != 4) {
                return;
            } else {
                if (this.PAINT_STATE == 1) {
                    this.PAINT_STATE = this.SAVED_STATE;
                }
                this.panZoom = false;
                setPaintColor(this.selectedColor, this.mPaint);
                AllColorEffectBootomView();
                this.colorButton.setSelected(true);
                this.paintButton.setSelected(false);
                this.eraseButton.setSelected(false);
                this.img_brush.setVisibility(View.GONE);
                this.r_brush.setVisibility(View.VISIBLE);
                this.ButtonState = 4;
                return;
            }
        }
        this.panZoom = false;
        if (this.PAINT_STATE == 1) {
            this.PAINT_STATE = this.SAVED_STATE;
        }
        AllColorEffectBootomView();
        this.paintButton.setSelected(true);
        this.img_original.setVisibility(View.GONE);
        this.r_original_color.setVisibility(View.VISIBLE);
        this.ButtonState = 2;
    }

    public void AllColorEffectBootomView() {
        this.eraseButton.setSelected(false);
        this.img_erase.setVisibility(View.VISIBLE);
        this.r_erase_color.setVisibility(View.GONE);
        this.colorButton.setSelected(false);
        this.img_brush.setVisibility(View.VISIBLE);
        this.r_brush.setVisibility(View.GONE);
        this.paintButton.setSelected(false);
        this.img_original.setVisibility(View.VISIBLE);
        this.r_original_color.setVisibility(View.GONE);
    }

    
    public class MyView extends View {
        ArrayList<Integer> actionItem;
        RectF bounds;
        public Matrix canvasMatrix;
        public Bitmap colorSourceBtm;
        private float f3371mX;
        private float f3372mY;
        Bitmap filterBitmap;
        public Bitmap graySourceBtm;
        boolean isPointerDown;
        boolean isYPositionSet;
        PointF leftTopCorner;
        public Paint mBitmapPaint;
        public Canvas mCanvas;
        public Path mPath;
        BitmapShader mShader;
        Paint maskPaint;
        PorterDuffXfermode maskPorterDuffXfermode;
        PointF mid;
        PointF midPoint;
        int mode;
        float oldDist;
        PointF oldPointerOne;
        PointF oldPointerTwo;
        Paint paintTrans;
        ArrayList<MyPointF> pathItem;
        PointF pointerOne;
        PointF pointerTwo;
        PointF rigtBottomCorner;
        public Matrix savedMatrix;
        public int sourceHeight;
        public int sourceWidth;
        PointF start;
        Paint surroundPaint;
        float[] values;

        private Bitmap decodeFile(String str) {
            return BitmapFactory.decodeFile(str, new BitmapFactory.Options());
        }

        public MyView(Context context) {
            super(context);
            this.canvasMatrix = new Matrix();
            this.isPointerDown = false;
            this.isYPositionSet = false;
            this.leftTopCorner = new PointF();
            this.mBitmapPaint = new Paint();
            this.maskPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
            this.mid = new PointF();
            this.midPoint = new PointF();
            this.mode = 0;
            this.oldDist = 1.0f;
            this.oldPointerOne = new PointF();
            this.oldPointerTwo = new PointF();
            this.pointerOne = new PointF();
            this.pointerTwo = new PointF();
            this.rigtBottomCorner = new PointF();
            this.savedMatrix = new Matrix();
            this.start = new PointF();
            this.surroundPaint = new Paint();
            this.values = new float[9];
            if (NeonDevilEditActivity.this.data == null) {
                loadBitmapFiles();
            } else {
                ArrayList[] arrayListArr = (ArrayList[]) NeonDevilEditActivity.this.data;
                this.colorSourceBtm = (Bitmap) arrayListArr[2].get(0);
                this.graySourceBtm = (Bitmap) arrayListArr[2].get(1);
                this.filterBitmap = (Bitmap) arrayListArr[2].get(2);
            }
            NeonDevilEditActivity.this.bitmapList = new ArrayList<>();
            NeonDevilEditActivity.this.bitmapList.add(this.colorSourceBtm);
            NeonDevilEditActivity.this.bitmapList.add(this.graySourceBtm);
            NeonDevilEditActivity.this.bitmapList.add(this.filterBitmap);
            this.sourceHeight = this.graySourceBtm.getHeight();
            this.sourceWidth = this.graySourceBtm.getWidth();
            this.mCanvas = new Canvas(this.colorSourceBtm);
            this.mPath = new Path();
            Paint paint = new Paint(4);
            this.mBitmapPaint = paint;
            paint.setAntiAlias(true);
            this.mBitmapPaint.setDither(true);
            this.mShader = new BitmapShader(this.graySourceBtm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            new Matrix().preRotate(90.0f);
            Paint paint2 = new Paint();
            this.maskPaint = paint2;
            paint2.setAlpha(168);
            NeonDevilEditActivity.this.mPaint.setShader(this.mShader);
            NeonDevilEditActivity.this.paintSource = new Paint(NeonDevilEditActivity.this.mPaint);
            NeonDevilEditActivity.this.surroundColor = getResources().getColor(R.color.shaderactivity_surround_color);
            this.surroundPaint.setColor(NeonDevilEditActivity.this.surroundColor);
            Paint paint3 = new Paint();
            this.paintTrans = paint3;
            paint3.setColor(-16711681);
            this.paintTrans.setAlpha(0);
            this.bounds = new RectF();
        }

        private void loadBitmapFiles() {
            Bitmap bitmap = Constant.color_bitmap;
            this.graySourceBtm = bitmap;
            this.sourceHeight = bitmap.getHeight();
            int width = this.graySourceBtm.getWidth();
            this.sourceWidth = width;
            this.colorSourceBtm = Bitmap.createBitmap(width, this.sourceHeight, Bitmap.Config.ARGB_8888);
            this.filterBitmap = this.graySourceBtm.copy(Bitmap.Config.ARGB_8888, true);
        }

        @Override 
        public int getId() {
            return super.getId();
        }

        public void setInitialYPosition() {
            float[] fArr = new float[9];
            this.canvasMatrix.getValues(fArr);
            if (getResources().getConfiguration().orientation == 1 || NeonDevilEditActivity.this.isScreenLayoutXlarge) {
                if (NeonDevilEditActivity.this.toolbar.getHeight() > 0) {
                    NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                    neonDevilEditActivity.initialYPosition = neonDevilEditActivity.toolbar.getMeasuredHeight();
                    NeonDevilEditActivity.this.initialYPosition = 0;
                    this.isYPositionSet = true;
                }
                int measuredHeight = NeonDevilEditActivity.this.ll_color_effect_layout.getMeasuredHeight() > 0 ? NeonDevilEditActivity.this.ll_color_effect_layout.getMeasuredHeight() : 0;
                fArr[5] = NeonDevilEditActivity.this.initialYPosition;
                NeonDevilEditActivity.this.topHeightOffset += NeonDevilEditActivity.this.initialYPosition;
                NeonDevilEditActivity.this.bottomHeightOffset -= measuredHeight;
            } else {
                if (NeonDevilEditActivity.this.toolbar.getHeight() > 0) {
                    NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                    neonDevilEditActivity2.initialYPosition = neonDevilEditActivity2.toolbar.getMeasuredWidth();
                    if (!NeonDevilEditActivity.this.isScreenLayoutXlarge) {
                        NeonDevilEditActivity.this.rightWidthOffset += NeonDevilEditActivity.this.initialYPosition;
                    }
                    this.isYPositionSet = true;
                }
                fArr[2] = NeonDevilEditActivity.this.initialYPosition;
            }
            this.canvasMatrix.setValues(fArr);
            float minZoom = setMinZoom();
            this.canvasMatrix.postScale(minZoom, minZoom, NeonDevilEditActivity.screenWidth, NeonDevilEditActivity.screenHeight);
            this.canvasMatrix.getValues(fArr);
        }

        @Override 
        public void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
        }

        @Override 
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
        }

        public void setStrokeWidth() {
            float f = NeonDevilEditActivity.this.DEFAULT_STROKE_WIDTH;
            if (this.values[0] > 0.0f) {
                f = NeonDevilEditActivity.this.strokeWidth / this.values[0];
            }
            NeonDevilEditActivity.this.mPaint.setStrokeWidth(f);
        }

        @Override 
        public void onDraw(Canvas canvas) {
            int i = sourceWidth;
            if (!this.isYPositionSet) {
                setInitialYPosition();
            }
            checkScreenBounds2();
            this.canvasMatrix.getValues(this.values);
            canvas.setMatrix(this.canvasMatrix);
            canvas.drawPaint(this.surroundPaint);
            canvas.drawBitmap(this.filterBitmap, 0.0f, 0.0f, this.mBitmapPaint);
            int saveLayer = NeonDevilEditActivity.this.isMasked.booleanValue() ? canvas.saveLayer(0.0f, 0.0f, this.sourceWidth, this.sourceHeight, null, Canvas.ALL_SAVE_FLAG) : 0;
            int saveLayer2 = NeonDevilEditActivity.this.isInClearState ? canvas.saveLayer(0.0f, 0.0f, this.sourceWidth, this.sourceHeight, null, Canvas.ALL_SAVE_FLAG) : 0;
            canvas.drawBitmap(this.colorSourceBtm, 0.0f, 0.0f, this.mBitmapPaint);
            canvas.drawPath(this.mPath, NeonDevilEditActivity.this.mPaint);
            if (NeonDevilEditActivity.this.isInClearState) {
                canvas.restoreToCount(saveLayer2);
            }
            if (NeonDevilEditActivity.this.isMasked.booleanValue()) {
                this.maskPaint.setXfermode(null);
                this.maskPaint.setXfermode(this.maskPorterDuffXfermode);
                canvas.drawPaint(this.maskPaint);
                canvas.restoreToCount(saveLayer);
            }
            canvas.setMatrix(this.canvasMatrix);
            float f = -this.sourceWidth;
            int i2 = this.sourceHeight;
            canvas.drawRect(f, -i2, 0.0f, i2 * 2, this.surroundPaint);
            int i3 = this.sourceWidth;
            int i4 = this.sourceHeight;
            canvas.drawRect(i3, -i4, i3 * 2, i4 * 2, this.surroundPaint);
            canvas.drawRect(0.0f, -this.sourceHeight, this.sourceWidth, 0.0f, this.surroundPaint);
            canvas.drawRect(0.0f, this.sourceHeight, this.sourceWidth, i * 2, this.surroundPaint);
        }

        public void checkScreenBounds2() {
            int i = this.sourceWidth;
            int i2 = this.sourceHeight;
            this.canvasMatrix.getValues(this.values);
            if (this.values[0] < NeonDevilEditActivity.this.MIN_ZOOM) {
                this.canvasMatrix.postScale(NeonDevilEditActivity.this.MIN_ZOOM / this.values[0], NeonDevilEditActivity.this.MIN_ZOOM / this.values[0], this.mid.x, this.mid.y);
                this.canvasMatrix.getValues(this.values);
            }
            if (this.values[0] > NeonDevilEditActivity.this.MAX_ZOOM) {
                this.canvasMatrix.postScale(NeonDevilEditActivity.this.MAX_ZOOM / this.values[0], NeonDevilEditActivity.this.MAX_ZOOM / this.values[0], NeonDevilEditActivity.screenWidth / 2.0f, NeonDevilEditActivity.screenHeight / 2.0f);
                this.canvasMatrix.getValues(this.values);
            }
            this.leftTopCorner.x = this.values[2];
            this.leftTopCorner.y = this.values[5];
            PointF pointF = this.rigtBottomCorner;
            float[] fArr = this.values;
            float f = i;
            pointF.x = fArr[2] + (fArr[0] * f);
            PointF pointF2 = this.rigtBottomCorner;
            float[] fArr2 = this.values;
            float f2 = i2;
            pointF2.y = fArr2[5] + (fArr2[0] * f2);
            this.midPoint.x = (this.leftTopCorner.x + this.rigtBottomCorner.x) / 2.0f;
            this.midPoint.y = (this.leftTopCorner.y + this.rigtBottomCorner.y) / 2.0f;
            if (this.values[0] * f2 > NeonDevilEditActivity.this.bottomHeightOffset - NeonDevilEditActivity.this.topHeightOffset) {
                if (this.leftTopCorner.y > NeonDevilEditActivity.this.topHeightOffset) {
                    this.values[5] = NeonDevilEditActivity.this.topHeightOffset;
                }
                if (this.rigtBottomCorner.y < NeonDevilEditActivity.this.bottomHeightOffset) {
                    this.values[5] = NeonDevilEditActivity.this.bottomHeightOffset - (f2 * this.values[0]);
                }
            } else if (this.midPoint.y != NeonDevilEditActivity.screenHeight / 2.0f) {
                if (NeonDevilEditActivity.this.MIN_ZOOM * f2 >= NeonDevilEditActivity.this.bottomHeightOffset - NeonDevilEditActivity.this.topHeightOffset) {
                    this.values[5] = ((NeonDevilEditActivity.this.bottomHeightOffset + NeonDevilEditActivity.this.topHeightOffset) - (f2 * this.values[0])) / 2.0f;
                } else {
                    this.values[5] = (NeonDevilEditActivity.screenHeight - (f2 * this.values[0])) / 2.0f;
                }
            }
            if (this.values[0] * f > NeonDevilEditActivity.this.leftWidthOffset - NeonDevilEditActivity.this.rightWidthOffset) {
                if (this.leftTopCorner.x > NeonDevilEditActivity.this.rightWidthOffset) {
                    this.values[2] = NeonDevilEditActivity.this.rightWidthOffset;
                }
                if (this.rigtBottomCorner.x < NeonDevilEditActivity.screenWidth) {
                    this.values[2] = NeonDevilEditActivity.this.leftWidthOffset - (f * this.values[0]);
                }
            } else if (this.midPoint.x != NeonDevilEditActivity.screenWidth / 2.0f) {
                this.values[2] = (NeonDevilEditActivity.screenWidth - (f * this.values[0])) / 2.0f;
            }
            setStrokeWidth();
            this.canvasMatrix.setValues(this.values);
        }

        public float setMinZoom() {
            float min;
            if (getResources().getConfiguration().orientation == 1) {
                min = Math.min(NeonDevilEditActivity.screenWidth / this.sourceWidth, (NeonDevilEditActivity.this.bottomHeightOffset - NeonDevilEditActivity.this.topHeightOffset) / this.sourceHeight);
            } else {
                min = Math.min((NeonDevilEditActivity.this.leftWidthOffset - NeonDevilEditActivity.this.rightWidthOffset) / this.sourceWidth, (NeonDevilEditActivity.this.bottomHeightOffset - NeonDevilEditActivity.this.topHeightOffset) / this.sourceHeight);
            }
            NeonDevilEditActivity.this.MIN_ZOOM = min;
            return min;
        }

        private void touch_start(float f, float f2) {
            this.pathItem = new ArrayList<>();
            this.actionItem = new ArrayList<>();
            float[] fArr = this.values;
            float f3 = (f - fArr[2]) / fArr[0];
            float f4 = (f2 - fArr[5]) / fArr[0];
            this.mPath.reset();
            this.mPath.moveTo(f3, f4);
            this.f3371mX = f3;
            this.f3372mY = f4;
            this.actionItem.add(0);
            this.pathItem.add(new MyPointF(f3, f4));
        }

        private void touch_move(float f, float f2) {
            float[] fArr = this.values;
            float f3 = (f - fArr[2]) / fArr[0];
            float f4 = (f2 - fArr[5]) / fArr[0];
            float abs = Math.abs(f3 - this.f3371mX);
            float abs2 = Math.abs(f4 - this.f3372mY);
            if (abs >= 0.0f || abs2 >= 0.0f) {
                Path path = this.mPath;
                float f5 = this.f3371mX;
                float f6 = this.f3372mY;
                path.quadTo(f5, f6, (f5 + f3) / 2.0f, (f6 + f4) / 2.0f);
                this.f3371mX = f3;
                this.f3372mY = f4;
                this.pathItem.add(new MyPointF(this.f3371mX, this.f3372mY));
                this.pathItem.add(new MyPointF((this.f3371mX + f3) / 2.0f, (this.f3372mY + f4) / 2.0f));
                this.actionItem.add(1);
            }
        }

        private void touch_up() {
            this.mPath.lineTo(this.f3371mX, this.f3372mY);
            this.mCanvas.drawPath(this.mPath, NeonDevilEditActivity.this.mPaint);
            Paint paint = new Paint();
            paint.set(NeonDevilEditActivity.this.mPaint);
            NeonDevilEditActivity.this.paintList.add(paint);
            this.mPath.reset();
        }

        @Override 
        public boolean onTouchEvent(MotionEvent motionEvent) {
            NeonDevilEditActivity.this.main_layout.setVisibility(View.GONE);
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                if (NeonDevilEditActivity.this.panZoom) {
                    this.savedMatrix.set(this.canvasMatrix);
                    this.start.set(motionEvent.getX(), motionEvent.getY());
                    this.mode = 1;
                } else {
                    touch_start(x, y);
                }
                invalidate();
                return true;
            } else if (action == 1) {
                if (NeonDevilEditActivity.this.panZoom) {
                    this.mode = 0;
                } else if (!this.isPointerDown) {
                    touch_up();
                } else {
                    this.isPointerDown = false;
                }
                invalidate();
                return true;
            } else if (action != 2) {
                if (action != 5) {
                    return true;
                }
                if (NeonDevilEditActivity.this.panZoom) {
                    try {
                        this.oldDist = MyMotionEvent.spacing(motionEvent);
                    } catch (Exception unused) {
                    }
                    if (this.oldDist <= 10.0f) {
                        return true;
                    }
                    this.savedMatrix.set(this.canvasMatrix);
                    MyMotionEvent.midPoint(this.mid, motionEvent);
                    this.mode = 2;
                    return true;
                }
                try {
                    this.oldDist = MyMotionEvent.spacing(motionEvent);
                    this.start.set(motionEvent.getX(), motionEvent.getY());
                    this.oldPointerOne.set(motionEvent.getX(0), motionEvent.getY(0));
                    this.oldPointerTwo.set(motionEvent.getX(1), motionEvent.getY(1));
                } catch (Exception unused2) {
                }
                this.isPointerDown = true;
                if (this.oldDist <= 10.0f) {
                    return true;
                }
                this.savedMatrix.set(this.canvasMatrix);
                MyMotionEvent.midPoint(this.mid, motionEvent);
                return true;
            } else {
                if (NeonDevilEditActivity.this.panZoom) {
                    int i = this.mode;
                    if (i == 1) {
                        this.canvasMatrix.set(this.savedMatrix);
                        this.canvasMatrix.postTranslate(motionEvent.getX() - this.start.x, motionEvent.getY() - this.start.y);
                    } else if (i == 2) {
                        float spacing = MyMotionEvent.spacing(motionEvent);
                        if (spacing > 10.0f) {
                            this.canvasMatrix.set(this.savedMatrix);
                            float f = spacing / this.oldDist;
                            this.canvasMatrix.postScale(f, f, this.mid.x, this.mid.y);
                        }
                    }
                } else if (this.isPointerDown) {
                    try {
                        this.pointerOne.x = motionEvent.getX(0);
                        this.pointerOne.y = motionEvent.getY(0);
                        this.pointerTwo.x = motionEvent.getX(1);
                        this.pointerTwo.y = motionEvent.getY(1);
                    } catch (Exception unused3) {
                    }
                    float f2 = this.pointerOne.x - this.oldPointerOne.x;
                    float f3 = this.pointerOne.y - this.oldPointerOne.y;
                    float sqrt = (float) Math.sqrt((f2 * f2) + (f3 * f3));
                    PointF pointF = new PointF(f2 / sqrt, f3 / sqrt);
                    float f4 = this.pointerTwo.x - this.oldPointerTwo.x;
                    float f5 = this.pointerTwo.y - this.oldPointerTwo.y;
                    float sqrt2 = (float) Math.sqrt((f4 * f4) + (f5 * f5));
                    PointF pointF2 = new PointF(f4 / sqrt2, f5 / sqrt2);
                    float degrees = (float) Math.toDegrees(Math.acos((pointF.x * pointF2.x) + (pointF.y * pointF2.y)));
                    float spacing2 = MyMotionEvent.spacing(motionEvent);
                    if (spacing2 <= 10.0f || degrees <= 90.0f) {
                        if (degrees <= 90.0f) {
                            this.canvasMatrix.set(this.savedMatrix);
                            this.canvasMatrix.postTranslate(motionEvent.getX() - this.start.x, motionEvent.getY() - this.start.y);
                        }
                    } else if (spacing2 > 10.0f) {
                        this.canvasMatrix.set(this.savedMatrix);
                        float f6 = spacing2 / this.oldDist;
                        this.canvasMatrix.postScale(f6, f6, this.mid.x, this.mid.y);
                    }
                } else {
                    touch_move(x, y);
                }
                invalidate();
                return true;
            }
        }
    }

    public float getSize() {
        return this.mySizePickerView.getSize();
    }

    public int getSelectedBrushType() {
        return this.selectedBrushType;
    }

    public float getSeekBarValue() {
        return this.brush_size_seekbar.getProgress();
    }

    public void setRadioButtonsCheckState(int i) {
        int i2 = 0;
        while (true) {
            RadioButton[] radioButtonArr = this.radioButtonList;
            if (i2 >= radioButtonArr.length) {
                return;
            }
            if (i2 == i) {
                radioButtonArr[i2].setChecked(true);
            } else {
                radioButtonArr[i2].setChecked(false);
            }
            i2++;
        }
    }

    
    public class SizePickerView extends View {
        float density;
        private Paint mPaint;
        private float mSize;
        int measuredHeight;
        int measuredHeightScale;
        int measuredWidth;
        int measuredWidthScale;
        float radius;

        SizePickerView(Context context, float f) {
            super(context);
            this.mSize = 20.0f;
            this.measuredHeightScale = 70;
            this.measuredWidthScale = 150;
            this.radius = 20.0f;
        }

        public void updateSize(float f) {
            this.mSize = f;
        }

        public float getSize() {
            return this.mSize;
        }

        @Override 
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(InputDeviceCompat.SOURCE_ANY);
            this.mPaint.setColor(-18161);
            float f = this.density;
            if (f > 0.0f) {
                this.radius = ((f * 0.7f) * this.mSize) / 2.0f;
            } else {
                this.radius = this.mSize / 2.0f;
            }
            canvas.drawCircle(this.measuredWidth / 2, this.measuredHeight / 2, this.radius, this.mPaint);
        }

        @Override 
        public void onMeasure(int i, int i2) {
            float f = getResources().getDisplayMetrics().density;
            this.density = f;
            int i3 = this.measuredHeightScale;
            this.measuredHeight = i3;
            int i4 = this.measuredWidthScale;
            this.measuredWidth = i4;
            if (f > 0.0f) {
                this.measuredHeight = (int) (i3 * f);
                this.measuredWidth = (int) (i4 * f);
            }
            setMeasuredDimension(this.measuredWidth, this.measuredHeight);
        }
    }

    public static void setImage(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                Bitmap resizeImageForImageView = resizeImageForImageView(bitmap);
                originalbmp = resizeImageForImageView;
                selectedphoto = resizeImageForImageView;
            } catch (Exception unused) {
            }
        }
    }

    public static Bitmap resizeImageForImageView(Bitmap bitmap) {
        int i;
        int i2 = 720;
        if (bitmap.getHeight() > 720 || bitmap.getWidth() > 720) {
            if (bitmap.getWidth() > bitmap.getHeight()) {
                i = (bitmap.getHeight() * 720) / bitmap.getWidth();
            } else {
                i2 = (bitmap.getWidth() * 720) / bitmap.getHeight();
                i = 720;
            }
            return Bitmap.createScaledBitmap(bitmap, i2, i, true);
        }
        return bitmap;
    }

    private void loadImage() throws Exception {
        setImage(this.OriginalBitmap);
    }

    
    class FocalZoom extends AsyncTask<Void, Void, Void> {
        FocalZoom() {
        }

        @Override 
        protected void onPreExecute() {
            NeonDevilEditActivity.this.Motion_blur_Button.setEnabled(false);
            NeonDevilEditActivity.this.brushImageView.setVisibility(View.GONE);
            NeonDevilEditActivity.this.f_ProgressDialog.show();
            NeonDevilEditActivity.this.apply.setVisibility(View.GONE);
            NeonDevilEditActivity.this.save_image.setVisibility(View.GONE);
            NeonDevilEditActivity.seekBar_cartoon.setVisibility(View.GONE);
            NeonDevilEditActivity.this.focul_zoom_seekbar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        
        @Override 
        public Void doInBackground(Void... voidArr) {
            NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
            neonDevilEditActivity.gPUImage = new GPUImage(neonDevilEditActivity);
            NeonDevilEditActivity.this.gPUImage.setImage(NeonDevilEditActivity.this.OriginalBitmap);
            GPUImageZoomBlurFilter gPUImageZoomBlurFilter = new GPUImageZoomBlurFilter();
            gPUImageZoomBlurFilter.setBlurSize(NeonDevilEditActivity.this.focul_zoom_seekbar.getProgress() / 100.0f);
            NeonDevilEditActivity.this.gPUImage.setFilter(gPUImageZoomBlurFilter);
            NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
            neonDevilEditActivity2.GPuBitmap = neonDevilEditActivity2.gPUImage.getBitmapWithFilterApplied();
            return null;
        }

        
        @Override 
        public void onPostExecute(Void r6) {
            Log.e("testt___", "onPostExecute: ");
            NeonDevilEditActivity.this.f_ProgressDialog.dismiss();
            NeonDevilEditActivity.this.applysave();
            NeonDevilEditActivity.this.ll_blur_effect.setSelected(true);
            NeonDevilEditActivity.this.img_blur.setVisibility(View.GONE);
            NeonDevilEditActivity.this.r_blur.setVisibility(View.VISIBLE);
            NeonDevilEditActivity.this.ll_blur_layout.setVisibility(View.VISIBLE);
            NeonDevilEditActivity.this.rl_blur.setVisibility(View.VISIBLE);
            NeonDevilEditActivity.this.Motion_blur_Button.setEnabled(true);
            try {
                NeonDevilEditActivity.this.rlImageViewContainer.setVisibility(View.VISIBLE);
                NeonDevilEditActivity.this.brushImageView.setVisibility(View.GONE);
                NeonDevilEditActivity.this.drawingPath = new Path();
                Display defaultDisplay = NeonDevilEditActivity.this.getWindowManager().getDefaultDisplay();
                NeonDevilEditActivity.this.mainViewSize = new Point();
                defaultDisplay.getSize(NeonDevilEditActivity.this.mainViewSize);
                NeonDevilEditActivity.this.rlImageViewContainer.getLayoutParams().height = NeonDevilEditActivity.this.mainViewSize.y;
                NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                neonDevilEditActivity.imageViewWidth = neonDevilEditActivity.mainViewSize.x;
                NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                neonDevilEditActivity2.imageViewHeight = neonDevilEditActivity2.rlImageViewContainer.getLayoutParams().height;
                NeonDevilEditActivity.this.touchImageView.setOnTouchListener(new OnTouchListner());
                NeonDevilEditActivity.selectedphoto = NeonDevilEditActivity.originalbmp;
                NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this;
                neonDevilEditActivity3.originalBitmap = neonDevilEditActivity3.GPuBitmap;
                NeonDevilEditActivity.this.iv_backgroundimg.setImageBitmap(NeonDevilEditActivity.selectedphoto);
                NeonDevilEditActivity.this.setBitMap();
                NeonDevilEditActivity neonDevilEditActivity4 = NeonDevilEditActivity.this;
                neonDevilEditActivity4.updateBrush(neonDevilEditActivity4.mainViewSize.x / 2, NeonDevilEditActivity.this.mainViewSize.y / 2);
                NeonDevilEditActivity.this.iserase = false;
                NeonDevilEditActivity.selectedphoto = NeonDevilEditActivity.originalbmp;
            } catch (Exception unused) {
            }
            NeonDevilEditActivity.this.focul_zoom.setSelected(true);
            NeonDevilEditActivity.this.erase_btn.setSelected(false);
            NeonDevilEditActivity.this.Motion_blur_Button.setSelected(false);
            super.onPostExecute( r6);
        }
    }

    public static Bitmap doRadialBlur(Bitmap bitmap, int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas(createBitmap);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        mcanvas.drawBitmap(bitmap, matrix, paint);
        paint.setAlpha(71);
        for (int i4 = 0; i4 < i3; i4++) {
            matrix.setTranslate(i4 * i, i4 * i2);
            mcanvas.drawBitmap(bitmap, matrix, paint);
        }
        return createBitmap;
    }

    public void setBitMap() {
        this.canvasMaster = null;
        Bitmap resizeBitmapByCanvas = resizeBitmapByCanvas();
        this.resizedBitmap = resizeBitmapByCanvas;
        Bitmap copy = resizeBitmapByCanvas.copy(Bitmap.Config.ARGB_8888, true);
        this.lastEditedBitmap = copy;
        this.bitmapMaster = Bitmap.createBitmap(copy.getWidth(), this.lastEditedBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.bitmapMaster);
        this.canvasMaster = canvas;
        canvas.drawBitmap(this.lastEditedBitmap, 0.0f, 0.0f, (Paint) null);
        this.touchImageView.setImageBitmap(this.bitmapMaster);
        resetPathArrays();
        this.touchImageView.setPan(false);
        this.brushImageView.invalidate();
    }

    public void updateBrush(float f, float f2) {
        this.brushImageView.offset = this.offset;
        this.brushImageView.centerx = f;
        this.brushImageView.centery = f2;
        this.brushImageView.width = this.brushSize / 2.0f;
        this.brushImageView.invalidate();
    }

    
    
    public class OnTouchListner implements View.OnTouchListener {
        OnTouchListner() {
        }

        @Override 
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (NeonDevilEditActivity.this.iserase) {
                int action = motionEvent.getAction();
                if (motionEvent.getPointerCount() != 1 && !NeonDevilEditActivity.this.isMultipleTouchErasing) {
                    if (NeonDevilEditActivity.this.initialDrawingCount > 0) {
                        NeonDevilEditActivity.this.UpdateCanvas();
                        NeonDevilEditActivity.this.drawingPath.reset();
                        NeonDevilEditActivity.this.initialDrawingCount = 0;
                    }
                    NeonDevilEditActivity.this.touchImageView.onTouchEvent(motionEvent);
                    NeonDevilEditActivity.this.MODE = 2;
                } else if (action == 0) {
                    NeonDevilEditActivity.this.isTouchOnBitmap = false;
                    NeonDevilEditActivity.this.touchImageView.onTouchEvent(motionEvent);
                    NeonDevilEditActivity.this.MODE = 1;
                    NeonDevilEditActivity.this.initialDrawingCount = 0;
                    NeonDevilEditActivity.this.isMultipleTouchErasing = false;
                    NeonDevilEditActivity.this.moveTopoint(motionEvent.getX(), motionEvent.getY());
                    NeonDevilEditActivity.this.updateBrush(motionEvent.getX(), motionEvent.getY());
                } else if (action == 2) {
                    if (NeonDevilEditActivity.this.MODE == 1) {
                        NeonDevilEditActivity.this.currentx = motionEvent.getX();
                        NeonDevilEditActivity.this.currenty = motionEvent.getY();
                        NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
                        neonDevilEditActivity.updateBrush(neonDevilEditActivity.currentx, NeonDevilEditActivity.this.currenty);
                        NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
                        neonDevilEditActivity2.lineTopoint(neonDevilEditActivity2.bitmapMaster, NeonDevilEditActivity.this.currentx, NeonDevilEditActivity.this.currenty);
                        NeonDevilEditActivity.this.drawOnTouchMove();
                    }
                } else if (action == 1 || action == 6) {
                    if (NeonDevilEditActivity.this.MODE == 1 && NeonDevilEditActivity.this.isTouchOnBitmap) {
                        NeonDevilEditActivity.this.addDrawingPathToArrayList();
                    }
                    NeonDevilEditActivity.this.isMultipleTouchErasing = false;
                    NeonDevilEditActivity.this.initialDrawingCount = 0;
                    NeonDevilEditActivity.this.MODE = 0;
                }
                if (action == 1 || action == 6) {
                    NeonDevilEditActivity.this.MODE = 0;
                }
            }
            return true;
        }
    }

    public void addDrawingPathToArrayList() {
        if (this.paths.size() >= this.undoLimit) {
            UpdateLastEiditedBitmapForUndoLimit();
            this.paths.remove(0);
            this.brushSizes.remove(0);
        }
        this.brushSizes.add(Integer.valueOf(this.updatedBrushSize));
        this.paths.add(this.drawingPath);
        this.drawingPath = new Path();
    }

    public void UpdateLastEiditedBitmapForUndoLimit() {
        Canvas canvas = new Canvas(this.lastEditedBitmap);
        for (int i = 0; i < 1; i++) {
            int intValue = this.brushSizes.get(i).intValue();
            Paint paint = new Paint();
            paint.setColor(0);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            paint.setStrokeWidth(intValue);
            canvas.drawPath(this.paths.get(i), paint);
        }
    }

    public Bitmap resizeBitmapByCanvas() {
        float f;
        float f2;
        float width = this.originalBitmap.getWidth();
        float height = this.originalBitmap.getHeight();
        if (width > height) {
            int i = this.imageViewWidth;
            f = i;
            f2 = (i * height) / width;
        } else {
            int i2 = this.imageViewHeight;
            f = (i2 * width) / height;
            f2 = i2;
        }
        if (f > width || f2 > height) {
            return this.originalBitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap((int) f, (int) f2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f3 = f / width;
        Matrix matrix = new Matrix();
        matrix.postTranslate(0.0f, (f2 - (height * f3)) / 2.0f);
        matrix.preScale(f3, f3);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(this.originalBitmap, matrix, paint);
        return createBitmap;
    }

    public void moveTopoint(float f, float f2) {
        float imageViewZoom = getImageViewZoom();
        float f3 = f2 - this.offset;
        PointF imageViewTranslation = getImageViewTranslation();
        double d = imageViewZoom;
        this.drawingPath.moveTo((int) ((f - imageViewTranslation.x) / d), (int) ((f3 - imageViewTranslation.y) / d));
        this.updatedBrushSize = (int) (this.brushSize / imageViewZoom);
    }

    public void lineTopoint(Bitmap bitmap, float f, float f2) {
        int i = this.initialDrawingCount;
        int i2 = this.initialDrawingCountLimit;
        if (i < i2) {
            int i3 = i + 1;
            this.initialDrawingCount = i3;
            if (i3 == i2) {
                this.isMultipleTouchErasing = true;
            }
        }
        float imageViewZoom = getImageViewZoom();
        float f3 = f2 - this.offset;
        PointF imageViewTranslation = getImageViewTranslation();
        double d = imageViewZoom;
        int i4 = (int) ((f - imageViewTranslation.x) / d);
        int i5 = (int) ((f3 - imageViewTranslation.y) / d);
        if (!this.isTouchOnBitmap && i4 > 0 && i4 < bitmap.getWidth() && i5 > 0 && i5 < bitmap.getHeight()) {
            this.isTouchOnBitmap = true;
        }
        this.drawingPath.lineTo(i4, i5);
    }

    public float getImageViewZoom() {
        return this.touchImageView.getCurrentZoom();
    }

    public PointF getImageViewTranslation() {
        return this.touchImageView.getTransForm();
    }

    public void drawOnTouchMove() {
        Paint paint = new Paint();
        paint.setStrokeWidth(this.updatedBrushSize);
        paint.setColor(0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.canvasMaster.drawPath(this.drawingPath, paint);
        this.touchImageView.invalidate();
    }

    public void UpdateCanvas() {
        this.canvasMaster.drawColor(0, PorterDuff.Mode.CLEAR);
        this.canvasMaster.drawBitmap(this.lastEditedBitmap, 0.0f, 0.0f, (Paint) null);
        for (int i = 0; i < this.paths.size(); i++) {
            int intValue = this.brushSizes.get(i).intValue();
            Paint paint = new Paint();
            paint.setColor(0);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            paint.setStrokeWidth(intValue);
            this.canvasMaster.drawPath(this.paths.get(i), paint);
        }
        this.touchImageView.invalidate();
    }

    public void resetPathArrays() {
        this.paths.clear();
        this.brushSizes.clear();
    }

    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap createBitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return createBitmap;
    }

    
    class BlurEffect extends AsyncTask<Void, Void, Void> {
        BlurEffect() {
        }

        @Override 
        protected void onPreExecute() {
            super.onPreExecute();
        }

        
        @Override 
        public Void doInBackground(Void... voidArr) {
            NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
            neonDevilEditActivity.gPUImage = new GPUImage(neonDevilEditActivity);
            NeonDevilEditActivity.this.gPUImage.setImage(NeonDevilEditActivity.this.OriginalBitmap);
            GPUImageZoomBlurFilter gPUImageZoomBlurFilter = new GPUImageZoomBlurFilter();
            gPUImageZoomBlurFilter.setBlurSize(NeonDevilEditActivity.this.focul_zoom_seekbar.getProgress() / 100.0f);
            NeonDevilEditActivity.this.gPUImage.setFilter(gPUImageZoomBlurFilter);
            NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
            neonDevilEditActivity2.selectbi = neonDevilEditActivity2.gPUImage.getBitmapWithFilterApplied();
            return null;
        }

        
        @Override 
        public void onPostExecute(Void r3) {
            super.onPostExecute(r3);
            NeonDevilEditActivity.this.drawingPath = new Path();
            Display defaultDisplay = NeonDevilEditActivity.this.getWindowManager().getDefaultDisplay();
            NeonDevilEditActivity.this.mainViewSize = new Point();
            defaultDisplay.getSize(NeonDevilEditActivity.this.mainViewSize);
            NeonDevilEditActivity.this.rlImageViewContainer.getLayoutParams().height = NeonDevilEditActivity.this.mainViewSize.y;
            NeonDevilEditActivity neonDevilEditActivity = NeonDevilEditActivity.this;
            neonDevilEditActivity.imageViewWidth = neonDevilEditActivity.mainViewSize.x;
            NeonDevilEditActivity neonDevilEditActivity2 = NeonDevilEditActivity.this;
            neonDevilEditActivity2.imageViewHeight = neonDevilEditActivity2.rlImageViewContainer.getLayoutParams().height;
            NeonDevilEditActivity.this.touchImageView.setOnTouchListener(new OnTouchListner());
            NeonDevilEditActivity neonDevilEditActivity3 = NeonDevilEditActivity.this;
            neonDevilEditActivity3.originalBitmap = neonDevilEditActivity3.selectbi;
            NeonDevilEditActivity.this.iv_backgroundimg.setImageBitmap(NeonDevilEditActivity.selectedphoto);
            NeonDevilEditActivity.this.setBitMap();
            NeonDevilEditActivity.this.progressDialogeffect.cancel();
            NeonDevilEditActivity.this.is_focal_zoom = false;
        }
    }
}
