package com.midorimart.managementsystem.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;

import com.midorimart.managementsystem.entity.Invoice;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.order.CustomerOrderDTOResponse;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;

public interface OrderService {

    public Map<String, OrderDTOResponse> addNewOrder(Map<String, OrderDTOPlace> OrderDTOPlacemap);

    public Map<String, OrderDTOResponse> updateStatus(String orderNumber, int status) throws CustomBadRequestException, UnsupportedEncodingException, MessagingException;

    public Map<String, OrderDTOResponse> updateStatusForCustomer(String orderNumber);

    public Map<String, CustomerOrderDTOResponse> getOrderDetail(String orderNumber);

    public Invoice getInvoiceByUser(int userId);

}
