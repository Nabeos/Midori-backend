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
import com.midorimart.managementsystem.entity.Role;
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
                .fullName(orderDTOPlace.getFullName())
                .email(orderDTOPlace.getEmail())
                .phoneNumber(orderDTOPlace.getPhoneNumber())
                .status(Order.STATUS_NEW_ORDER_OR_PENDING)
                .cart(toOrderDetailDTOList(orderDTOPlace.getCart()))
                .paymentMethod(orderDTOPlace.getPaymentMethod())
                .deliveryDate(orderDTOPlace.getDeliveryDate())
                .deliveryTimeRange(orderDTOPlace.getDeliveryTimeRange())
                .totalMoney(orderDTOPlace.getTotalBill())
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
                .discount(orderDetailDTOPlace.getDiscount())
                .price(orderDetailDTOPlace.getPrice())
                .totalMoney(orderDetailDTOPlace.getTotalPrice())
                .build();
    }

    public static OrderDTOResponse toOrderDTOResponse(Order order, int id) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String deliveryDate = order.getDeliveryDate().substring(0, 10);
        String day = deliveryDate.split("-")[2];
        String month = deliveryDate.split("-")[1];
        String year = deliveryDate.split("-")[0];
        return OrderDTOResponse.builder()
                .id(order.getId())
                .totalBill(order.getTotalMoney())
                .orderNumber(order.getOrderNumber())
                .orderDate(format.format(order.getOrderDate()))
                .deliveryDate(day + "-" + month + "-" + year)
                .deliveryTimeRange(order.getDeliveryTimeRange())
                .fullName(order.getFullName())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddressField())
                .notes(order.getNote())
                .receiveProductsMethod(getReceiveMethod(order.getReceiveProductsMethod()))
                .status(getStatus(order.getStatus(), id))
                .paymentMethod(getPaymentMethod(order.getPaymentMethod()))
                .orderDetail(toListOrderDetailDTOResponse(order.getCart()))
                .orderCode(order.getOrderCode())
                .build();
    }

    private static String getPaymentMethod(int paymentMethod) {
        switch (paymentMethod) {
            case 0:
                return "Thanh toán tại cửa hàng";
            case 1:
                return "Đang Chờ Thanh toán Online";
            case 2:
                return "Đã Thanh toán online Thành Công";
            default:
                return "Error";
        }
    }

    public static String getOrderNumber() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static String getStatus(int status, int id) {

        switch (status) {
            case Order.STATUS_NEW_ORDER_OR_PENDING:
                if (id == Role.SHOPKEEPER)
                    return "Đơn Hàng Mới";
                else
                    return "Đang Chờ Xác Nhận";
            case 1:
                return "Đang Xử Lý";
            case 2:
                return "Đang Giao Hàng";
            case 3:
                return "Thành Công";
            case 4:
                return "Từ Chối";
            case 5:
                return "Hoàn Tiền";
            case 6:
                return "Hủy Bỏ";
            default:
                return "ERROR";

        }
    }

    public static String getReceiveMethod(int receive) {
        switch (receive) {
            case 1:
                return "Giao Tận Nhà";
            default:
                return "Nhận Tại Cửa Hàng";
        }
    }

    // public static CustomerOrderDTOResponse toCustomerOrderDTOResponse(Order
    // order) {
    // DateFormat format = new SimpleDateFormat("hh:mm dd-mm-yyyy");
    // return CustomerOrderDTOResponse.builder()
    // .orderNumber(order.getOrderNumber())
    // .fullName(order.getFullName())
    // .phoneNumber(order.getPhoneNumber())
    // // .address(order.getAddress())
    // .notes(order.getNote())
    // .receiveProductsMethod(getReceiveMethod(order.getReceiveProductsMethod()))
    // .orderStatus(getStatus(order.getStatus()))
    // .createdDate(format.format(order.getOrderDate()))
    // .receiveDateTime(order.getDeliveryTimeRange() + " " +
    // order.getDeliveryDate())
    // .build();
    // }

    public static OrderDetailDTOResponse toOrderDetailDTOResponse(OrderDetail orderDetails) {
        String thumbnails = null;
        if (orderDetails.getProduct().getGalleries() != null
                && orderDetails.getProduct().getGalleries().isEmpty() == false) {
            thumbnails = orderDetails.getProduct().getGalleries().get(0).getThumbnail().replace("\\", "/");
        }
        return OrderDetailDTOResponse.builder().sku(orderDetails.getProduct().getSku())
                .productName(orderDetails.getProduct().getTitle())
                .thumbnails(thumbnails)
                .discount(orderDetails.getDiscount())
                .price(orderDetails.getPrice())
                .totalPrice(orderDetails.getTotalMoney())
                .quantity(orderDetails.getQuantity())
                .build();
    }

    public static List<OrderDetailDTOResponse> toListOrderDetailDTOResponse(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(OrderMapper::toOrderDetailDTOResponse).collect(Collectors.toList());
    }

    public static List<OrderDTOResponse> toOrderDTOList(List<Order> orders, int roleId) {
        return orders.stream().map(order -> toOrderDTOResponse(order, roleId)).collect(Collectors.toList());
    }

}
