package com.midorimart.managementsystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.Order;
import com.midorimart.managementsystem.entity.OrderDetail;
import com.midorimart.managementsystem.model.mapper.OrderMapper;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;
import com.midorimart.managementsystem.repository.OrderDetailRepository;
import com.midorimart.managementsystem.repository.OrderRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.service.OrderService;
import com.midorimart.managementsystem.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;



    @Override
    public Map<String, OrderDTOResponse> addNewOrder(Map<String, OrderDTOPlace> OrderDTOPlacemap) {
        OrderDTOPlace orderDTOPlace=OrderDTOPlacemap.get("orderinformation");
        //System.out.println("ORDERDTOPLACEHERE");
        Order order= OrderMapper.toOrder(orderDTOPlace);
        order.setAddress(orderDTOPlace.getAddress());
        order=orderRepository.save(order);
        saveOrderDetail(order.getCart(),order);

        return buildDTOResponse(order);
    }

    private Map<String, OrderDTOResponse> buildDTOResponse(Order order) {
        Map<String, OrderDTOResponse> wrapper = new HashMap<>();
        OrderDTOResponse orderDTOResponse = OrderMapper.toOrderDTOResponse(order);
        wrapper.put("order_response", orderDTOResponse);
        return wrapper;
    }

    public void saveOrderDetail(List<OrderDetail> orderDetailList , Order order){
        for (OrderDetail od : orderDetailList){
            od.setOrder(order);
            orderDetailRepository.save(od);

        }
    }
    
}
