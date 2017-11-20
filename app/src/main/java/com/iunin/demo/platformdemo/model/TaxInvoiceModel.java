package com.iunin.demo.platformdemo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述发票的视图模型
 *
 * @author leon@iunin.com
 */
public class TaxInvoiceModel implements Serializable {
    public int kpLx = 0;
    /** 发票类型 */
    public String fpLxDm = "";
    /** 用户类型 */
    public String yhlx = "";
    /** 购货单位识别号 */
    public String ghdwsbh = "";
    /** 购货单位名称 */
    public String ghdwmc = "";
    /** 购货单位地址电话 */
    public String ghdwdzdh = "";
    /** 购货单位银行账号 */
    public String ghdwyhzh = "";
    /** 发票票样 */
    public String fppy = "";

    /** 商品列表 */
    public List<TaxGoodsModel> goodsList = new ArrayList<>();

    /** 备注 */
    public String bz = "";
    /** 收款人 */
    public String skr = "";
    /** 复核人 */
    public String fhr = "";
    /** 开票人 */
    public String kpr = "";
    /** 原发票代码 */
    public String yfpdm = "";
    /** 原发票号码 */
    public String yfphm = "";
    /** 收票人手机号 */
    public String sprsjh = "";


}
