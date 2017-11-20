package com.iunin.demo.platformdemo.queryinvoice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.invaildinvoice.ActivityInvaildInvoice;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;
import com.iunin.demo.platformdemo.utils.ConfigUtil;
import com.iunin.service.invoice.InvoiceProxy;
import com.iunin.service.invoice.baiwang.v1_0.Invoicemodel.QueryInvoiceModel;
import com.iunin.service.invoice.baiwang.v1_0.Invoicemodel.Result;
import com.iunin.service.invoice.baiwang.v1_0.userModel.UserInvoiceModel;

import static com.iunin.demo.platformdemo.utils.Constants.APPID;
import static com.iunin.demo.platformdemo.utils.Constants.APP_SCRET;
import static com.iunin.demo.platformdemo.utils.Constants.KPZDBS;
import static com.iunin.demo.platformdemo.utils.Constants.NSRSBH;
import static com.iunin.demo.platformdemo.utils.Constants.PROXY_TYPE;
import static com.iunin.demo.platformdemo.utils.Constants.SELECTED_FPPY;


/**
 * Created by copo on 17-11-16.
 */

public class ActivityQueryInvoice extends AppCompatActivity implements View.OnClickListener{
    private AutoCompleteTextViewWithDeleteView inputFpdmlx;
    private AutoCompleteTextViewWithDeleteView inputFphm;
    private AutoCompleteTextViewWithDeleteView inputFpdm;
    private Button btn_query;

    private TextView tv_fphm;
    private TextView tv_fpzt;
    private TextView tv_fpdm;
    private TextView tv_sksbbh;
    private TextView tv_kprq;
    private TextView tv_ghdwsbh;
    private TextView tv_ghdwmc;
    private TextView tv_hjje;
    private TextView tv_hjse;
    private TextView tv_jshj;
    private TextView tv_dzfpxz;

    private ConfigUtil mConfigUtil;
    private void initView() {
        inputFpdmlx = findViewById(R.id.fpdmlx);
        inputFphm = findViewById(R.id.invoice_num);
        inputFpdm = findViewById(R.id.invoice_code);

        btn_query = findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        tv_fphm = findViewById(R.id.tv_invoice_num);
        tv_fpdm = findViewById(R.id.tv_invoice_code);
        tv_fpzt = findViewById(R.id.tv_invoice_status);
        tv_sksbbh = findViewById(R.id.sksbbh);
        tv_kprq = findViewById(R.id.kprq);
        tv_ghdwsbh = findViewById(R.id.ghdwsbh);
        tv_ghdwmc = findViewById(R.id.ghdwmc);
        tv_hjje = findViewById(R.id.tv_hjje);
        tv_hjse = findViewById(R.id.tv_hjse);
        tv_jshj = findViewById(R.id.tv_jshj);
        tv_dzfpxz = findViewById(R.id.download_url);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_invoice);
        mConfigUtil = new ConfigUtil(ActivityQueryInvoice.this);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        queryInvoice();
                    }
                }).start();
                break;
        }
    }

    //TODO 发票查询的方法
    public void queryInvoice(){
        String fplxdm = inputFpdmlx.getText().toString();
        String cxtj = inputFphm.getText().toString() + inputFpdm.getText().toString();
        String jsbh = mConfigUtil.getString(NSRSBH, "") + "~~" + mConfigUtil.getString(KPZDBS, "");
        final InvoiceProxy proxy = new InvoiceProxy(APPID, APP_SCRET, mConfigUtil.getString(NSRSBH, ""), jsbh, mConfigUtil.getString(SELECTED_FPPY, ""), PROXY_TYPE);
        QueryInvoiceModel queryInvoiceModel = new QueryInvoiceModel(jsbh,fplxdm,0,cxtj);
        Result result = (Result) proxy.queryInvoice(queryInvoiceModel);
        Log.i("info",result.body.toString());
    }
}
