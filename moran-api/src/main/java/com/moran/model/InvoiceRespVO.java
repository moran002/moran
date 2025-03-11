package com.moran.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceRespVO {

    private Long id;

    private String orderType;

    private String orderSn;

    private String invoiceSn;

    private String invoiceLogo;

    private Integer status;

    private String statusName;

    private LocalDateTime issueTime;

    private LocalDateTime dueTime;

    private Integer terms;

    private BigDecimal invoiceSubTotal;

    private BigDecimal invoiceTotalAmount;

    private String companyName;

    private String companyAddress;

    private String companyZip;

    private String companyCity;

    private String companyCountry;

    private String companyEmail;

    private String companyPhone;

    private String companyFax;

    private String memberName;

    private String memberPhone;

    private String memberEmail;

    private String memberId;

    private String param;

    private String raisedBy;

    private String refferralBy;

    private BigDecimal subTotal;

    private BigDecimal totalAmount;

    private BigDecimal paidAmount;

    private BigDecimal notPaidAmount;

    private BigDecimal discountPrice;
    private BigDecimal taxPrice;

    private Integer discount;

    private BigDecimal discountAmount;

    private BigDecimal discountRate;

    private Integer tax;

    private BigDecimal taxRate;

    private BigDecimal taxAmount;

    private String file;
    private LocalDateTime createTime;

    private List<InvoiceItemRespVO> items;

    private List<InvoicePaymentDetailRespVO> payments;
}