package com.midorimart.managementsystem.model.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.midorimart.managementsystem.entity.Order;
import com.midorimart.managementsystem.entity.OrderDetail;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.model.order.CustomerOrderDTOResponse;
import com.midorimart.managementsystem.model.order.OrderDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDTOResponse;
import com.midorimart.managementsystem.model.order.OrderDetailDTOPlace;
import com.midorimart.managementsystem.model.order.OrderDetailDTOResponse;

public class OrderMapper {

    public static Order toOrder(OrderDTOPlace orderDTOPlace) {
        Date now = new Date();
        return Order.builder()
                .note(orderDTOPlace.getNote())
                .orderDate(now)
                .orderNumber(getOrderNumber())
                .totalMoney(orderDTOPlace.getTotalBill())
                .address("dsadddsa")
                .fullName(orderDTOPlace.getFullName())
                .email(orderDTOPlace.getEmail())
                .phoneNumber(orderDTOPlace.getPhoneNumber())
                .status(Order.STATUS_PENDING)
                .cart(toOrderDetailDTOList(orderDTOPlace.getCart()))
                .paymentMethod(orderDTOPlace.getPaymentMethod())
                .deliveryDate(orderDTOPlace.getDeliveryDate())
                .deliveryTimeRange(orderDTOPlace.getDeliveryTimeRange())
                .receiveProductsMethod(orderDTOPlace.getReceiveProductsMethod())
                .build();

    }

    private static List<OrderDetail> toOrderDetailDTOList(List<OrderDetailDTOPlace> cart) {
        return cart.stream().map(OrderMapper::toOrderDetail).collect(Collectors.toList());
    }

    private static OrderDetail toOrderDetail(OrderDetailDTOPlace orderDetailDTOPlace) {
        return OrderDetail.builder()
                .product(Product.builder().id(orderDetailDTOPlace.getProductId()).build())
                .quantity(orderDetailDTOPlace.getQuantity())
                .price(orderDetailDTOPlace.getPrice())
                .totalMoney(orderDetailDTOPlace.getTotalPrice())
                .build();
    }

    public static OrderDTOResponse toOrderDTOResponse(Order order) {
        return OrderDTOResponse.builder()
                .id(order.getId())
                .totalBill(order.getTotalMoney())
                .orderNumber(order.getOrderNumber())
                .fullName(order.getFullName())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .notes(order.getNote())
                .receiveProductsMethod(getReceiveMethod(order.getReceiveProductsMethod()))
                .status(getStatus(order.getStatus()))
                .orderDetail(toListOrderDetailDTOResponse(order.getCart()))
                .build();
    }



    public static String getOrderNumber() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static String getStatus(int status) {
        switch (status) {
            case Order.STATUS_NEW_ORDER:
                return "NEW ORDER";
            case 1:
                return "IN PROGRESS";
            case 2:
                return "SHIPPING";
            case 3:
                return "SUCCESS";
            case 4:
                return "CANCELLED";
            case 5:
                return "REFUND";
            case 6:
                return "REJECT";
            case 7:
                return "ALL";
            default:
                return "PENDING";

        }
    }

    public static String getReceiveMethod(int receive) {
        switch (receive) {
            case 1:
                return "HOME DELIVERY";
            default:
                return "PICK UP AT SHOP";
        }
    }

    public static CustomerOrderDTOResponse toCustomerOrderDTOResponse(Order order) {
        DateFormat format = new SimpleDateFormat("hh:mm dd-mm-yyyy");
        return CustomerOrderDTOResponse.builder()
                .orderNumber(order.getOrderNumber())
                .fullName(order.getFullName())
                .phoneNumber(order.getPhoneNumber())
                // .address(order.getAddress())
                .notes(order.getNote())
                .receiveProductsMethod(getReceiveMethod(order.getReceiveProductsMethod()))
                .orderStatus(getStatus(order.getStatus()))
                .createdDate(format.format(order.getOrderDate()))
                .receiveDateTime(order.getDeliveryTimeRange()+" "+order.getDeliveryDate())
                .build();
    }

    public static OrderDetailDTOResponse toOrderDetailDTOResponse(OrderDetail orderDetails) {
        return OrderDetailDTOResponse.builder().sku(orderDetails.getProduct().getSku())
                .productName(orderDetails.getProduct().getTitle())
                .thumbnails(orderDetails.getProduct().getGalleries().isEmpty() ? null
                        : orderDetails.getProduct().getGalleries().get(0).toString())
                .total(orderDetails.getTotalMoney())
                .quantity(orderDetails.getQuantity())
                .build();
    }

    public static List<OrderDetailDTOResponse> toListOrderDetailDTOResponse(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(OrderMapper::toOrderDetailDTOResponse).collect(Collectors.toList());
    }

    public static List<OrderDTOResponse> toOrderDTOList(List<Order> orders) {
        return orders.stream().map(OrderMapper::toOrderDTOResponse).collect(Collectors.toList());
    }

}
