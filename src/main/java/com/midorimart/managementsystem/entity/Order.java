package com.midorimart.managementsystem.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name="[Order]")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Order {
    public static final int STATUS_NEW_ORDER = 0;
    public static final int STATUS_IN_PROGRESS = 1;
    public static final int STATUS_SHIPPING = 2;
    public static final int STATUS_SUCCESS = 3;
    public static final int STATUS_CANCEL = 4;
    public static final int STATUS_REFUND = 5;
    public static final int STATUS_ALL = 7;
    public static final int STATUS_REJECT = 6;
    public static final int STATUS_PENDING = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "order_number", unique = true)
    private String orderNumber;
    private String note;
    @Column(name = "order_date")
    private Date orderDate;

    private int status;
    private String address;

    @OneToMany (mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderDetail> cart;

    @Column(name="full_name")
    private String fullName;

    private String email;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="receive_products_method")
    private int receiveProductsMethod;
    @Column(name="delivery_date")
    private String deliveryDate;
    @Column(name="delivery_time_range")
    private String deliveryTimeRange;
    @Column(name="payment_method")
    private int paymentMethod;


    @Column(name="total_money")
    private float totalMoney;

    public List<String> getAddressField(){
        return Arrays.asList(this.address.split(";"));
    }

    public void setAddress(List<String> addressField){
        StringBuilder str=new StringBuilder();
        for (String field:addressField){
            str.append(field).append(";");
        }
        this.address=str.substring(0,str.length()-1).toString();
    }
}
