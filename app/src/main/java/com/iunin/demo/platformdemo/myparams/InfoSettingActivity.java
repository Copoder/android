package com.iunin.demo.platformdemo.myparams;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.ui.base.PageActivity;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;
import com.iunin.demo.platformdemo.utils.ConfigUtil;
import com.iunin.demo.platformdemo.utils.Constants;

import static com.iunin.demo.platformdemo.utils.Constants.FPPY;
import static com.iunin.demo.platformdemo.utils.Constants.KPZDBS;
import static com.iunin.demo.platformdemo.utils.Constants.NSRSBH;
import static com.iunin.demo.platformdemo.utils.Constants.SELECTED_FPPY;

/**
 * Created by copo on 17-11-15.
 */

public class InfoSettingActivity extends PageActivity implements View.OnClickListener{
    private ConfigUtil configUtil;
    private AutoCompleteTextViewWithDeleteView act_narsbh;
    private AutoCompleteTextViewWithDeleteView act_kpzdbs;
    private Spinner fppy_spinner;
    private TypeStringAdapter typeStringAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        configUtil = new ConfigUtil(InfoSettingActivity.this);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act_narsbh = findViewById(R.id.edt_nasrsbh);
        act_kpzdbs = findViewById(R.id.edt_kpzdbs);
        fppy_spinner = findViewById(R.id.fppy_spinner);
        Button saveButton = findViewById(R.id.save_params);
        saveButton.setOnClickListener(this);
        setParams();
    }

    private void setParams() {
        act_narsbh.setText(configUtil.getString(NSRSBH,""));
        act_kpzdbs.setText(configUtil.getString(KPZDBS,""));
        typeStringAdapter = new TypeStringAdapter(Constants.FPPY);
        fppy_spinner.setAdapter(typeStringAdapter);
        fppy_spinner.setSelection(getSavedFppyIndex(configUtil.getString(SELECTED_FPPY,FPPY[0])));
        fppy_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fppy_spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private int getSavedFppyIndex(String str){
        int index = 0;
        for(int i = 0;i<FPPY.length;i++){
            if (str.equals(FPPY[i])){
                index = i;
            }
        }
        return index;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_params:
                saveParams();
                break;
        }
    }

    private void saveParams() {
        configUtil.putString(NSRSBH,act_narsbh.getText().toString());
        configUtil.putString(KPZDBS,act_kpzdbs.getText().toString());
        configUtil.putString(SELECTED_FPPY, (String) typeStringAdapter.getItem(fppy_spinner.getSelectedItemPosition()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
