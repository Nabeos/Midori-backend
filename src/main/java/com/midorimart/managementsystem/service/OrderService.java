package com.midorimart.managementsystem.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.midorimart.managementsystem.entity.Invoice;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.order.OrderDTOFilter;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;

public interface OrderService {

    public Map<String, OrderDTOResponse> addNewOrder(Map<String, OrderDTOPlace> OrderDTOPlacemap) throws CustomBadRequestException;

    public Map<String, OrderDTOResponse> updateStatus(String orderNumber, int status) throws CustomBadRequestException, UnsupportedEncodingException, MessagingException;

    public Map<String, OrderDTOResponse> updateStatusForCustomer(String orderNumber) throws CustomBadRequestException;

    // public Map<String, CustomerOrderDTOResponse> getOrderDetail(String orderNumber);

    // public List<Invoice> getInvoiceByUser(int userId);

    public Map<String, List<OrderDTOResponse>> getOrderListForSeller(OrderDTOFilter filter);

    public Map<String, List<OrderDTOResponse>> getOrderListForCustomer(OrderDTOFilter filter);

}
