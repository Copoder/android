package com.iunin.demo.platformdemo.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.utils.ConfigUtil;


/**
 * Created by copo on 17-11-22.
 */

public class PageFragment extends Fragment {
    private ConfigUtil mConfigUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigUtil = new ConfigUtil(getContext());
    }

    private ProgressDialog dialog;

    protected void showWaitDialog(String message){
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    protected void dismissWaitDialog(){
        if(dialog != null && dialog.isShowing()){

            this.dialog.dismiss();
        }
    }

    protected void switchToPage(Fragment fragment){
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content,fragment)
                .commit();
    }

    protected void showToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    protected ConfigUtil getConfigUtil(){
        return this.mConfigUtil;
    }
}
