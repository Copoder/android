package com.iunin.demo.platformdemo;

import android.app.Application;

import com.iunin.demo.platformdemo.utils.ConfigUtil;
import com.iunin.service.invoice.baiwang.v1_0.userModel.InvoiceProxy;
import com.iunin.service.invoice.baiwang.v1_0.userModel.ParameterInfo;

import static com.iunin.demo.platformdemo.utils.Constants.*;

/**
 * Created by copo on 17-11-24.
 */

public class MyApplication extends Application {
    private static InvoiceProxy proxy;
    private ConfigUtil mConfigUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        mConfigUtil = new ConfigUtil(this);
        String jsbh = mConfigUtil.getString(NSRSBH, "") + "~~" + mConfigUtil.getString(KPZDBS, "");
        ParameterInfo info = new ParameterInfo(APP_SCRET,TAXPLAYERID,jsbh,mConfigUtil.getString(SELECTED_FPPY,""));
        info.setAppID(123);
        info.setUrl("https://honekit.com:8080/invoicer/v1.0/api");
        proxy = new InvoiceProxy(info,PROXY_TYPE);
    }

    public static InvoiceProxy getProxyInstence(){
        return proxy;
    }

}
