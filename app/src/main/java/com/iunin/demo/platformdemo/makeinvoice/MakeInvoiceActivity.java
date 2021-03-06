package com.iunin.demo.platformdemo.makeinvoice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.ui.base.PageActivity;

/**
 * Created by copo on 17-11-15.
 */

public class MakeInvoiceActivity extends PageActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_invoice);
        initView();
    }


    private void initView() {
        switchPage(new PageSelectKplx());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_content)
                        instanceof
                        PageSelectKplx)) {
                    switchPage(new PageSelectKplx());
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    private void switchPage(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .commit();
    }
}
