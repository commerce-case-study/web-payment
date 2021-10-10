package com.commerce.web.payment.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

    private Long id;
    
    private Long memberId;
    
    private String orderCode;
    
    private String status;
    
    private BigDecimal shippingFee;
    
    private BigDecimal totalPrice;
    
    private BigDecimal totalDiscount;
    
    private BigDecimal totalPayment;
}
