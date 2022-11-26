package com.midorimart.managementsystem.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midorimart.managementsystem.entity.DeliveryNote;
import com.midorimart.managementsystem.entity.DeliveryNoteDetail;
import com.midorimart.managementsystem.entity.Invoice;
import com.midorimart.managementsystem.entity.Order;
import com.midorimart.managementsystem.entity.OrderDetail;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.CustomError;
import com.midorimart.managementsystem.model.mapper.OrderMapper;
import com.midorimart.managementsystem.model.order.OrderDTOFilter;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;
import com.midorimart.managementsystem.repository.InvoiceRepository;
import com.midorimart.managementsystem.repository.OrderDetailRepository;
import com.midorimart.managementsystem.repository.OrderRepository;
import com.midorimart.managementsystem.repository.ProductQuantityRepository;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.repository.UserRepository;
import com.midorimart.managementsystem.repository.custom.OrderCriteria;
import com.midorimart.managementsystem.service.EmailService;
import com.midorimart.managementsystem.service.OrderService;
import com.midorimart.managementsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;
    private final InvoiceRepository invoiceRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderCriteria orderCriteria;
    private final ProductQuantityRepository productQuantityRepository;
    private final DeliveryNoteServiceImpl deliveryNoteServiceImpl;

    @Override
    @Transactional(rollbackFor = { StaleObjectStateException.class, SQLException.class,
            ObjectOptimisticLockingFailureException.class })
    public Map<String, OrderDTOResponse> addNewOrder(Map<String, OrderDTOPlace> OrderDTOPlacemap)
            throws CustomBadRequestException {
        OrderDTOPlace orderDTOPlace = OrderDTOPlacemap.get("orderinformation");
        // turn address into string to save in db
        List<String> address = new ArrayList<>();
        address.add(orderDTOPlace.getAddress().getProvinceId());
        address.add(orderDTOPlace.getAddress().getDistrictId());
        address.add(orderDTOPlace.getAddress().getWardId());
        address.add(orderDTOPlace.getAddress().getAddressDetail());
        Order order = OrderMapper.toOrder(orderDTOPlace);
        order.setAddress(address);
        if (CheckQuantity(order.getCart())) {
            order = orderRepository.save(order);
            saveOrderDetail(order.getCart(), order);
        } else {
            throw new CustomBadRequestException(CustomError.builder().code("400").message("run out of stock").build());
        }
        if (userService.getUserLogin() != null)
            saveInvoiceForUser(userService.getUserLogin(), order);

        return buildDTOResponse(order);
    }

    private boolean CheckQuantity(List<OrderDetail> cart) {
        for (OrderDetail orderDetail : cart) {
            int quantityInStock = productRepository.findById(orderDetail.getProduct().getId()).get().getQuantity();
            if (quantityInStock < orderDetail.getQuantity())
                return false;
        }
        return true;
    }

    // Save invoice for user
    private void saveInvoiceForUser(User userLogin, Order order) {
        Invoice invoice = new Invoice();
        invoice.setUser(userLogin);
        invoice.setOrder(order);
        invoice.setStatus(1);
        invoice = invoiceRepository.save(invoice);
        saveUserProductStatus(invoice.getOrder().getCart(), userLogin);
    }

    public void saveOrderDetail(List<OrderDetail> orderDetailList, Order order) {
        for (OrderDetail od : orderDetailList) {
            od.setOrder(order);
            orderDetailRepository.save(od);
        }
    }

    private boolean CheckQuantityInStock(List<OrderDetail> oDetail) {
        for (OrderDetail orderDetail : oDetail) {
            int quantityInStock = productQuantityRepository
                    .findSumOfQuantityByProductId(orderDetail.getProduct().getId());
            if (quantityInStock < orderDetail.getQuantity())
                return false;
        }
        return true;
    }

    // update status for seller
    @Override
    public Map<String, OrderDTOResponse> updateStatus(String orderNumber, int status)
            throws CustomBadRequestException, UnsupportedEncodingException, MessagingException {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        // change status to reject or accept
        if (status == Order.STATUS_REJECT || status == Order.STATUS_IN_PROGRESS && order.getStatus() == 0) {
            order.setStatus(status);
            order = orderRepository.save(order);
            // Send email for customer if order is accepted
            if (order.getStatus() == Order.STATUS_IN_PROGRESS) {
                emailService.sendAcceptedEmail(order);
                DeliveryNote deliveryNote = createdDeliveryNote(order);
            }

            // Send email for customer if order is rejected
            if (order.getStatus() == Order.STATUS_REJECT) {
                emailService.sendRejectedEmail(order);
            }
            return buildDTOResponse(order);
        }
        // update status
        else if (order.getStatus() < Order.STATUS_SUCCESS) {
            order.setStatus(order.getStatus() + 1);
            order = orderRepository.save(order);

            // send email if success
            if (order.getStatus() == Order.STATUS_SUCCESS) {
                emailService.sendSuccessfulOrderNotice(order);
            }
            return buildDTOResponse(order);
        }
        throw new CustomBadRequestException(
                CustomError.builder().code("400").message("You can not update status anymore").build());
    }

    private DeliveryNote createdDeliveryNote(Order order) {
        DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setCreatedAt(new Date());
        deliveryNote.setName("XUAT" + order.getOrderNumber());
        deliveryNote.setNote("Phiếu xuất kho cho đơn hàng " + order.getOrderNumber() + " giao lúc "
                + order.getDeliveryDate().substring(0, 10) + " " + order.getDeliveryTimeRange() + "\n"
                + "Chú thích của Đơn Hàng: " + order.getNote());
        deliveryNote.setUser(userService.getUserLogin());
        deliveryNote.setOrder(order);
        deliveryNote.setStatus(1);
        DeliveryNote newDelivery = deliveryNoteServiceImpl.addNewDeliveryNote(deliveryNote, order.getCart());

        return newDelivery;
    }

    @Override
    public Map<String, OrderDTOResponse> updateStatusForCustomer(String orderNumbers) {
        Order order = orderRepository.findByOrderNumber(orderNumbers);
        if (order.getStatus() == Order.STATUS_NEW_ORDER_OR_PENDING) {
            order.setStatus(Order.STATUS_CANCEL);
        } else if (order.getStatus() == Order.STATUS_SUCCESS) {
            order.setStatus(Order.STATUS_REFUND);
        }
        order = orderRepository.save(order);
        return buildDTOResponse(order);
    }

    private Map<String, OrderDTOResponse> buildDTOResponse(Order order) {
        Map<String, OrderDTOResponse> wrapper = new HashMap<>();
        int roleId = 0;
        if (userService.getUserLogin() != null) {
            roleId = userService.getUserLogin().getRole().getId();
        }
        OrderDTOResponse orderDTOResponse = OrderMapper.toOrderDTOResponse(order, roleId);
        wrapper.put("order_response", orderDTOResponse);
        return wrapper;
    }

    @Override
    public List<Invoice> getInvoiceByUser(int userId) {
        return invoiceRepository.findByUserId(userId) != null ? invoiceRepository.findByUserId(userId) : null;
    }

    @Override
    public Map<String, List<OrderDTOResponse>> getOrderListForSeller(OrderDTOFilter filter) {
        Map<String, Object> result = orderCriteria.getOrders(filter);
        List<Order> orders = (List<Order>) result.get("totalOrders");
        return toOrderDTOList(orders);
    }

    @Override
    public Map<String, List<OrderDTOResponse>> getOrderListForCustomer(OrderDTOFilter filter) {
        Map<String, Object> result = orderCriteria.getOrdersForCustomer(filter, userService.getUserLogin().getId());
        List<Order> orders = (List<Order>) result.get("totalOrders");
        return toOrderDTOList(orders);
    }

    private void saveUserProductStatus(List<OrderDetail> list, User user) {
        for (OrderDetail orderDetail : list) {
            Product product = productRepository.findById(orderDetail.getProduct().getId()).get();
            user.getProducts().add(product);
            user = userRepository.save(user);
        }
    }

    private Map<String, List<OrderDTOResponse>> toOrderDTOList(List<Order> orders) {
        User user = userService.getUserLogin();
        List<OrderDTOResponse> orderDTOResponses = OrderMapper.toOrderDTOList(orders, user.getRole().getId());
        Map<String, List<OrderDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("orders", orderDTOResponses);
        return wrapper;
    }
}
