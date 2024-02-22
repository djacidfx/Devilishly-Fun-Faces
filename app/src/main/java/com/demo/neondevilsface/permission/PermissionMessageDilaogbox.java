package com.demo.neondevilsface.permission;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.neondevilsface.R;


public class PermissionMessageDilaogbox extends Dialog {
    private TextView PermissionMessage;
    private TextView cancel;
    private TextView grantpermissionclick;
    private String message;
    public onDialogclickListener onDialogclickListener;

    
    public interface onDialogclickListener {
        void onCancleclick();

        void onPermissionclick();
    }

    public PermissionMessageDilaogbox(Context context, String str) {
        super(context);
        this.message = str;
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pdf_permission_message_dialog);
        this.PermissionMessage = (TextView) findViewById(R.id.permissionmessage);
        this.grantpermissionclick = (TextView) findViewById(R.id.grantpermissionclick);
        this.cancel = (TextView) findViewById(R.id.cancel);
        this.PermissionMessage.setText(this.message);
        this.grantpermissionclick.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (PermissionMessageDilaogbox.this.onDialogclickListener != null) {
                    PermissionMessageDilaogbox.this.onDialogclickListener.onPermissionclick();
                }
            }
        });
        this.cancel.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (PermissionMessageDilaogbox.this.onDialogclickListener != null) {
                    PermissionMessageDilaogbox.this.onDialogclickListener.onCancleclick();
                }
            }
        });
    }

    public void SetOnDialogclickListener(onDialogclickListener ondialogclicklistener) {
        this.onDialogclickListener = ondialogclicklistener;
    }
}
