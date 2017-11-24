package com.iunin.demo.platformdemo.displayinfosetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.ui.base.PageFragment;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;
import com.iunin.demo.platformdemo.utils.ConfigUtil;

import static com.iunin.demo.platformdemo.utils.Constants.*;

/**
 * Created by copo on 17-11-22.
 */

public class PageInvoiceData extends PageFragment implements View.OnClickListener {

    private AutoCompleteTextViewWithDeleteView purchaser_name;
    private AutoCompleteTextViewWithDeleteView nsrsbh;
    private AutoCompleteTextViewWithDeleteView address_phone;
    private AutoCompleteTextViewWithDeleteView bank_account;
    private AutoCompleteTextViewWithDeleteView phone_num;
    private AutoCompleteTextViewWithDeleteView payee;
    private AutoCompleteTextViewWithDeleteView rechecker;
    private AutoCompleteTextViewWithDeleteView invoice_maker;
    private AutoCompleteTextViewWithDeleteView remark;

    private ConfigUtil configUtil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fillout_info, null);
        initView(rootView);
        configUtil = new ConfigUtil(getContext());
        return rootView;
    }

    private void initView(View rootView) {
        purchaser_name = rootView.findViewById(R.id.purchaser_name);
        nsrsbh = rootView.findViewById(R.id.nsrsbh);
        address_phone = rootView.findViewById(R.id.address_phone);
        bank_account = rootView.findViewById(R.id.bank_account);
        phone_num = rootView.findViewById(R.id.phone_num);
        payee = rootView.findViewById(R.id.payee);
        rechecker = rootView.findViewById(R.id.rechecker);
        invoice_maker = rootView.findViewById(R.id.invoice_maker);
        remark = rootView.findViewById(R.id.remark);
        Button btn_save = rootView.findViewById(R.id.btn_next);
        btn_save.setOnClickListener(this);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);
        setHasOptionsMenu(true);
        autoFillData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                fillData();
                break;
        }
    }

    private void fillData() {
        configUtil.putString(DISPLAY_GMFMC, purchaser_name.getText().toString());
        configUtil.putString(DISPLAY_NSRSBH, nsrsbh.getText().toString());
        configUtil.putString(DISPLAY_DZDH, address_phone.getText().toString());
        configUtil.putString(DISPLAY_KHHJZH, bank_account.getText().toString());
        configUtil.putString(DISPLAY_SPRSJH, phone_num.getText().toString());
        configUtil.putString(DISPLAY_SKY, payee.getText().toString());
        configUtil.putString(DISPLAY_FHR, rechecker.getText().toString());
        configUtil.putString(DISPLAY_KPR, invoice_maker.getText().toString());
        configUtil.putString(DISPLAY_BZ, remark.getText().toString());
        showToast("已保存");
    }

    private void autoFillData() {

            purchaser_name.setText(getConfigUtil().getString(DISPLAY_GMFMC,""));
            nsrsbh.setText(getConfigUtil().getString(DISPLAY_NSRSBH,""));
            address_phone.setText(getConfigUtil().getString(DISPLAY_DZDH,""));
            bank_account.setText(getConfigUtil().getString(DISPLAY_KHHJZH,""));
            phone_num.setText(getConfigUtil().getString(DISPLAY_SPRSJH,""));
            payee.setText(getConfigUtil().getString(DISPLAY_SKY,""));
            rechecker.setText(getConfigUtil().getString(DISPLAY_FHR,""));
            invoice_maker.setText(getConfigUtil().getString(DISPLAY_KPR,""));
            remark.setText(getConfigUtil().getString(DISPLAY_BZ,""));

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
}
