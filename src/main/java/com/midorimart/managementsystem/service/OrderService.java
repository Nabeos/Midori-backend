package com.midorimart.managementsystem.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.order.OrderDTOFilter;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;

@Validated
public interface OrderService {

    public Map<String, OrderDTOResponse> addNewOrder(@Valid OrderDTOPlace orderDTOPlace) throws CustomBadRequestException, ConstraintViolationException;

    public Map<String, OrderDTOResponse> updateStatus(String orderNumber, int status) throws CustomBadRequestException, UnsupportedEncodingException, MessagingException;

    public Map<String, OrderDTOResponse> updateStatusForCustomer(String orderNumber, String orderCode) throws CustomBadRequestException;

    // public Map<String, CustomerOrderDTOResponse> getOrderDetail(String orderNumber);

    // public List<Invoice> getInvoiceByUser(int userId);

    public Map<String, List<OrderDTOResponse>> getOrderListForSeller(OrderDTOFilter filter);

    public Map<String, List<OrderDTOResponse>> getOrderListForCustomer(OrderDTOFilter filter);

    public Map<String, OrderDTOResponse> addNewOrderTest(@Valid OrderDTOPlace addNewCartMap);

}
