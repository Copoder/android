package com.iunin.demo.platformdemo.makeinvoice;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.iunin.demo.platformdemo.MyApplication;
import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.myparams.TypeStringAdapter;
import com.iunin.demo.platformdemo.ui.base.PageFragment;
import com.iunin.demo.platformdemo.ui.widgit.AutoCompleteTextViewWithDeleteView;
import com.iunin.demo.platformdemo.utils.ConfigUtil;
import com.iunin.demo.platformdemo.utils.GoodsDigitHelper;
import com.iunin.service.invoice.baiwang.v1_0.userModel.InvoiceReturn;
import com.iunin.service.invoice.baiwang.v1_0.userModel.PrintInvoiceModel;
import com.iunin.service.invoice.baiwang.v1_0.userModel.PurchaserInfo;
import com.iunin.service.invoice.baiwang.v1_0.userModel.ResultError;
import com.iunin.service.invoice.baiwang.v1_0.userModel.UserGoodsModel;
import com.iunin.service.invoice.baiwang.v1_0.userModel.UserInvoiceModel;

import java.util.ArrayList;
import java.util.List;

import static com.iunin.demo.platformdemo.utils.Constants.*;


/**
 * Created by copo on 17-11-15.
 */

public class PageMakeNormalInvoice extends PageFragment implements View.OnClickListener {
    private static final int FILL_OUT_INVOICE = 1;
    private static final int QUERY_GOODS = 2;
    private static final int PRINT_INVOICE = 3;
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

    private ConfigUtil mConfigUtil;

