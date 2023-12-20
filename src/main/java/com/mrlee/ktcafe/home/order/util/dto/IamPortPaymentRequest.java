package com.mrlee.ktcafe.home.order.util.dto;

import lombok.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IamPortPaymentRequest {

    private String merchant_uid;
    private int amount;
    private String card_number;
    private String expiry;
    private String birth;
    private String pwd_2digit;

    public IamPortPaymentRequest(int amount) {
        this.merchant_uid = "kt1234";
        this.card_number = "0000-0000-0000-0000";
        this.expiry = "12/23";
        this.birth = "900520";
        this.pwd_2digit = "00";
        this.amount = amount;
    }

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("merchant_uid", merchant_uid);
        multiValueMap.add("amount", String.valueOf(amount));
        multiValueMap.add("card_number", card_number);
        multiValueMap.add("expiry", expiry);
        multiValueMap.add("birth", birth);
        multiValueMap.add("pwd_2digit", pwd_2digit);
        multiValueMap.add("pg", "nice");
        return multiValueMap;
    }
}
