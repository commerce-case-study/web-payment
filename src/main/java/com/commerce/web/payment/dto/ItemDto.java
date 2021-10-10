package com.commerce.web.payment.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemDto {

    private Long id;
    
    private String name;
    
    private BigDecimal price;
    
    private Integer quantity;
}
