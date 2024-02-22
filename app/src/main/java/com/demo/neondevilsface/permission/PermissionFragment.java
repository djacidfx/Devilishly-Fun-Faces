package com.demo.neondevilsface.permission;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;

import com.demo.neondevilsface.R;


public class PermissionFragment extends DialogFragment {
    public static PermissionMessageDilaogbox permissionMessageDilaogbox;
    private String PermissionMessage1;
    private String PermissionMessage2;
    private String PermissionMessage3;
    private String[] Permissionstring;
    public Context context;
    Boolean is_form;
    public OnPermissionresult onPermissionresult;
    private boolean isstop = false;
    private boolean permissiongranted = false;
    private boolean variable1 = false;
    private boolean variable2 = false;

    
    public interface OnPermissionresult {
        void onFail();

        void onSuccess();
    }

    private void ShowMessagedialog_After_Permission(String str) {
        final PermissionMessageDilaogbox permissionMessageDilaogbox2 = new PermissionMessageDilaogbox(this.context, str);
        permissionMessageDilaogbox2.setCancelable(false);
        permissionMessageDilaogbox2.getWindow().getDecorView().setBackgroundResource(17170445);
        permissionMessageDilaogbox2.getWindow().setDimAmount(0.2f);
        permissionMessageDilaogbox2.SetOnDialogclickListener(new PermissionMessageDilaogbox.onDialogclickListener() { 
            @Override 
            public void onPermissionclick() {
                PermissionFragment.this.RequestPermission();
                permissionMessageDilaogbox2.cancel();
            }

            @Override 
            public void onCancleclick() {
                if (PermissionFragment.this.onPermissionresult != null) {
                    PermissionFragment.this.onPermissionresult.onFail();
                }
                permissionMessageDilaogbox2.cancel();
                PermissionFragment.this.dismiss();
            }
        });
        permissionMessageDilaogbox2.show();
    }

    private void ShowMessagedialog_If_NeverAsk_checked(String str) {
        PermissionMessageDilaogbox permissionMessageDilaogbox2 = new PermissionMessageDilaogbox(this.context, str);
        permissionMessageDilaogbox = permissionMessageDilaogbox2;
        permissionMessageDilaogbox2.setCancelable(false);
        permissionMessageDilaogbox.getWindow().getDecorView().setBackgroundResource(17170445);
        permissionMessageDilaogbox.getWindow().setDimAmount(0.2f);
        permissionMessageDilaogbox.SetOnDialogclickListener(new PermissionMessageDilaogbox.onDialogclickListener() { 
            @Override 
            public void onPermissionclick() {
                PermissionFragment.permissionMessageDilaogbox.cancel();
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", PermissionFragment.this.context.getPackageName(), null));
                PermissionFragment.this.startActivityForResult(intent, 2);
            }

            @Override 
            public void onCancleclick() {
                if (PermissionFragment.this.onPermissionresult != null) {
                    PermissionFragment.this.onPermissionresult.onFail();
                }
                PermissionFragment.permissionMessageDilaogbox.cancel();
                PermissionFragment.this.dismiss();
            }
        });
        permissionMessageDilaogbox.show();
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.e("PermissionFragment", "inside of onCreatview");
        View inflate = layoutInflater.inflate(R.layout.pdf_fragment_permission, viewGroup, false);
        this.context = inflate.getContext();
        this.isstop = false;
        return inflate;
    }

    @Override 
    public void onStart() {
        Log.e("PermissionFragment", "inside of onStart");
        super.onStart();
        if (!this.isstop) {
            Log.e("PermissionFragment", "inside of onStart if");
            performafterpermission();
            return;
        }
        Log.e("PermissionFragment", "inside of onStart else");
        this.isstop = false;
    }

    @Override 
    public void onPause() {
        super.onPause();
    }

    @Override 
    public void onResume() {
        Log.e("PermissionFragment", "inside of onResume");
        super.onResume();
    }

    @Override 
    public void onStop() {
        Log.e("PermissionFragment", "inside of onStop");
        this.isstop = true;
        super.onStop();
    }

    private void performafterpermission() {
        if (!CheckPermission()) {
            RequestPermission();
            return;
        }
        OnPermissionresult onPermissionresult = this.onPermissionresult;
        if (onPermissionresult != null) {
            onPermissionresult.onSuccess();
            dismiss();
        }
    }

    private boolean CheckPermission() {
        for (String str : this.Permissionstring) {
            if (ContextCompat.checkSelfPermission(this.context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    public void RequestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(this.Permissionstring, 1);
        }
    }

    @Override 
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        String[] strArr2;
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1) {
            this.permissiongranted = true;
            for (int i2 : iArr) {
                if (i2 != 0) {
                    this.permissiongranted = false;
                }
            }
            if (this.permissiongranted) {
                OnPermissionresult onPermissionresult = this.onPermissionresult;
                if (onPermissionresult != null) {
                    onPermissionresult.onSuccess();
                    dismiss();
                }
            } else if (Build.VERSION.SDK_INT >= 23) {
                this.variable2 = false;
                this.variable1 = false;
                for (String str : this.Permissionstring) {
                    if (shouldShowRequestPermissionRationale(str)) {
                        this.variable1 = true;
                    } else if (ContextCompat.checkSelfPermission(this.context, str) != 0) {
                        this.variable2 = true;
                    }
                }
                boolean z = this.variable1;
                if (z) {
                    ShowMessagedialog_After_Permission(this.PermissionMessage1);
                } else if (!z && this.variable2) {
                    ShowMessagedialog_If_NeverAsk_checked(this.PermissionMessage2);
                }
            }
        }
    }

    @Override 
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (CheckPermission()) {
            OnPermissionresult onPermissionresult = this.onPermissionresult;
            if (onPermissionresult != null) {
                onPermissionresult.onSuccess();
            }
        } else if (this.onPermissionresult != null) {
            Log.e("onActivityResult", "onActivityResult: " + this.is_form);
            if (this.is_form.booleanValue()) {
                this.onPermissionresult.onFail();
                return;
            }
            try {
                PermissionMessageDilaogbox permissionMessageDilaogbox2 = permissionMessageDilaogbox;
                if (permissionMessageDilaogbox2 == null || permissionMessageDilaogbox2.isShowing()) {
                    return;
                }
                permissionMessageDilaogbox.show();
            } catch (Exception unused) {
            }
        }
    }

    public PermissionFragment CheckForPermission(Boolean bool, String[] strArr, String str, OnPermissionresult onPermissionresult) {
        this.onPermissionresult = onPermissionresult;
        this.Permissionstring = strArr;
        this.PermissionMessage1 = str;
        this.PermissionMessage2 = str;
        this.PermissionMessage3 = str;
        this.is_form = bool;
        return this;
    }

    @Override 
    public void show(FragmentManager fragmentManager, String str) {
        super.show(fragmentManager, str);
    }
}
