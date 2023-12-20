package com.mrlee.ktcafe.home.order.util;

import lombok.extern.slf4j.Slf4j;
import com.mrlee.ktcafe.home.order.util.dto.IamPortPaymentRequest;
import com.mrlee.ktcafe.home.order.util.dto.PaymentAnnotationResponse;
import com.mrlee.ktcafe.home.order.util.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class IamPortUtil {

    static private String impKey;
    static private String impSecret;

    static private TokenResponse getImportToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("imp_key", "6156780614658570");
        formData.add("imp_secret", "Ea3X5R1QWREAKQ4zyrJBlgunVEz75XG6wGXh1a1An1C3l6bnpfV8S2he27USIcyAJkJ5gylOBm9Y7RLT");

        TokenResponse tokenResponse = WebClient.create().post().uri("https://api.iamport.kr/users/getToken")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new IllegalArgumentException("아임포트 400 에러")))
                .bodyToMono(TokenResponse.class)
                .block();

        tokenResponse.validateResponse();
        return tokenResponse;
    }

    static public void applyForPayment(IamPortPaymentRequest iamPortPaymentRequest) {

        MultiValueMap<String, String> multiValueMap = iamPortPaymentRequest.toMultiValueMap();

        PaymentAnnotationResponse paymentAnnotationResponse = WebClient.create().post().uri("https://api.iamport.kr/subscribe/payments/onetime")
                .header("Authorization", getImportToken().getResponse().getAccess_token())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(multiValueMap))
                .retrieve()
                .bodyToMono(PaymentAnnotationResponse.class)
                .block();

        paymentAnnotationResponse.validateResponse();
    }

    static public void cancelPayment() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("merchant_uid", "1");
        formData.add("amount", "0"); // 0으로 설정시 전액 환불 요청

        WebClient.create().post().uri("https://api.iamport.kr/subscribe/payments/cancel")
                .header("Authorization", getImportToken().getResponse().getAccess_token())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(PaymentAnnotationResponse.class)
                .block();
    }

}
