package com.mrlee.ktcafe.home.order.util.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public abstract class IamPortResponse {
    private Integer code;
    private String message;

    public IamPortResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void validateResponse() {
        if (code != 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
