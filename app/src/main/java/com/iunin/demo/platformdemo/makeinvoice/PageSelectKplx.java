package com.iunin.demo.platformdemo.makeinvoice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.ui.base.PageFragment;

import static com.iunin.demo.platformdemo.utils.Constants.*;


/**
 * Created by copo on 17-11-22.
 */

public class PageSelectKplx extends PageFragment implements View.OnClickListener {
    private static final String[] TYPE = {"ROLL", "NORMAL"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_select_fplx, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        RelativeLayout rlZp = rootView.findViewById(R.id.rl_zp);
        RelativeLayout rlPp = rootView.findViewById(R.id.rl_pp);
        RelativeLayout rlJp = rootView.findViewById(R.id.rl_jp);
        RelativeLayout rlDzp = rootView.findViewById(R.id.rl_dzp);
        rlZp.setOnClickListener(this);
        rlPp.setOnClickListener(this);
        rlJp.setOnClickListener(this);
        rlDzp.setOnClickListener(this);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(null);
        setHasOptionsMenu(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_zp:
                getConfigUtil().putString(KPLXDM, "004");
                switchPage(TYPE[0]);
                break;
            case R.id.rl_pp:
                getConfigUtil().putString(KPLXDM, "007");
                switchPage(TYPE[0]);
                break;
            case R.id.rl_dzp:
                showToast("电子发票暂不可用");
//                getConfigUtil().putString(KPLXDM, "026");
//                switchPage(TYPE[0]);
                break;
            case R.id.rl_jp:
                getConfigUtil().putString(KPLXDM, "025");
                switchPage(TYPE[1]);
                break;
        }
    }

    private void switchPage(String type) {
        if (type.equals(TYPE[0])) {
            switchToPage(new PageMakeNormalInvoice());
        } else {
            switchToPage(new PageMakeRollInvoce());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return true;
    }
}
