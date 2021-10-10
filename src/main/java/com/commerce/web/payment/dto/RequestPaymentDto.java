package com.commerce.web.payment.dto;

import lombok.Data;

@Data
public class RequestPaymentDto {

    private String orderCode;
    
    private String paymentType;
    
}
