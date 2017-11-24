package com.iunin.demo.platformdemo.displayinfosetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;


import com.iunin.demo.platformdemo.R;
import com.iunin.demo.platformdemo.ui.base.PageFragment;
import com.iunin.demo.platformdemo.utils.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

import static com.iunin.demo.platformdemo.utils.Constants.IS_DISPLAY_FILLOUT_OPEN;

/**
 * Created by copo on 17-11-22.
 */

public class PageDisPlaySetting extends PageFragment {
    private List<DisplayItem> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_display_item, null);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        final ConfigUtil configUtil = new ConfigUtil(getContext());
        items = new ArrayList<>();
        ListView listView = rootView.findViewById(R.id.lv_display);
        items.add(new DisplayItem("开票基本信息", new PageInvoiceData()));
        items.add(new DisplayItem("发票查询信息",new PageQueryInvoiceData()));
        items.add(new DisplayItem("发票作废信息",new PageInvalidInvoice()));
        listView.setAdapter(new ItemAdapter(items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //FIXME 发票作废信息功能尚未完成
                if(position == 2){
                    showToast("暂不可用");
                    return;
                }
                switchToPage(items.get(position).getFragment());
            }
        });
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        setHasOptionsMenu(true);

        Switch switch_button = rootView.findViewById(R.id.btn_switch);
        //FIXME 是否开启展示数据填充
        switch_button.setChecked(configUtil.getBoolean(IS_DISPLAY_FILLOUT_OPEN, false));
        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                configUtil.putBoolean(IS_DISPLAY_FILLOUT_OPEN, isChecked);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class ItemAdapter extends BaseAdapter {
        private List<DisplayItem> items;
        private View view;

        public ItemAdapter(List<DisplayItem> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_display_function, null);
            TextView tv_name = view.findViewById(R.id.name);
            tv_name.setText(this.items.get(position).getName());
            return view;
        }
    }


}
