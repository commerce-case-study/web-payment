package com.commerce.web.payment.dto;

import lombok.Data;

@Data
public class NotificationPaymentDto {

    private String paymentCode;
    
    private String status;
}
