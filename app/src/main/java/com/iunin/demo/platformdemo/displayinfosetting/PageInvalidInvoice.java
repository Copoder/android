package com.iunin.demo.platformdemo.displayinfosetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;


import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.myparams.TypeStringAdapter;
import com.iunin.demo.platformdemo.ui.base.PageFragment;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;

import static com.iunin.demo.platformdemo.utils.Constants.*;

/**
 * Created by copo on 17-11-23.
 */

public class PageInvalidInvoice extends PageFragment {

    private Spinner spinnerZflx;
    private Spinner spinnerFplx;
    private AutoCompleteTextViewWithDeleteView fpdm;
    private AutoCompleteTextViewWithDeleteView fphm;
    private AutoCompleteTextViewWithDeleteView hjje;
    private AutoCompleteTextViewWithDeleteView zfr;

    private int zflxdm = 0;
    private String fplxdm = "004";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_invalid_invoice_data, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        spinnerZflx = rootView.findViewById(R.id.zflx);
        spinnerZflx.setAdapter(new TypeStringAdapter(ZFLX));
        spinnerZflx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zflxdm = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerFplx = rootView.findViewById(R.id.fplx);
        spinnerFplx.setAdapter(new TypeStringAdapter(FPLX));
        spinnerFplx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fplxdm = FPLXDM[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fpdm = rootView.findViewById(R.id.invoice_code);
        fphm = rootView.findViewById(R.id.invoice_num);
        hjje = rootView.findViewById(R.id.hjje);
        zfr = rootView.findViewById(R.id.zfr);

        Button btn_save = rootView.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveParams();
            }
        });
    }

    private void saveParams() {
        if (zflxdm != 0) {

            getConfigUtil().putInt(SAVED_INVALID_ZFLXDM, zflxdm);
            getConfigUtil().putString(SAVED_INVALID_FPLXDM, fplxdm);
            getConfigUtil().putString(SAVED_INVALID_FPDM, fpdm.getText().toString());
            getConfigUtil().putString(SAVED_INVALID_FPHM, fphm.getText().toString());
            getConfigUtil().putString(SAVED_INVALID_HJJE, hjje.getText().toString());
            getConfigUtil().putString(SAVED_INVALID_ZFR, zfr.getText().toString());
        } else {
            getConfigUtil().putString(SAVED_INVALID_KB_FPLXDM, fplxdm);
            getConfigUtil().putInt(SAVED_INVALID_KB_ZFLXDM, zflxdm);
            getConfigUtil().putString(SAVED_INVALID_KB_ZFR, zfr.getText().toString());
        }
    }

    private void showSavedParams() {
        if (getConfigUtil().getInt(SAVED_INVALID_ZFLXDM, 0) == 0) {
            //空白作废
            spinnerZflx.setSelection(getConfigUtil().getInt(SAVED_INVALID_ZFLXDM, 0));
            spinnerFplx.setSelection(getFplxdmPostion(getConfigUtil().getString(SAVED_INVALID_FPLXDM, "")));
            zfr.setText(getConfigUtil().getString(SAVED_INVALID_ZFR, ""));
        } else {
            //已开作废
            spinnerZflx.setSelection(getConfigUtil().getInt(SAVED_INVALID_ZFLXDM, 0));
            spinnerFplx.setSelection(getFplxdmPostion(getConfigUtil().getString(SAVED_INVALID_FPLXDM, "")));
            zfr.setText(getConfigUtil().getString(SAVED_INVALID_ZFR, ""));
            fpdm.setText(getConfigUtil().getString(SAVED_INVALID_FPDM, ""));
            fphm.setText(getConfigUtil().getString(SAVED_INVALID_FPHM, ""));
            hjje.setText(getConfigUtil().getString(SAVED_INVALID_HJJE, ""));
        }

    }

    private int getFplxdmPostion(String str) {
        for (int i = 0; i < FPLX.length; i++) {
            if (str.equals(FPLX[i])) {
                fplxdm = FPLXDM[i];
                return i;
            }
        }
        return 0;
    }
}
