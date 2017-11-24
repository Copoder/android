package com.iunin.demo.platformdemo.utils;

import com.iunin.service.invoice.baiwang.v1_0.userModel.UserGoodsModel;

/**
 * Created by copo on 17-11-24.
 */

public class DigitHelper {
    public static String returnHsje(UserGoodsModel model) {
        return stringValue(doubleValue(model.hsdj) * doubleValue(model.spsl));
    }

    public static String retuenSe(UserGoodsModel model) {
        return stringValue(doubleValue(model.hsdj) - (doubleValue(model.hsdj) / (1 + doubleValue(model.sl))) * doubleValue(model.spsl));
    }

    public static String returnDj(UserGoodsModel model) {
        return stringValue((doubleValue(model.hsdj) / (1 + doubleValue(model.sl))));
    }

    private static Double doubleValue(String value) {
        if (!value.isEmpty()) {

            return Double.valueOf(value);
        } else {
            return 0d;
        }
    }

    private static String stringValue(Double value) {
        return String.valueOf(value);
    }
}
