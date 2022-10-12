package com.midorimart.managementsystem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "order_number", unique = true)
    private String orderNumber;
    private String note;
    @Column(name = "order_date")
    private Date orderDate;

    enum status {
        IN_PROGRESS, CANCEL, COMPLETED
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private float totalMoney;
}
