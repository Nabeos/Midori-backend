package com.midorimart.managementsystem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product_Quantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuantity {
    @Id
    @GeneratedValue
    private int id;

    private int quantity;
    @Column(name = "expiry_date")
    private int expiryDate;
    @Column(name = "updated_at")
    private Date updatedDate;
    @Column(name = "created_at")
    private Date createdDate;
    @Column(name = "manufacturing_date")
    private Date manufacturingDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
