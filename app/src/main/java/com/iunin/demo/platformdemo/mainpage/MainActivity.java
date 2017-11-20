package com.iunin.demo.platformdemo.mainpage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.invaildinvoice.ActivityInvaildInvoice;
import com.iunin.demo.platformdemo.makeinvoice.MakeInvoiceActivity;
import com.iunin.demo.platformdemo.myparams.InfoSettingActivity;
import com.iunin.demo.platformdemo.queryinvoice.ActivityQueryInvoice;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mainFunctionList;
    private Context mContext;
    private List<FunctionItem> functionItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        init();
    }

    private void init() {
        mainFunctionList = findViewById(R.id.main_function);
        functionItems = new ArrayList<>();
        initFuction(functionItems);
        MainFunctionAdapter adapter = new MainFunctionAdapter(functionItems,mContext);
        mainFunctionList.setAdapter(adapter);
        mainFunctionList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter.setOnItemClickListener(new MainFunctionAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(mContext,functionItems.get(position).getClazz());
                startActivity(intent);
            }
        });
    }

    private void initFuction(List<FunctionItem> items) {
        items.add(new FunctionItem("发票开具",R.drawable.kp, MakeInvoiceActivity.class));
        items.add(new FunctionItem("发票查询",R.drawable.query, ActivityQueryInvoice.class));
        items.add(new FunctionItem("发票作废",R.drawable.invaild, ActivityInvaildInvoice.class));
        items.add(new FunctionItem("我的信息",R.drawable.mine, InfoSettingActivity.class));
    }
}
