package com.mrlee.ktcafe.home.order.util.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentAnnotationResponse extends IamPortResponse {

    private String imp_uid;
    private String merchant_uid;
    private String pay_method;
    private Integer amount;
    private Integer cancel_amount;
    private String currency;

    public PaymentAnnotationResponse(Integer code, String message, String imp_uid, String merchant_uid, String pay_method, Integer amount, Integer cancel_amount, String currency) {
        super(code, message);
        this.imp_uid = imp_uid;
        this.merchant_uid = merchant_uid;
        this.pay_method = pay_method;
        this.amount = amount;
        this.cancel_amount = cancel_amount;
        this.currency = currency;
    }
}
