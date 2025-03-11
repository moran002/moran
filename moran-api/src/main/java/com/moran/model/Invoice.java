package com.moran.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : moran
 */
@Data
public class Invoice {
    /**
     * 订单编号
     */
    private String orderSn;
    private String invoiceSn;
    /**
     * 发行时间
     */
    private String issueTime;
    /**
     * 到期时间 = 发行时间+ 条件
     */
    private String dueTime;
    /**
     * 条件(天)
     */
    private Integer terms;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 公司电话
     */
    private String companyPhone;
    /**
     * 客户名称
     */
    private String memberName;
    /**
     * 客户电话
     */
    private String memberPhone;
    /**
     * 客户邮箱
     */
    private String memberEmail;
    /**
     * 发票开具人(名称)
     */
    private String raisedBy;
    /**
     * 引荐来源
     */
    private String refferralBy;
    /**
     * 小计:账单金额
     */
    private BigDecimal subTotal;
    /**
     * 订单要支付的金额=小计+税金-折扣
     */
    private BigDecimal totalAmount;
    /**
     * 支付折扣最终金额
     */
    private BigDecimal discountPrice;
    /**
     * 税金最终金额
     */
    private BigDecimal taxPrice;

    private List<InvoiceItem> items;

    @Data
    public static class InvoiceItem {
        /**
         * 项目名称
         */
        private String category;
        /**
         * 描述
         */
        private String description;
        /**
         * 数量
         */
        private BigDecimal quantity;
        /**
         * 单价
         */
        private BigDecimal unitAmount;
        /**
         * 总价
         */
        private BigDecimal amount;
    }
}