    private UserInvoiceModel mUserInvoiceModel;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissWaitDialog();
            switch (msg.what) {
                case QUERY_GOODS:
                    ResultError<List<UserGoodsModel>> goodsResult = (ResultError<List<UserGoodsModel>>) msg.obj;
                    savedGoodsModels = goodsResult.data;
                    break;
                case FILL_OUT_INVOICE:
                    ResultError<InvoiceReturn> invoiceResult = (ResultError<InvoiceReturn>) msg.obj;
                    if (!invoiceResult.hasError()) {
                        //TODO 是否打印
                        createPrintDialog(invoiceResult.data.fphm, invoiceResult.data.fpdm);
                    }
                    showToast(invoiceResult.massage);
                    break;
                case PRINT_INVOICE:
                    ResultError result = (ResultError) msg.obj;
                    showToast(result.massage);
                    break;
            }

        }
    };

    private void createPrintDialog(final String fphm, final String fpdm) {
        new MaterialDialog.Builder(getContext())
                .title("开票成功")
                .content("是否要打印发票?")
                .positiveText("打印")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        showWaitDialog("正在打印");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.obj = MyApplication.getProxyInstence().printInvoice(new PrintInvoiceModel(mConfigUtil.getString(KPLXDM, ""),
                                        fpdm, fphm));
                                message.what = PRINT_INVOICE;
                                handler.sendMessage(message);
                            }
                        }).start();
                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();

    }

    private PurchaserInfo purchaserInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigUtil = new ConfigUtil(getContext());
        queryGoods();
    }

    //TODO 查询商品
    public void queryGoods() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.obj = MyApplication.getProxyInstence().queryGoodList(mConfigUtil.getString(KPLXDM, ""));
                message.what = QUERY_GOODS;
                handler.sendMessage(message);
            }
        }).start();
    }

    private void initView(View rootView) {
        RecyclerView goodsList = rootView.findViewById(R.id.goods_list);
        mAdapter = new GoodsListAdapter(goodsModels, getContext());
        goodsList.setAdapter(mAdapter);
        goodsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnItemClickGoodsListener(new GoodsListAdapter.OnItemClickGoodsListener() {
            @Override
            public void addGoods() {
                createGoodsListDialog();
            }

            @Override
            public void modify(UserGoodsModel model) {
                createModifyGoodsDialog(model);
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

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(null);
        setHasOptionsMenu(true);

        autoFillData();
    }

    private void autoFillData() {
        if (getConfigUtil().getBoolean(IS_DISPLAY_FILLOUT_OPEN, false)) {
            purchaser_name.setText(getConfigUtil().getString(DISPLAY_GMFMC, ""));
            nsrsbh.setText(getConfigUtil().getString(DISPLAY_NSRSBH, ""));
            address_phone.setText(getConfigUtil().getString(DISPLAY_DZDH, ""));
            bank_account.setText(getConfigUtil().getString(DISPLAY_KHHJZH, ""));
            phone_num.setText(getConfigUtil().getString(DISPLAY_SPRSJH, ""));
            payee.setText(getConfigUtil().getString(DISPLAY_SKY, ""));
            rechecker.setText(getConfigUtil().getString(DISPLAY_FHR, ""));
            invoice_maker.setText(getConfigUtil().getString(DISPLAY_KPR, ""));
            remark.setText(getConfigUtil().getString(DISPLAY_BZ, ""));
            //添加一个用于展示的虚拟商品数据
            UserGoodsModel userGoodsModel = new UserGoodsModel("测试商品", "100.00", "105.00", "5.0", "5.0");
            userGoodsModel.zk = "0.0";
            userGoodsModel.spbm = "1020102000000000000";
            userGoodsModel.lslbs = "3";
            userGoodsModel.zzstsgl = "2";
            userGoodsModel.sl = "0.0";
            userGoodsModel.spsl = "1.0";
            userGoodsModel.spsm = "";
            goodsModels.add(userGoodsModel);
        }

    }

    private void fillInvoice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResultError<InvoiceReturn> invoiceReturn = MyApplication.getProxyInstence().makeOutInvoice(mUserInvoiceModel);
                Message message = new Message();
                message.obj = invoiceReturn;
                message.what = FILL_OUT_INVOICE;
                handler.sendMessage(message);
            }
        }).start();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_make_normal_invoice, container, false);
        initView(rootView);
        return rootView;
    }

    public void setGoodsModels(ArrayList<UserGoodsModel> goodsModels) {
        this.savedGoodsModels = goodsModels;
    }

    private void createGoodsListDialog() {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_sel_good, null);
        Button cancelButton = rootView.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
        ListView listView = rootView.findViewById(R.id.id_good_list);
        listView.setAdapter(new SelectorGoodsListAdapter(getContext(), savedGoodsModels));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserGoodsModel model = savedGoodsModels.get(position);
                model.spsl = "1.0";
                model.zk = "0";
                model.se = GoodsDigitHelper.retuenSe(model);
                model.hsje = GoodsDigitHelper.returnHsje(model);
                goodsModels.add(model);
                mAdapter.notifyDataSetChanged();
                materialDialog.dismiss();
            }
        });

        materialDialog = new MaterialDialog.Builder(getContext()).customView(rootView, false).build();
        materialDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                materialDialog.cancel();
                break;
            case R.id.btn_next:
                purchaserInfo = new PurchaserInfo(purchaser_name.getText().toString());
                purchaserInfo.ghdwsbh = nsrsbh.getText().toString();
                purchaserInfo.ghdwdzdh = address_phone.getText().toString();
                purchaserInfo.ghdwyhzh = bank_account.getText().toString();
                purchaserInfo.sprsjh = phone_num.getText().toString();
                mUserInvoiceModel = new UserInvoiceModel(mConfigUtil.getString(KPLXDM, ""), 0, "0", goodsModels, invoice_maker.getText().toString());
                mUserInvoiceModel.purchaserInfo = purchaserInfo;
                mUserInvoiceModel.fhr = rechecker.getText().toString();
                mUserInvoiceModel.skr = payee.getText().toString();
                mUserInvoiceModel.bz = remark.getText().toString();

                createPreViewDialog(mUserInvoiceModel);
                //TODO 结果查询 是否打印
                break;
        }
    }

    private void showIsPrintDialog() {
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

    private void createPreViewDialog(UserInvoiceModel model) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.preview_normal, null);
        initDialogView(rootView, model);
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .customView(rootView, true)
                .title("开具预览")
                .positiveText("开具")
                .negativeText("取消")
                .buttonsGravity(GravityEnum.CENTER)
                .titleGravity(GravityEnum.CENTER)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showWaitDialog("正在开具...");
                        fillInvoice();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .build();
        dialog.show();
    }

    private void createModifyGoodsDialog(final UserGoodsModel model) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_modify_goods, null);
        TextView spmc = rootView.findViewById(R.id.spmc);
        final AutoCompleteTextViewWithDeleteView spdj = rootView.findViewById(R.id.spdj);
        final AutoCompleteTextViewWithDeleteView spsl = rootView.findViewById(R.id.spsl);
        final AutoCompleteTextViewWithDeleteView spzk = rootView.findViewById(R.id.spzk);
        Spinner spinner = rootView.findViewById(R.id.kysl);
        TypeStringAdapter adapter = new TypeStringAdapter(TypeStringAdapter.StringListToArray(model.kysl));
        spinner.setAdapter(adapter);
        spmc.setText(model.spmc);
        spdj.setText(model.hsdj);
        spsl.setText(model.spsl);
        spzk.setText(model.zk);
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .titleGravity(GravityEnum.CENTER)
                .title("商品信息修改")
                .customView(rootView, true)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //notify
                        model.spsl = spsl.getText().toString();
                        model.hsdj = spdj.getText().toString();
                        model.zk = spzk.getText().toString();
                        model.hsje = GoodsDigitHelper.returnHsje(model);
                        model.se = GoodsDigitHelper.retuenSe(model);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .negativeText("取消")
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .negativeColor(Color.GRAY)
                .cancelable(false)
                .build();
        dialog.show();

    }

    private void initDialogView(View rootView, UserInvoiceModel model) {
        TextView tv_xsfmc = rootView.findViewById(R.id.xsfmc);
        TextView tv_gmfmc = rootView.findViewById(R.id.gmfmc);
        TextView tv_nsrsbh = rootView.findViewById(R.id.nsrsbh);
        TextView tv_dzdh = rootView.findViewById(R.id.dzdh);
        TextView tv_khhjzh = rootView.findViewById(R.id.khhjzh);
        TextView tv_sprsjh = rootView.findViewById(R.id.sprsjh);
        TextView tv_skr = rootView.findViewById(R.id.skr);
        TextView tv_fhr = rootView.findViewById(R.id.fhr);
        TextView tv_kpr = rootView.findViewById(R.id.kpr);
        TextView tv_bz = rootView.findViewById(R.id.bz);
        TextView tv_hjje = rootView.findViewById(R.id.hjje);
        TextView tv_hjse = rootView.findViewById(R.id.hjse);
        ListView lv_goodlist = rootView.findViewById(R.id.goods_list);
        GoodsListViewAdapter adapter = new GoodsListViewAdapter(model.goodList);
        lv_goodlist.setAdapter(adapter);

        tv_xsfmc.setText("深圳联云");

        tv_gmfmc.setText(model.purchaserInfo.ghdwmc);
        tv_nsrsbh.setText(model.purchaserInfo.ghdwsbh);
        tv_dzdh.setText(model.purchaserInfo.ghdwdzdh);
        tv_khhjzh.setText(model.purchaserInfo.ghdwyhzh);
        tv_sprsjh.setText(model.purchaserInfo.sprsjh);

        tv_skr.setText(model.skr);
        tv_fhr.setText(model.fhr);
        tv_kpr.setText(model.kpr);
        tv_bz.setText(model.bz);
        tv_hjje.setText(GoodsDigitHelper.returnListHjje(model.goodList));
        tv_hjse.setText(GoodsDigitHelper.returnListHjse(model.goodList));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switchToPage(new PageSelectKplx());
                break;
        }
        return true;
    }

}
