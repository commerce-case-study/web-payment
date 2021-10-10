package com.commerce.web.payment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.commerce.web.payment.dto.OrderDeliveryDto;
import com.commerce.web.payment.dto.OrderDto;
import com.commerce.web.payment.dto.OrderItemDto;
import com.commerce.web.payment.dto.OrderPaymentDto;
import com.commerce.web.payment.dto.ShopDto;

@FeignClient(name = TradeService.SERVICE_NAME)
public interface TradeService {

    public static final String SERVICE_NAME = "service-trade";
    
    @PostMapping(value = "createOrder", consumes = "application/json")
    public Long createOrder(@RequestBody OrderDto order);
    
    @GetMapping(value = "updateOrderStatus/{orderId}/{status}", produces = "application/json")
    public Boolean updateOrderStatus(@PathVariable("orderId") Long orderId, @PathVariable("status") String status);
    
    @PostMapping(value = "createOrderItem", consumes = "application/json")
    public Long createOrderItem(@RequestBody OrderItemDto orderItem);
    
    @PostMapping(value = "createOrderPayment", consumes = "application/json")
    public Long createOrderPayment(@RequestBody OrderPaymentDto orderPayment);
    
    @PostMapping(value = "createOrderDelivery", consumes = "application/json")
    public Long createOrderDelivery(@RequestBody OrderDeliveryDto orderDelivery);
    
    @GetMapping(value = "findShopById/{shopId}", produces = "application/json")
    public ShopDto findShopById(@PathVariable("shopId") Long id);
    
    @GetMapping(value = "findOrderByOrderCode/{orderCode}", produces = "application/json")
    public OrderDto findOrderByOrderCode(@PathVariable("orderCode") String orderCode);
    
    @GetMapping(value = "findOrderPaymentByPaymentCode/{paymentCode}", produces = "application/json")
    public OrderPaymentDto findOrderPaymentByPaymentCode(@PathVariable("paymentCode") String paymentCode);

    @GetMapping(value = "updateOrderPaymentStatus/{paymentCode}/{status}", produces = "application/json")
    public Boolean updateOrderPaymentStatus(@PathVariable("paymentCode") String paymentCode, @PathVariable("status") String status);

}
