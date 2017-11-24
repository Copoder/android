package com.iunin.demo.platformdemo.utils;

import com.iunin.service.invoice.baiwang.v1_0.userModel.UserGoodsModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by copo on 17-11-24.
 */

public class GoodsDigitHelper {
    public static String returnHsje(UserGoodsModel model) {
        return stringValue(doubleValue(model.hsdj) * doubleValue(model.spsl));
    }

    public static String retuenSe(UserGoodsModel model) {
        DecimalFormat format = new DecimalFormat("#.00");
        double se = doubleValue(model.hsdj) - (doubleValue(model.hsdj) / (1 + doubleValue(model.sl))) * doubleValue(model.spsl);
        if (se != 0) {
            return format.format(se);
        }else {
            return "0";
        }
    }

    public static String returnDj(UserGoodsModel model) {
        return stringValue((doubleValue(model.hsdj) / (1 + doubleValue(model.sl))));
    }

    public static String returnListHjje(List<UserGoodsModel> models) {
        double hjje = 0d;
        for (UserGoodsModel model : models) {
            hjje += doubleValue(model.hsdj) * doubleValue(model.spsl);
        }
        return stringValue(hjje);
    }

    public static String returnListHjse(List<UserGoodsModel> models) {
        double hjse = 0d;
        for (UserGoodsModel model : models) {
            hjse += doubleValue(model.se) * doubleValue(model.spsl);
        }
        DecimalFormat format = new DecimalFormat("#.00");
        return format.format(hjse);
    }


    private static Double doubleValue(String value) {
        if (!value.isEmpty()) {

            return Double.valueOf(value);
        } else {
            return 0d;
        }
    }

    /**
     * 百分比化的税率
     *
     * @param model
     * @return
     */
    public static String returnPercentSl(UserGoodsModel model) {
        return stringValue(doubleValue(model.sl) * 100);
    }

    private static String stringValue(Double value) {
        return String.valueOf(value);
    }
}
