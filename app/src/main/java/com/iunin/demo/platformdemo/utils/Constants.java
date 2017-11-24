package com.iunin.demo.platformdemo.utils;

/**
 * Created by copo on 17-11-15.
 */

public class Constants {
    public static final String CONFIG_INFO = "config_info";
    /**
     * 纳税人识别号
     */
    public static final String NSRSBH = "nsrsbh";
    /**
     * 开票终端标识
     */
    public static final String KPZDBS = "kpzdbs";
    /**
     * 发票票样
     */
    public static final String[] FPPY = {
            "76mm*177mm",
            "76mm*152mm",
            "57mm*177mm",
            "57mm*152mm",
            "湖南票样",
            "76mm*177.8mm",
            "57mm*177.8mm"
    };
    /**
     * 已选择的发票票样
     */
    public static final String SELECTED_FPPY = "selected_fppy";
    /**
     * 开票类型
     */
    public static final String KPLXDM = "kplxdm";
    /**
     * APPID 联云平台提供
     */
    public static final String APPID = "123";
    /**
     * APPKEY 联云平台提供
     */
    public static final String APP_SCRET = "123";
    /**
     * 代理类型
     */
    public static final int PROXY_TYPE = 0;

    /**
     * 发票类型
     */
    public static final String[] FPLX = {
            "增值税专用发票",
            "增值税普通发票",
            "增值税卷式发票",
            "增值税电子发票"
    };
    /**
     * 发票类型代码
     */
    public static final String[] FPLXDM = {
            "004",
            "007",
            "025",
            "026"
    };
    /**
     * 作废类型
     */
    public static final String[] ZFLX = {
            "空白作废",
            "已开作废"
    };
    /**
     * 作废类型代码
     */
    public static final String[] ZFLXDM = {
            "0",
            "1"
    };
    /**
     * 开启数据填充
     */
    public static final String IS_DISPLAY_FILLOUT_OPEN = "is_open_display_fillout";


    public static final String DISPLAY_GMFMC = "display_gmfmc";
    public static final String DISPLAY_NSRSBH = "display_nsrsbh";
    public static final String DISPLAY_DZDH = "display_dzdh";
    public static final String DISPLAY_KHHJZH = "display_khhjzh";
    public static final String DISPLAY_SPRSJH = "display_sprsjh";
    public static final String DISPLAY_SKY = "display_sky";
    public static final String DISPLAY_FHR = "display_fhr";
    public static final String DISPLAY_KPR = "display_kpr";
    public static final String DISPLAY_BZ = "display_bz";
    /**
     * 查询发票的展示参数
     */
    public static String SAVED_QUERY_POSITON_FPLXDM = "saved_query_positon_fplxdm";
    public static String SAVED_QUERY_NUM = "saved_query_num";
    public static String SAVED_QUERY_CODE = "saved_query_code";
    /**
     * 作废发票的展示参数(已开作废)
     */
    public static String SAVED_INVALID_ZFLXDM = "saved_valid_zflxdm";
    public static String SAVED_INVALID_FPLXDM = "saved_valid_fplxdm";
    public static String SAVED_INVALID_FPDM = "saved_inlid_fpdm";
    public static String SAVED_INVALID_FPHM = "saved_inlid_fphm";
    public static String SAVED_INVALID_HJJE = "saved_inlid_hjje";
    public static String SAVED_INVALID_ZFR = "saved_invid_zfr";
    /**
     * 作废发票的展示参数(空白作废)
     */
    public static String SAVED_INVALID_KB_ZFLXDM = "saved_valid_zflxdm";
    public static String SAVED_INVALID_KB_FPLXDM = "saved_valid_fplxdm";
    public static String SAVED_INVALID_KB_ZFR = "saved_invid_zfr";
    /**
     * 发票状态代码
     */
    public static String[] FPZTDM = {
            "00",
            "01",
            "02",
            "03",
            "04"
    };
    /**
     * 发票状态描述
     */

    public static String[] FPZT = {
            "已开具的正数发票",
            "已开具的负数发票",
            "未开具发票的作废发票",
            "已开具正数发票的作废发票",
            "已开具负数发票的作废发票"
    };

    public static String TAXPLAYERID = "110101201612220010";
}
