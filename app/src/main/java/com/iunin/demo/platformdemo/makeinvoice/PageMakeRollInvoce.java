package com.iunin.demo.platformdemo.makeinvoice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;
import com.iunin.demo.platformdemo.utils.ConfigUtil;
import com.iunin.service.invoice.InvoiceProxy;
import com.iunin.service.invoice.baiwang.v1_0.Invoicemodel.ResultBody;
import com.iunin.service.invoice.baiwang.v1_0.userModel.PurchaserInfo;
import com.iunin.service.invoice.baiwang.v1_0.userModel.UserGoodsModel;
import com.iunin.service.invoice.baiwang.v1_0.userModel.UserInvoiceModel;

import java.util.ArrayList;
import java.util.List;

import static com.iunin.demo.platformdemo.utils.Constants.APPID;
import static com.iunin.demo.platformdemo.utils.Constants.APP_SCRET;
import static com.iunin.demo.platformdemo.utils.Constants.KPLXDM;
import static com.iunin.demo.platformdemo.utils.Constants.KPZDBS;
import static com.iunin.demo.platformdemo.utils.Constants.NSRSBH;
import static com.iunin.demo.platformdemo.utils.Constants.PROXY_TYPE;
import static com.iunin.demo.platformdemo.utils.Constants.SELECTED_FPPY;

/**
 * Created by copo on 17-11-15.
 */

public class PageMakeRollInvoce extends Fragment implements View.OnClickListener{
    //展示已添加的商品列表,初始为空
    private List<UserGoodsModel> goodsModels = new ArrayList<>();
    //查询到已储存的商品
    private List<UserGoodsModel> savedGoodsModels = new ArrayList<>();
    private MaterialDialog materialDialog;
    private GoodsListAdapter mAdapter;
    private AutoCompleteTextViewWithDeleteView purchaserName;
    private AutoCompleteTextViewWithDeleteView nsrsbh;
    private AutoCompleteTextViewWithDeleteView phoneNum;
    private AutoCompleteTextViewWithDeleteView payee;
    private AutoCompleteTextViewWithDeleteView invoiceMaker;
    private Button btn_next;

    private ConfigUtil mConfigUtil;
    private UserInvoiceModel mUserInvoiceModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigUtil = new ConfigUtil(getContext());
    }

    private void initView(View rootView) {
        purchaserName = rootView.findViewById(R.id.purchaser_name);
        nsrsbh = rootView.findViewById(R.id.nsrsbh);
        phoneNum = rootView.findViewById(R.id.phone_num);
        payee = rootView.findViewById(R.id.payee);
        btn_next = rootView.findViewById(R.id.btn_next);
        invoiceMaker = rootView.findViewById(R.id.invoice_maker);
        btn_next.setOnClickListener(this);

        RecyclerView goodList = rootView.findViewById(R.id.goods_list);
        mAdapter = new GoodsListAdapter(goodsModels,getContext());
        goodList.setAdapter(mAdapter);
        goodList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnaddGoodsListener(new GoodsListAdapter.OnaddGoodsListener() {
            @Override
            public void addGoods() {
                createGoodsListDialog();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_make_roll_invoice,container,false);
        queryGoods();
        initView(rootView);
        return rootView;
    }

    // TODO/**查询商品,暂模拟数据**/
    public void queryGoods(){
        UserGoodsModel userGoodsModel = new UserGoodsModel("测试商品","100.00","100.00","0","0");
        userGoodsModel.zk = "0.0";
        userGoodsModel.spbm = "1020102000000000000";
        userGoodsModel.zzstsgl = "2";
        userGoodsModel.lslbs = "1";
        UserGoodsModel userGoodsModel1 = new UserGoodsModel("liberTango","300.00","303.00","0.0","3");
        savedGoodsModels.add(userGoodsModel);
        savedGoodsModels.add(userGoodsModel1);
    }

    private void createGoodsListDialog() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_sel_good,null);
        Button cancelButton = rootView.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
        ListView listView = rootView.findViewById(R.id.id_good_list);
        listView.setAdapter(new SelectorGoodsListAdapter(getContext(),savedGoodsModels));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserGoodsModel model = savedGoodsModels.get(position);
                goodsModels.add(model);
                mAdapter.notifyDataSetChanged();
                materialDialog.dismiss();
            }
        });

        materialDialog = new MaterialDialog.Builder(getContext()).customView(rootView,false).build();
        materialDialog.show();
    }

    private void fillOutInvoice(){
        PurchaserInfo purchaserInfo = new PurchaserInfo(purchaserName.getText().toString());
        purchaserInfo.ghdwsbh = nsrsbh.getText().toString();
        purchaserInfo.sprsjh = phoneNum.getText().toString();
        mUserInvoiceModel = new UserInvoiceModel(mConfigUtil.getString(KPLXDM,""),0,"1",goodsModels,invoiceMaker.getText().toString());
        mUserInvoiceModel.skr = payee.getText().toString();
        mUserInvoiceModel.purchaserInfo = purchaserInfo;
        mUserInvoiceModel.yhlx = "0";
        String jsbh = mConfigUtil.getString(NSRSBH,"") + "~~" + mConfigUtil.getString(KPZDBS,"");
        final InvoiceProxy proxy = new InvoiceProxy(APPID,APP_SCRET,mConfigUtil.getString(NSRSBH,""),jsbh,mConfigUtil.getString(SELECTED_FPPY,""),PROXY_TYPE);
        //TODO Handler
        new Thread(new Runnable() {
            @Override
            public void run() {
             proxy.makeOutInvoice(mUserInvoiceModel);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                materialDialog.cancel();
                break;
            case R.id.btn_next:
                fillOutInvoice();
                break;
        }
    }
}
