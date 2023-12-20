package com.mrlee.ktcafe.home.order.util.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse extends IamPortResponse {

    private ImportToken response;

    public TokenResponse(Integer code, String message, ImportToken response) {
        super(code, message);
        this.response = response;
    }

    @Getter
    @NoArgsConstructor
    public static class ImportToken {
        private String access_token;
        private Integer expired_at;
        private Integer now;

        public ImportToken(String access_token, Integer expired_at, Integer now) {
            this.access_token = access_token;
            this.expired_at = expired_at;
            this.now = now;
        }
    }
}
