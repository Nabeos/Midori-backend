package com.midorimart.managementsystem.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.order.CustomerOrderDTOResponse;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;
import com.midorimart.managementsystem.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get Order Detail API by order number")
    @GetMapping("/v1/order-management/{order-number}")
    public Map<String, CustomerOrderDTOResponse> getOrderDetail(@PathVariable(name = "order-number") String orderNumber){
        return orderService.getOrderDetail(orderNumber);
    }

    @Operation(summary = "Add new order")
    @PostMapping("/payment-management/finishOrder")
    public Map<String, OrderDTOResponse> addNewCart(@RequestBody Map<String, OrderDTOPlace> addNewCartMap) {
        return orderService.addNewOrder(addNewCartMap);
    }

    @Operation(summary = "Update Status For Seller")
    @PutMapping("/v1/order-management/{order-number}")
    public Map<String, OrderDTOResponse> updateStatus(@PathVariable(name = "order-number") String orderNumber,
            @RequestParam(name = "status", defaultValue = "0", required = false) int status)
            throws CustomBadRequestException, UnsupportedEncodingException, MessagingException {
        return orderService.updateStatus(orderNumber, status);
    }

    @Operation(summary = "Update Status for customer (WIP)")
    @PutMapping("v1/payment-management/user/purchases/{order-number}")
    public Map<String, OrderDTOResponse> updateStatusForCustomer(
            @PathVariable(name = "order-number") String orderNumber,@RequestParam(name = "status", required = false, defaultValue = "8") int status) {
        return orderService.updateStatusForCustomer(orderNumber);
    }
}
