package com.commerce.web.payment.dto;

import lombok.Data;

@Data
public class RequestOrderItemDto {

	private Long itemId;
	
	private Integer quantity;
}
