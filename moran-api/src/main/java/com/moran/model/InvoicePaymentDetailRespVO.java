package com.moran.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoicePaymentDetailRespVO {

    private BigDecimal amount;

    private LocalDateTime payTime;

    private String methodCode;

    private String method;

    private String accountCode;

    private String account;

    private String remark;

    private String file;

    private LocalDateTime createTime;

}