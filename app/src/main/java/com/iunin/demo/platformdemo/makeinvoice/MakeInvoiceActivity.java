package com.iunin.demo.platformdemo.makeinvoice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.utils.ConfigUtil;

import static com.iunin.demo.platformdemo.utils.Constants.KPLXDM;

/**
 * Created by copo on 17-11-15.
 */

public class MakeInvoiceActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton rb_ZP;
    private RadioButton rb_PP;
    private RadioButton rb_JP;
    private RadioButton rb_DZP;
    private static final String[] KPLX = {"004","007","025","026"};
    private String kplx;
    private FragmentManager manager;
    private ConfigUtil configUtil;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_invoice);
        mContext = MakeInvoiceActivity.this;
        configUtil = new ConfigUtil(mContext);
        initView();
        radioGroup.check(getSavedKplx());
    }


    private void initView() {
        initFragment();
        radioGroup = findViewById(R.id.radio_group);
        rb_ZP = findViewById(R.id.rb_zp);
        rb_JP = findViewById(R.id.rb_jp);
        rb_PP = findViewById(R.id.rb_pp);
        rb_DZP = findViewById(R.id.rb_dzp);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                kplx = KPLX[getIndex(i)];
                configUtil.putString(KPLXDM,kplx);
                if(getCurrentFragment() instanceof PageMakeNormalInvoice){
                    if(kplx.equals("025")){
                        //切换到卷票页面
                        switchRollInvoice();
                    }
                }else {
                    if(kplx.equals("025")){
                        return;
                    }else {

                        //切换到非卷票页面
                        switchNormalInvoice();
                    }
                }
            }
        });
    }

    private int getIndex(int id){
        int index = -1;
        if(id == rb_ZP.getId()){
            index = 0;
        }
        if(id == rb_PP.getId()){
            index = 1;
        }
        if(id == rb_JP.getId()){
            index = 2;
        }
        if(id == rb_DZP.getId()){
            index = 3;
        }
        return index;
    }

    public int getSavedKplx(){
        int id = rb_ZP.getId();
        String kplx = configUtil.getString(KPLXDM, "004");
        if(kplx.equals("025")){
            id = rb_JP.getId();
        }
        if(kplx.equals("026")){
            id = rb_DZP.getId();
        }
        if(kplx.equals("007")){
            id = rb_PP.getId();
        }
        return id;
    }

    private Fragment getCurrentFragment(){
        return getSupportFragmentManager().findFragmentById(R.id.fragment_content);
    }

    private void initFragment(){
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(!configUtil.getString(KPLXDM,"").equals("025")){

            transaction.add(R.id.fragment_content,new PageMakeNormalInvoice());
        }else {

            transaction.add(R.id.fragment_content,new PageMakeRollInvoce());
        }

        transaction.commit();
    }

    private void switchNormalInvoice(){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_content,new PageMakeNormalInvoice());
        transaction.commit();
    }

    private void switchRollInvoice(){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_content,new PageMakeRollInvoce());
        transaction.commit();
    }
}
