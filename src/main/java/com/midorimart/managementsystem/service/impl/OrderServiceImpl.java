package com.midorimart.managementsystem.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midorimart.managementsystem.entity.DeliveryNote;
import com.midorimart.managementsystem.entity.Invoice;
import com.midorimart.managementsystem.entity.Order;
import com.midorimart.managementsystem.entity.OrderDetail;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.ProductQuantity;
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.CustomError;
import com.midorimart.managementsystem.model.mapper.OrderMapper;
import com.midorimart.managementsystem.model.order.OrderDTOFilter;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;
import com.midorimart.managementsystem.repository.DeliveryNoteRepository;
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
import net.bytebuddy.utility.RandomString;

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
    private final DeliveryNoteRepository deliveryNoteRepository;

    @Override
    @Transactional(rollbackFor = { StaleObjectStateException.class, SQLException.class,
            ObjectOptimisticLockingFailureException.class, CustomBadRequestException.class })
    public Map<String, OrderDTOResponse> addNewOrder(OrderDTOPlace orderDTOPlace)
            throws CustomBadRequestException, UnsupportedEncodingException, MessagingException {
        // turn address into string to save in db
        if (orderDTOPlace.getCart() == null || orderDTOPlace.getCart().isEmpty()) {
            throw new CustomBadRequestException(CustomError.builder().code("400").message("Chưa có sản phẩm").build());
        }
        List<String> address = new ArrayList<>();
        address.add(orderDTOPlace.getAddress().getProvinceId());
        address.add(orderDTOPlace.getAddress().getDistrictId());
        address.add(orderDTOPlace.getAddress().getWardId());
        address.add(orderDTOPlace.getAddress().getAddressDetail());
        Order order = OrderMapper.toOrder(orderDTOPlace);
        order.setOrderCode(RandomString.make(10) + order.getOrderNumber() + RandomString.make(10));
        order.setAddress(address);
        // Check quantity before payment
        if (CheckQuantity(order.getCart())) {
            order = orderRepository.save(order);
            saveOrderDetail(order.getCart(), order);
            if(orderDTOPlace.getPaymentMethod() == 1){
                emailService.sendPlaceOrderNotice(order);
            }
        } else {
            throw new CustomBadRequestException(CustomError.builder().code("400").message("Hết Hàng").build());
        }
        // check if customer or guest
        if (userService.getUserLogin() != null)
            saveInvoiceForUser(userService.getUserLogin(), order);

        return buildDTOResponse(order);
    }

    public void saveOrderDetail(List<OrderDetail> orderDetailList, Order order) {
        for (OrderDetail od : orderDetailList) {
            od.setOrder(order);
            orderDetailRepository.save(od);
        }
    }

    // update status for seller (accept, reject, shipping, successful)
    @Override
    public Map<String, OrderDTOResponse> updateStatus(String orderNumber, int status)
            throws CustomBadRequestException, UnsupportedEncodingException, MessagingException {
        Optional<Order> orderOptional = orderRepository.findByOrderNumber(orderNumber);
        if (!orderOptional.isPresent()) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("wrong order number").build());
        }
        Order order = orderOptional.get();

        // change status to reject or accept
        if (status == Order.STATUS_REJECT || status == Order.STATUS_IN_PROGRESS && order.getStatus() == 0) {
            order.setStatus(status);
            order = orderRepository.save(order);
            // Send email for customer if order is accepted
            if (order.getStatus() == Order.STATUS_IN_PROGRESS) {
                emailService.sendAcceptedEmail(order);
                DeliveryNote deliveryNote = createdDeliveryNote(order);
                return buildDTOResponse(order);
            }

            // Send email for customer if order is rejected
            if (order.getStatus() == Order.STATUS_REJECT) {
                refillProductQuantityList(order.getCart());
                emailService.sendRejectedEmail(order);
                return buildDTOResponse(order);
            }
        }
        else if((status == Order.STATUS_REJECT || status == Order.STATUS_IN_PROGRESS) && (order.getStatus() == 1 || order.getStatus() == 4)){
            throw new CustomBadRequestException(
                CustomError.builder().code("400").message("Đã được cập nhật hoặc xử lý bởi người khác").build());
        }
        // update status
        else if (order.getStatus() == Order.STATUS_IN_PROGRESS && order.getReceiveProductsMethod() != 1) {
            order.setStatus(Order.STATUS_SUCCESS);
            order = orderRepository.save(order);
            // send email if success
            if (order.getStatus() == Order.STATUS_SUCCESS) {
                emailService.sendSuccessfulOrderNotice(order);
            }
            return buildDTOResponse(order);
        } else if (order.getStatus() < Order.STATUS_SUCCESS) {
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

    // Update status for Customer (refund, cancel)
    @Override
    public Map<String, OrderDTOResponse> updateStatusForCustomer(String orderNumbers, String code)
            throws CustomBadRequestException {
        Optional<Order> orderOptional = orderRepository.findByOrderNumber(orderNumbers);
        if (!orderOptional.isPresent()) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("wrong order number").build());
        }
        Order order = orderOptional.get();
        // Check order belonging
        if (!order.getOrderCode().equals(code)) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("This is not your order").build());
        }
        // Set status
        if (order.getStatus() == Order.STATUS_NEW_ORDER_OR_PENDING) {
            // Cancel order
            order.setStatus(Order.STATUS_CANCEL);
            refillProductQuantityList(order.getCart());
        } else if (order.getStatus() == Order.STATUS_SUCCESS) {
            // Refund
            order.setStatus(Order.STATUS_REFUND);
            updateStatusForDeliveryNote(order);
            refillInventory(order.getCart());
        } else {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Hủy Bỏ Thất Bại do đơn được chấp nhận").build());
        }
        order = orderRepository.save(order);
        return buildDTOResponse(order);
    }

    // Display customer's orders list
    @Override
    public Map<String, List<OrderDTOResponse>> getOrderListForSeller(OrderDTOFilter filter) {
        Map<String, Object> result = orderCriteria.getOrders(filter);
        List<Order> orders = (List<Order>) result.get("totalOrders");
        return toOrderDTOList(orders);
    }

    // Display customer's purchase
    @Override
    public Map<String, List<OrderDTOResponse>> getOrderListForCustomer(OrderDTOFilter filter) {
        Map<String, Object> result = orderCriteria.getOrdersForCustomer(filter, userService.getUserLogin().getId());
        List<Order> orders = (List<Order>) result.get("totalOrders");
        return toOrderDTOList(orders);
    }

    // Create Delivery Note
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

    // Save purchase for Customer after Placing order successfully
    private void saveUserProductStatus(List<OrderDetail> list, User user) {
        for (OrderDetail orderDetail : list) {
            Product product = productRepository.findById(orderDetail.getProduct().getId()).get();
            user.getProducts().add(product);
            user = userRepository.save(user);
        }
    }

    // Return to orderDTO to display to user
    private Map<String, List<OrderDTOResponse>> toOrderDTOList(List<Order> orders) {
        User user = userService.getUserLogin();
        List<OrderDTOResponse> orderDTOResponses = OrderMapper.toOrderDTOList(orders, user.getRole().getId());
        Map<String, List<OrderDTOResponse>> wrapper = new HashMap<>();
        wrapper.put("orders", orderDTOResponses);
        return wrapper;
    }

    // Change status for delivery note when order's status changes
    private void updateStatusForDeliveryNote(Order order) {
        DeliveryNote existed = deliveryNoteRepository.findByOrderId(order.getId());
        existed.setStatus(Order.STATUS_REFUND);
        existed = deliveryNoteRepository.save(existed);
    }

    // Return the quantity of product before Buying
    private void refillProductQuantityList(List<OrderDetail> list) {
        for (OrderDetail product : list) {
            refillProductQuantity(product);
        }
    }

    // Return the quantity of product before Buying in Inventory
    private void refillInventory(List<OrderDetail> cart) {
        for (OrderDetail product : cart) {
            refillQuantityInStock(product);
        }
    }

    // Return quantity for each product in inventory
    private void refillQuantityInStock(OrderDetail od) {
        ProductQuantity existed = productQuantityRepository.findByProductId(od.getProduct().getId());
        existed.setQuantity(existed.getQuantity() + od.getQuantity());
        existed = productQuantityRepository.save(existed);
    }

    // Refill quantity for each product
    private void refillProductQuantity(OrderDetail product) {
        Product p = productRepository.findById(product.getProduct().getId()).get();
        p.setQuantity(p.getQuantity() + product.getQuantity());
        p = productRepository.save(p);
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

    // Check quantity in cart
    private boolean CheckQuantity(List<OrderDetail> cart) {
        for (OrderDetail orderDetail : cart) {
            if (orderDetail.getQuantity() <= 0 || Integer.toString(orderDetail.getQuantity()) == null)
                return false;
            if (!productRepository.findById(orderDetail.getProduct().getId()).isPresent()) {
                return false;
            }
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
}
