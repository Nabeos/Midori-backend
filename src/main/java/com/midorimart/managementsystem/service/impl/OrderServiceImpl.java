package com.midorimart.managementsystem.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

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
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.repository.UserRepository;
import com.midorimart.managementsystem.repository.custom.OrderCriteria;
import com.midorimart.managementsystem.service.EmailService;
import com.midorimart.managementsystem.service.OrderService;
import com.midorimart.managementsystem.service.ProductService;
import com.midorimart.managementsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final EmailService emailService;
    private final InvoiceRepository invoiceRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderCriteria orderCriteria;

    @Override
    public Map<String, OrderDTOResponse> addNewOrder(Map<String, OrderDTOPlace> OrderDTOPlacemap) {
        OrderDTOPlace orderDTOPlace = OrderDTOPlacemap.get("orderinformation");
        Order order = OrderMapper.toOrder(orderDTOPlace);
        order.setAddress(orderDTOPlace.getAddress());
        order = orderRepository.save(order);
        saveOrderDetail(order.getCart(), order);
        if (userService.getUserLogin() != null)
            saveInvoiceForUser(userService.getUserLogin(), order);

        return buildDTOResponse(order);
    }

    private void saveInvoiceForUser(User userLogin, Order order) {
        Invoice invoice = new Invoice();
        invoice.setUser(userLogin);
        invoice.setOrder(order);
        invoice = invoiceRepository.save(invoice);
        saveUserProductStatus(invoice.getOrder().getCart(), userLogin);
    }

    public void saveOrderDetail(List<OrderDetail> orderDetailList, Order order) {
        for (OrderDetail od : orderDetailList) {
            od.setOrder(order);
            orderDetailRepository.save(od);
        }
    }

    @Override
    public Map<String, OrderDTOResponse> updateStatus(String orderNumber, int status)
            throws CustomBadRequestException, UnsupportedEncodingException, MessagingException {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        // change status to reject or accept
        if (status == Order.STATUS_CANCEL_OR_REJECT || status == Order.STATUS_IN_PROGRESS) {
            order.setStatus(status);
            order = orderRepository.save(order);
            return buildDTOResponse(order);
        }
        // update status
        else if (order.getStatus() < Order.STATUS_SUCCESS) {
            order.setStatus(order.getStatus() + 1);
            order = orderRepository.save(order);
            // send email if success
            if (order.getStatus() == Order.STATUS_SUCCESS) {
                String sendEmailStatus = emailService.sendSuccessfulOrderNotice(order);
                System.out.println(sendEmailStatus);
            }
            return buildDTOResponse(order);
        }
        throw new CustomBadRequestException(
                CustomError.builder().code("400").message("You can not update status anymore").build());
    }

    @Override
    public Map<String, OrderDTOResponse> updateStatusForCustomer(String orderNumber) {
        // TODO Auto-generated method stub
        return null;
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

    // @Override
    // public Map<String, CustomerOrderDTOResponse> getOrderDetail(String
    // orderNumber) {
    // Order order = orderRepository.findByOrderNumber(orderNumber);
    // CustomerOrderDTOResponse customerOrderDTOResponse =
    // setOrderDetailForEachProduct(order);
    // Map<String, CustomerOrderDTOResponse> wrapper = new HashMap<>();
    // wrapper.put("customerOrderDetailInformation", customerOrderDTOResponse);
    // return wrapper;
    // }

    // //Get Order Detail For Each Product
    // private CustomerOrderDTOResponse setOrderDetailForEachProduct(Order order) {
    // List<OrderDetail> orderDetails =
    // orderDetailRepository.findByOrderId(order.getId());
    // CustomerOrderDTOResponse customerOrderDTOResponse =
    // OrderMapper.toCustomerOrderDTOResponse(order);
    // Map<String, List<OrderDetailDTOResponse>> cart = new HashMap<>();
    // cart.put("productItem",
    // OrderMapper.toListOrderDetailDTOResponse(orderDetails));
    // customerOrderDTOResponse.setCart(cart);
    // return customerOrderDTOResponse;
    // }

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
