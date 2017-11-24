package com.iunin.demo.platformdemo.displayinfosetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.myparams.TypeStringAdapter;
import com.iunin.demo.platformdemo.ui.base.PageFragment;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;
import com.iunin.demo.platformdemo.utils.ConfigUtil;

import static com.iunin.demo.platformdemo.utils.Constants.*;

/**
 * Created by copo on 17-11-22.
 */

public class PageQueryInvoiceData extends PageFragment implements View.OnClickListener{
    private ConfigUtil mConfigUtil;
    private int fplx_position;
    private AutoCompleteTextViewWithDeleteView inputFphm;
    private AutoCompleteTextViewWithDeleteView inputFpdm;
    private Spinner inputFpdmlx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigUtil = new ConfigUtil(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_query_invoice,null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);
        setHasOptionsMenu(true);
        inputFpdmlx = rootView.findViewById(R.id.fpdmlx);
        inputFphm = rootView.findViewById(R.id.invoice_num);
        inputFpdm = rootView.findViewById(R.id.invoice_code);
        TypeStringAdapter adapter = new TypeStringAdapter(FPLX);
        inputFpdmlx.setAdapter(adapter);
        showSavedParam();

        //Spinner选择并设置查询类型
        inputFpdmlx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fplx_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button btn_save = rootView.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                saveQueryParam();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                switchToPage(new PageDisPlaySetting());
                break;
        }
        return true;
    }

    private void saveQueryParam() {
        mConfigUtil.putInt(SAVED_QUERY_POSITON_FPLXDM,fplx_position);
        mConfigUtil.putString(SAVED_QUERY_NUM,inputFphm.getText().toString());
        mConfigUtil.putString(SAVED_QUERY_CODE,inputFpdm.getText().toString());
        showToast("保存成功");
    }

    private void showSavedParam(){
        inputFpdmlx.setSelection(mConfigUtil.getInt(SAVED_QUERY_POSITON_FPLXDM,0));
        inputFphm.setText(mConfigUtil.getString(SAVED_QUERY_NUM,""));
        inputFpdm.setText(mConfigUtil.getString(SAVED_QUERY_CODE,""));
    }
}
