package com.iunin.demo.platformdemo.makeinvoice;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
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

public class PageMakeNormalInvoice extends Fragment implements View.OnClickListener{
    //展示已添加的商品列表,初始为空
    private List<UserGoodsModel> goodsModels = new ArrayList<>();
    //查询到已储存的商品
    private List<UserGoodsModel> savedGoodsModels = new ArrayList<>();
    private MaterialDialog materialDialog;
    private GoodsListAdapter mAdapter;

    private AutoCompleteTextViewWithDeleteView purchaser_name;
    private AutoCompleteTextViewWithDeleteView nsrsbh;
    private AutoCompleteTextViewWithDeleteView address_phone;
    private AutoCompleteTextViewWithDeleteView bank_account;
    private AutoCompleteTextViewWithDeleteView phone_num;
    private AutoCompleteTextViewWithDeleteView payee;
    private AutoCompleteTextViewWithDeleteView rechecker;
    private AutoCompleteTextViewWithDeleteView invoice_maker;
    private AutoCompleteTextViewWithDeleteView remark;
    private Button btn_next;

    private ConfigUtil mConfigUtil ;

    private UserInvoiceModel mUserInvoiceModel;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg1 == 0){
                Toast.makeText(getContext(),"开票成功",Toast.LENGTH_SHORT);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigUtil = new ConfigUtil(getContext());
        queryGoods();
    }
    //TODO 查询商品
    public void queryGoods(){
        UserGoodsModel userGoodsModel = new UserGoodsModel("测试商品","100.00","105.00","5.0","5.0");
        userGoodsModel.zk = "0.0";
        userGoodsModel.spbm = "1020102000000000000";
        userGoodsModel.lslbs = "3";
        userGoodsModel.zzstsgl = "2";
        userGoodsModel.sl = "0.0";
        userGoodsModel.spsl = "1.0";



//        UserGoodsModel userGoodsModel1 = new UserGoodsModel("liberTango","300.00","303.00","1.0","3");
        savedGoodsModels.add(userGoodsModel);
//        savedGoodsModels.add(userGoodsModel1);
    }

    private void initView(View rootView) {
        RecyclerView goodsList = rootView.findViewById(R.id.goods_list);
        mAdapter = new GoodsListAdapter(goodsModels,getContext());
        goodsList.setAdapter(mAdapter);
        goodsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnaddGoodsListener(new GoodsListAdapter.OnaddGoodsListener() {
            @Override
            public void addGoods() {
                createGoodsListDialog();
            }
        });
        purchaser_name = rootView.findViewById(R.id.purchaser_name);
        nsrsbh = rootView.findViewById(R.id.nsrsbh);
        address_phone = rootView.findViewById(R.id.address_phone);
        bank_account = rootView.findViewById(R.id.bank_account);
        phone_num = rootView.findViewById(R.id.phone_num);
        payee = rootView.findViewById(R.id.payee);
        rechecker = rootView.findViewById(R.id.rechecker);
        invoice_maker = rootView.findViewById(R.id.invoice_maker);
        remark = rootView.findViewById(R.id.remark);
        btn_next = rootView.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    private void fillInvoice() {
        PurchaserInfo purchaserInfo = new PurchaserInfo(purchaser_name.getText().toString());
        purchaserInfo.ghdwsbh = nsrsbh.getText().toString();
        purchaserInfo.ghdwdzdh = address_phone.getText().toString();
        purchaserInfo.ghdwyhzh = bank_account.getText().toString();
        purchaserInfo.sprsjh = phone_num.getText().toString();
        mUserInvoiceModel = new UserInvoiceModel(mConfigUtil.getString(KPLXDM, ""), 0, "1", goodsModels, invoice_maker.getText().toString());
        mUserInvoiceModel.fhr = rechecker.getText().toString();
        mUserInvoiceModel.skr = payee.getText().toString();
        mUserInvoiceModel.bz = remark.getText().toString();
        String jsbh = mConfigUtil.getString(NSRSBH, "") + "~~" + mConfigUtil.getString(KPZDBS, "");
        final InvoiceProxy proxy = new InvoiceProxy(APPID, APP_SCRET, mConfigUtil.getString(NSRSBH, ""), jsbh, mConfigUtil.getString(SELECTED_FPPY, ""), PROXY_TYPE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResultBody resultBody = (ResultBody) proxy.makeOutInvoice(mUserInvoiceModel);
            }
        }).start();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_make_normal_invoice,container,false);
        initView(rootView);
        return rootView;
    }

    public void setGoodsModels(List<UserGoodsModel> goodsModels){
        this.savedGoodsModels = goodsModels;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                materialDialog.cancel();
                break;
            case R.id.btn_next:
                fillInvoice();
                //TODO 结果查询 是否打印
                break;
        }
    }

    private void showIsPrintDialog(){
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(getContext());
        dialog.title("发票开具成功,是否打印")
              .positiveText("确定")
                .negativeText("取消")
                .autoDismiss(false)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //打印
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //取消
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }
}
