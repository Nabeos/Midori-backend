package com.midorimart.managementsystem.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(unique = true)
    private String title;
    private double price;
    private double amount;
    private int discount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToStringExclude
    private List<Gallery> galleries;
    private String description;
    private String status;
    private Date created_at;
    private Date updated_at;
    private int deleted;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @Column(name = "slug", unique = true)
    private String slug;
    @Column(name = "sku",unique = true)
    private String sku;

    @ManyToOne
    @JoinColumn(name = "product_unit_id")
    private ProductUnit unit;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "product")
    private List<ProductQuantity> productQuantities;
}
