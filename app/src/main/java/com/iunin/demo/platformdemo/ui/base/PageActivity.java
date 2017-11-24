package com.iunin.demo.platformdemo.ui.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by copo on 17-11-23.
 */

public class PageActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    protected void showWaitDialog(String message) {
        dialog = new ProgressDialog(this);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    protected void dismissWaitDialog() {
        if (dialog != null && dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
