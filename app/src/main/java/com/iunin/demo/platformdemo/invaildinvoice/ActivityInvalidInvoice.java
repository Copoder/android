package com.iunin.demo.platformdemo.invaildinvoice;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;



import com.iunin.demo.platformdemo.MyApplication;
import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.myparams.TypeStringAdapter;
import com.iunin.demo.platformdemo.ui.base.PageActivity;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;
import com.iunin.demo.platformdemo.utils.ConfigUtil;

import com.iunin.service.invoice.baiwang.v1_0.userModel.InvalidInvoiceModel;
import com.iunin.service.invoice.baiwang.v1_0.userModel.InvalidInvoiceReturn;
import com.iunin.service.invoice.baiwang.v1_0.userModel.ResultError;

import static com.iunin.demo.platformdemo.utils.Constants.FPLX;
import static com.iunin.demo.platformdemo.utils.Constants.FPLXDM;
import static com.iunin.demo.platformdemo.utils.Constants.KPZDBS;
import static com.iunin.demo.platformdemo.utils.Constants.NSRSBH;
import static com.iunin.demo.platformdemo.utils.Constants.ZFLX;
import static com.iunin.demo.platformdemo.utils.Constants.ZFLXDM;

/**
 * Created by copo on 17-11-16.
 */

public class ActivityInvalidInvoice extends PageActivity implements View.OnClickListener {
    private Spinner zflx;
    private Spinner fplx;
    private AutoCompleteTextViewWithDeleteView fpdm;
    private AutoCompleteTextViewWithDeleteView fphm;
    private AutoCompleteTextViewWithDeleteView hjje;
    private AutoCompleteTextViewWithDeleteView zfr;
    private Button btn_invalid;

    private LinearLayout ll_fpdm;
    private LinearLayout ll_fphm;
    private LinearLayout ll_hjje;

    private String zflxdm;
    private String fplxdm;

    private final String KBZF = "0";
    private final String YKZF = "1";

    private ConfigUtil mConfigUtil;

    private android.support.v7.widget.Toolbar toolbar;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissWaitDialog();
            ResultError<InvalidInvoiceReturn> result = (ResultError<InvalidInvoiceReturn>) msg.obj;
            showToast(result.massage);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigUtil = new ConfigUtil(ActivityInvalidInvoice.this);
        zflxdm = KBZF;
        setContentView(R.layout.activity_invalid_invoice);
        initView();
    }

    private void initView() {
        zflx = findViewById(R.id.zflx);
        TypeStringAdapter zflxAdapter = new TypeStringAdapter(ZFLX);
        zflx.setAdapter(zflxAdapter);
        zflx.setSelection(0);
        zflx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zflxdm = ZFLXDM[position];
                if (position == 0) {
                    //空白作废
                    ll_fpdm.setVisibility(View.GONE);
                    ll_fphm.setVisibility(View.GONE);
                    ll_hjje.setVisibility(View.GONE);
                } else {
                    //已开作废
                    ll_fpdm.setVisibility(View.VISIBLE);
                    ll_fphm.setVisibility(View.VISIBLE);
                    ll_hjje.setVisibility(View.VISIBLE);
                    //第一个输入框重新获取焦点
                    fpdm.setFocusable(true);
                    fpdm.requestFocus();
                }
                zflxdm = position + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fplx = findViewById(R.id.fplx);
        TypeStringAdapter fplxAdapter = new TypeStringAdapter(FPLX);
        fplx.setAdapter(fplxAdapter);
        fplx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fplxdm = FPLXDM[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fpdm = findViewById(R.id.invoice_code);
        fphm = findViewById(R.id.invoice_num);
        hjje = findViewById(R.id.hjje);
        zfr = findViewById(R.id.zfr);

        ll_fpdm = findViewById(R.id.ll_fpdm);
        ll_fphm = findViewById(R.id.ll_fphm);
        ll_hjje = findViewById(R.id.ll_hjje);

        btn_invalid = findViewById(R.id.btn_invalid);
        btn_invalid.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_invalid:
                showWaitDialog("正在操作...");
                invalidInvoice(zflxdm);
                break;
        }
    }

    private void invalidInvoice(String type) {
        String zfr = this.zfr.getText().toString();
        String jsbh = mConfigUtil.getString(NSRSBH, "") + "~~" + mConfigUtil.getString(KPZDBS, "");
        InvalidInvoiceModel invalidInvoiceModel = null;
        switch (type) {
            case KBZF:
                invalidInvoiceModel = new InvalidInvoiceModel();
                invalidInvoiceModel.zfr = zfr;
                invalidInvoiceModel.fplxdm = fplxdm;
                invalidInvoiceModel.zflx = type;
                break;
            case YKZF:
                invalidInvoiceModel = new InvalidInvoiceModel(fplxdm, type, fpdm.getText().toString()
                        , fphm.getText().toString(), hjje.getText().toString(), zfr);
                break;
        }
        final InvalidInvoiceModel invalidInvoiceModel1 = invalidInvoiceModel;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.obj = MyApplication.getProxyInstence().invalidInvoice(invalidInvoiceModel1);
                handler.sendMessage(message);

            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
