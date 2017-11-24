package com.iunin.demo.platformdemo.displayinfosetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.ui.base.PageActivity;

/**
 * Created by copo on 17-11-22.
 */

public class ActivityDisplayInfo extends PageActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_info_setting);
        initFragment();
    }

    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content, new PageDisPlaySetting())
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_content)
                        instanceof
                        PageDisPlaySetting)) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_content, new PageDisPlaySetting())
                            .commit();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }
}
