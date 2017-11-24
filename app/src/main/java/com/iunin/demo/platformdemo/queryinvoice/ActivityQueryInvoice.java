package com.iunin.demo.platformdemo.queryinvoice;

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
import android.widget.TextView;
import android.widget.Toast;

import com.iunin.demo.platformdemo.MyApplication;
import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.myparams.TypeStringAdapter;
import com.iunin.demo.platformdemo.ui.base.PageActivity;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;
import com.iunin.demo.platformdemo.utils.ConfigUtil;
import com.iunin.service.invoice.baiwang.v1_0.userModel.InvoiceReturn;
import com.iunin.service.invoice.baiwang.v1_0.userModel.QueryInvoiceModel;
import com.iunin.service.invoice.baiwang.v1_0.userModel.ResultError;

import static com.iunin.demo.platformdemo.utils.Constants.FPLX;
import static com.iunin.demo.platformdemo.utils.Constants.FPLXDM;
import static com.iunin.demo.platformdemo.utils.Constants.FPZT;
import static com.iunin.demo.platformdemo.utils.Constants.FPZTDM;
import static com.iunin.demo.platformdemo.utils.Constants.IS_DISPLAY_FILLOUT_OPEN;
import static com.iunin.demo.platformdemo.utils.Constants.KPZDBS;
import static com.iunin.demo.platformdemo.utils.Constants.NSRSBH;

import static com.iunin.demo.platformdemo.utils.Constants.SAVED_QUERY_CODE;
import static com.iunin.demo.platformdemo.utils.Constants.SAVED_QUERY_NUM;
import static com.iunin.demo.platformdemo.utils.Constants.SAVED_QUERY_POSITON_FPLXDM;



/**
 * Created by copo on 17-11-16.
 */

public class ActivityQueryInvoice extends PageActivity implements View.OnClickListener {
    private Spinner inputFpdmlx;
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
    private Handler handler;

    private LinearLayout invoiceCard;

    private String fplxdm = "004";

    private boolean isInvoiceCardShow = false;

    private android.support.v7.widget.Toolbar toolbar;

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputFpdmlx = findViewById(R.id.fpdmlx);
        inputFphm = findViewById(R.id.invoice_num);
        inputFpdm = findViewById(R.id.invoice_code);

        final TypeStringAdapter adapter = new TypeStringAdapter(FPLX);
        inputFpdmlx.setAdapter(adapter);
        //Spinner选择并设置查询类型
        inputFpdmlx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setFplxdm((String) adapter.getItem(position));
                if(isInvoiceCardShow){
                    invoiceCard.setVisibility(View.GONE);
                    isInvoiceCardShow = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_query = findViewById(R.id.btn_save);
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

        invoiceCard = findViewById(R.id.invoice_card);

        showSavedParam();
    }

    private void showSavedParam() {
        if (mConfigUtil.getBoolean(IS_DISPLAY_FILLOUT_OPEN, false)) {
            inputFpdmlx.setSelection(mConfigUtil.getInt(SAVED_QUERY_POSITON_FPLXDM, 0));
            inputFphm.setText(mConfigUtil.getString(SAVED_QUERY_NUM, ""));
            inputFpdm.setText(mConfigUtil.getString(SAVED_QUERY_CODE, ""));
        }
    }

    private void setFplxdm(String str) {
        for (int i = 0; i < FPLX.length; i++) {
            if (str.equals(FPLX[i])) {
                fplxdm = FPLXDM[i];
                break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_invoice);
        mConfigUtil = new ConfigUtil(ActivityQueryInvoice.this);
        initView();
        Filldata();
    }

    @SuppressLint("HandlerLeak")
    private void Filldata() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dismissWaitDialog();
                ResultError<InvoiceReturn> result = (ResultError<InvoiceReturn>) msg.obj;
                if (result.code == 0) {
                    Toast.makeText(ActivityQueryInvoice.this, result.massage, Toast.LENGTH_SHORT);
                    InvoiceReturn invoiceReturn = result.data;
                    invoiceCard.setVisibility(View.VISIBLE);
                    isInvoiceCardShow = true;
                    tv_fphm.setText(invoiceReturn.fphm);
                    tv_fpdm.setText(invoiceReturn.fpdm);
                    tv_fpzt.setText(getFpzt(invoiceReturn.fpzt));
                    tv_ghdwmc.setText(invoiceReturn.ghdwmc);
                    tv_ghdwsbh.setText(invoiceReturn.ghdwsbh);
                    tv_hjje.setText(invoiceReturn.jshj);
                    tv_hjse.setText(invoiceReturn.hjse);
                    tv_dzfpxz.setText(invoiceReturn.url);
                    tv_kprq.setText(invoiceReturn.kprq);
                }
                Toast.makeText(ActivityQueryInvoice.this, result.massage, Toast.LENGTH_SHORT).show();


            }
        };
    }

    private String getFpzt(String fpztdm){
        String fpzt = "";
        for(int i = 0;i<FPZTDM.length;i++){
            if(fpztdm.equals(FPZTDM[i])){
                fpzt = FPZT[i];
            }
        }
        return fpzt;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                final Message message = new Message();
                showWaitDialog("正在查询...");
                if(isInvoiceCardShow){
                    invoiceCard.setVisibility(View.GONE);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        message.obj = queryInvoice();
                        handler.sendMessage(message);
                    }
                }).start();

        }
    }

    public ResultError<InvoiceReturn> queryInvoice() {
        String cxtj = inputFphm.getText().toString() + inputFpdm.getText().toString();
        String jsbh = mConfigUtil.getString(NSRSBH, "") + "~~" + mConfigUtil.getString(KPZDBS, "");
        QueryInvoiceModel queryInvoiceModel = new QueryInvoiceModel(jsbh, fplxdm, 0, cxtj);
        ResultError<InvoiceReturn> resulInvoice = MyApplication.getProxyInstence().queryInvoice(queryInvoiceModel);
        return resulInvoice;
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

    public void clearEditInfo(){

        inputFpdm.setText("");
        inputFphm.setText("");

        if(invoiceCard.isActivated()){
            invoiceCard.setVisibility(View.GONE);
        }
    }
}
