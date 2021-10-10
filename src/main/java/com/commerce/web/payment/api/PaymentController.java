package com.commerce.web.payment.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.lib.JsonConverterUtil;
import com.commerce.web.payment.config.annotation.NeedLogin;
import com.commerce.web.payment.dto.MemberDetail;
import com.commerce.web.payment.dto.NotificationPaymentDto;
import com.commerce.web.payment.dto.OrderDto;
import com.commerce.web.payment.dto.OrderPaymentDto;
import com.commerce.web.payment.dto.RequestPaymentDto;
import com.commerce.web.payment.enums.PaymentStatus;
import com.commerce.web.payment.service.TradeService;

@RestController
@RequestMapping("/api/microsite/payment")
public class PaymentController {

    @Autowired
    TradeService tradeService;
    
    /**
     * Generate Payment Code
     * @return
     */
    private String generatePaymentCode() {
        return "PYM"+System.currentTimeMillis();
    }
    
    @NeedLogin
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<String> createPayment(MemberDetail memberDetail, @RequestBody RequestPaymentDto requestPayment) {
        
        // 1. Find Order Data
        OrderDto orderDto = tradeService.findOrderByOrderCode(requestPayment.getOrderCode());

        // 4. Create Order Payment
        OrderPaymentDto orderPayment = OrderPaymentDto.builder()
                .orderId(orderDto.getId())
                .paymentCode(generatePaymentCode())
                .status(PaymentStatus.CREATED.toString())
                .amount(orderDto.getTotalPayment())
                .build();
        tradeService.createOrderPayment(orderPayment);
        
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("payment_code"  , orderPayment.getPaymentCode());
        maps.put("payment_url"   , "https://please-pay-your-order.here/payment/"+orderPayment.getPaymentCode());
        
        // Show the response
        return new ResponseEntity<String>(
                JsonConverterUtil.convertObjectToJson(maps), 
                HttpStatus.OK);
    }
    
    @PostMapping(value = "/notification", consumes = "application/json")
    public ResponseEntity<String> paymentNotification(@RequestBody NotificationPaymentDto notificationPayment) {
        
        OrderPaymentDto orderPayment = tradeService.findOrderPaymentByPaymentCode(notificationPayment.getPaymentCode());
        if(null == orderPayment) {
            // Show the response
            return new ResponseEntity<String>(
                    JsonConverterUtil.convertObjectToJson("Failed"), 
                    HttpStatus.BAD_REQUEST);
        }
        tradeService.updateOrderPaymentStatus(orderPayment.getPaymentCode(), notificationPayment.getStatus());
        
        // Show the response
        return new ResponseEntity<String>(
                JsonConverterUtil.convertObjectToJson("Ok"), 
                HttpStatus.OK);
    }
}
