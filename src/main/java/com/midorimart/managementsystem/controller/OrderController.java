package com.midorimart.managementsystem.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
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
import com.midorimart.managementsystem.model.order.OrderDTOFilter;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;
import com.midorimart.managementsystem.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get All Orders for Seller")
    @GetMapping("/v1/order-management/orders")
    public Map<String, List<OrderDTOResponse>> getOrderListForSeller(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit,
            @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
            @RequestParam(name = "status", defaultValue = "8", required = true) Integer status) {
        OrderDTOFilter filter = OrderDTOFilter.builder().limit(limit).offset(offset).status(status).build();
        return orderService.getOrderListForSeller(filter);
    }

    @Operation(summary = "Get Customer Purchase List")
    @GetMapping("/v1/users/purchase")
    public Map<String, List<OrderDTOResponse>> getOrderListForCustomer(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit,
            @RequestParam(name = "offset", defaultValue = "0", required = false) Integer offset,
            @RequestParam(name = "status", defaultValue = "8", required = true) Integer status) {
        OrderDTOFilter filter = OrderDTOFilter.builder().limit(limit).offset(offset).status(status).build();
        return orderService.getOrderListForCustomer(filter);
    }

    @Operation(summary = "Add new order")
    @PostMapping("/payment-management/finishOrder")
    public Map<String, OrderDTOResponse> addNewCart(@RequestBody Map<String, @Valid OrderDTOPlace> addNewCartMap)
            throws CustomBadRequestException, ConstraintViolationException, HttpMessageNotReadableException {
        return orderService.addNewOrder(addNewCartMap.get("orderinformation"));
    }

    @Operation(summary = "Update Status For Seller")
    @PutMapping("/v1/order-management/{order-number}")
    public Map<String, OrderDTOResponse> updateStatus(@PathVariable(name = "order-number") String orderNumber,
            @RequestParam(name = "status", defaultValue = "8", required = true) Integer status)
            throws CustomBadRequestException, UnsupportedEncodingException, MessagingException {
        return orderService.updateStatus(orderNumber, status);
    }

    @Operation(summary = "Update Status for Customer")
    @PutMapping("/user/purchases/{order-number}")
    public Map<String, OrderDTOResponse> updateStatusForCustomer(
            @PathVariable(name = "order-number") String orderNumber,
            @RequestParam(name = "code", required = true, defaultValue = "abc") String orderCode,
            @RequestParam(name = "status", required = false, defaultValue = "8") int status)
            throws CustomBadRequestException {
        return orderService.updateStatusForCustomer(orderNumber, orderCode);
    }
}
