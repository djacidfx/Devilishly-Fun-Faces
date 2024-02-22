package com.demo.neondevilsface.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.demo.neondevilsface.R;
import com.demo.neondevilsface.interfac.IOnBackPressed;


public class TextEditorDialogFragment extends DialogFragment {
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String TAG = "TextEditorDialogFragment";
    private static Layout.Alignment alignment;
    private static IOnBackPressed iOnBackPressed1;
    private static String inputtext1;
    private static Typeface thisfinal_font_ttf;
    private static int thisfont_color;
    int SelectPos;
    private ImageView btn_text_done;
    private Dialog dialog;
    private ImageView lin_close;
    private EditText mAddTextEditText;
    private int mColorCode;
    public int[] mColorSeed;
    private InputMethodManager mInputMethodManager;
    private TextEditor mTextEditor;
    private LinearLayout root;
    TextEditorClose textEditorClose;

    
    public interface TextEditor {
        void onDone(String str, int i, Typeface typeface, Layout.Alignment alignment);
    }

    
    public interface TextEditorClose {
        void onClose();
    }

    public static TextEditorDialogFragment show(Context context, FragmentManager fragmentManager, String str, int i, Typeface typeface, Layout.Alignment alignment2, IOnBackPressed iOnBackPressed) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INPUT_TEXT, str);
        bundle.putInt(EXTRA_COLOR_CODE, i);
        inputtext1 = str;
        thisfinal_font_ttf = typeface;
        thisfont_color = i;
        alignment = alignment2;
        TextEditorDialogFragment textEditorDialogFragment = new TextEditorDialogFragment();
        textEditorDialogFragment.setArguments(bundle);
        textEditorDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
        textEditorDialogFragment.show(fragmentManager, TAG);
        iOnBackPressed1 = iOnBackPressed;
        return textEditorDialogFragment;
    }

    public static TextEditorDialogFragment show(Context context, FragmentManager fragmentManager, Layout.Alignment alignment2, IOnBackPressed iOnBackPressed) {
        return show(context, fragmentManager, "", ContextCompat.getColor(context, R.color.white), null, alignment2, iOnBackPressed);
    }

    @Override 
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        this.dialog = dialog;
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { 
                @Override 
                public void onDismiss(DialogInterface dialogInterface) {
                    TextEditorDialogFragment.iOnBackPressed1.onBackPressed();
                    TextEditorDialogFragment.this.textEditorClose.onClose();
                    TextEditorDialogFragment.this.dismiss();
                }
            });
        }
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.add_text_dialog, viewGroup, false);
    }

    @Override 
    public void onViewCreated(final View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mAddTextEditText = (EditText) view.findViewById(R.id.add_text_edit_text);
        this.mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.btn_text_done = (ImageView) view.findViewById(R.id.add_text_done_tv);
        this.root = (LinearLayout) view.findViewById(R.id.root);
        this.lin_close = (ImageView) view.findViewById(R.id.close_btn);
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(new Rect());
        this.lin_close.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view2) {
                TextEditorDialogFragment.this.mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                TextEditorDialogFragment.this.textEditorClose.onClose();
                TextEditorDialogFragment.this.dismiss();
            }
        });
        this.mAddTextEditText.setText(getArguments().getString(EXTRA_INPUT_TEXT));
        int i = getArguments().getInt(EXTRA_COLOR_CODE);
        this.mColorCode = i;
        this.mAddTextEditText.setTextColor(i);
        this.mColorCode = thisfont_color;
        Typeface typeface = thisfinal_font_ttf;
        if (typeface != null) {
            this.mAddTextEditText.setTypeface(typeface);
        }
        this.mInputMethodManager.toggleSoftInput(2, 0);
        this.mAddTextEditText.requestFocus();
        EditText editText = this.mAddTextEditText;
        editText.setSelection(editText.getText().length());
        this.btn_text_done.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view2) {
                String obj = TextEditorDialogFragment.this.mAddTextEditText.getText().toString();
                int currentTextColor = TextEditorDialogFragment.this.mAddTextEditText.getCurrentTextColor();
                Typeface typeface2 = TextEditorDialogFragment.this.mAddTextEditText.getTypeface();
                if (!TextUtils.isEmpty(obj) && TextEditorDialogFragment.this.mTextEditor != null) {
                    TextEditorDialogFragment.this.mTextEditor.onDone(obj, currentTextColor, typeface2, TextEditorDialogFragment.alignment);
                    TextEditorDialogFragment.this.mInputMethodManager.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                    TextEditorDialogFragment.this.dismiss();
                    return;
                }
                Toast.makeText(TextEditorDialogFragment.this.getActivity(), "Enter text to add.", Toast.LENGTH_SHORT).show();
            }
        });
        this.mAddTextEditText.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
    }

    
    private class EmojiExcludeFilter implements InputFilter {
        private EmojiExcludeFilter() {
        }

        @Override 
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            while (i < i2) {
                int type = Character.getType(charSequence.charAt(i));
                if (type == 19 || type == 28) {
                    return "";
                }
                i++;
            }
            return null;
        }
    }

    @Override 
    public void onPause() {
        super.onPause();
        this.mInputMethodManager.hideSoftInputFromWindow(this.root.getWindowToken(), 0);
    }

    @Override 
    public void onResume() {
        super.onResume();
        EditText editText = this.mAddTextEditText;
        if (editText != null) {
            editText.requestFocus();
            EditText editText2 = this.mAddTextEditText;
            editText2.setSelection(editText2.getText().length());
        }
    }

    public void setOnTextEditorListener(TextEditor textEditor) {
        this.mTextEditor = textEditor;
    }

    public void setOnCloseTextEditorListener(TextEditorClose textEditorClose) {
        this.textEditorClose = textEditorClose;
    }
}
