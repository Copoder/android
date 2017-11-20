package com.iunin.demo.platformdemo.model;


import java.math.BigDecimal;

/**
 * @author leon@iunin.com
 */
public class TaxGoodsModel  {
    /**
     * 0 正常行 1 折扣行 2 被折扣行
     */
    public String fphxz = "";
    /**
     * 商品名称
     */
    public String spmc = "";
    /**
     * 规格型号
     */
    public String ggxh = "";
    /**
     * 单位
     */
    public String dw = "";
    /**
     *商品数量
     */
    public BigDecimal spsl = BigDecimal.ZERO;
    /**
     *单价(不含税)普，专，电子票
     */
    public BigDecimal dj = BigDecimal.ZERO;
    /**
     *金额(不含税)普，专，电子票
     */
    public BigDecimal je = BigDecimal.ZERO;
    /**
     *单价(不含税)卷票
     */
    public BigDecimal hsdj = BigDecimal.ZERO;
    /**
     *金额(不含税)卷票
     */
    public BigDecimal hsje = BigDecimal.ZERO;
    /**
     *税率
     */
    public BigDecimal sl = BigDecimal.ZERO;
    /**
     *税额
     */
    public BigDecimal se = BigDecimal.ZERO;
    /**
     *商品编码
     */
    public String spbm = "";
    /**
     *自行编码
     */
    public String zxbm = "";
    /**
     *优惠政策标识
     */
    public int  yhzcbs = 0;
    /**
     *0税率标识
     */
    public int lslbs = 0;
    /**
     *增值税特殊管理
     */
    public String zzstsgl = "";
    /**
     * 折扣
     */
    public String disCount = "";

    /**
     * 开 专/普/电子票
     * @param fphxz
     * @param spmc
     * @param spsl
     * @param sl
     * @param se
     */
    public TaxGoodsModel(String fphxz, String spmc, BigDecimal spsl, BigDecimal sl, BigDecimal se) {
        this.fphxz = fphxz;
        this.spmc = spmc;
        this.spsl = spsl;
        this.sl = sl;
        this.se = se;
    }


}
