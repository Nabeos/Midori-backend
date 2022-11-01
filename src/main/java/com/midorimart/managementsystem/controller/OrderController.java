package com.midorimart.managementsystem.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;
import com.midorimart.managementsystem.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/paymentmanagement")
@CrossOrigin
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;
    
    @Operation(summary = "add new order")
    @PostMapping("/finishOrder")
    public Map<String, OrderDTOResponse> addNewCart(@RequestBody Map<String, OrderDTOPlace> addNewCartMap){
        return orderService.addNewOrder(addNewCartMap);
    
    }


}
