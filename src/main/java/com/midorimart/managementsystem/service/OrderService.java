package com.midorimart.managementsystem.service;

import java.util.Map;

import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;

public interface OrderService {

    public Map<String, OrderDTOResponse> addNewOrder(Map<String, OrderDTOPlace> OrderDTOPlacemap);
    
    
}
