package com.midorimart.managementsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Payment")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "user_id")
  private String user;
  @OneToOne
  @JoinColumn(name = "order_id")
  private Order order;
  @Column(name = "vnp_Amount")
  private String vnp_Amount;
  @Column(name = "vnp_BankCode")
  private String vnp_BankCode;
  @Column(name = "vnp_CardType")
  private String vnp_CardType;
  @Column(name = "vnp_BankTranNo")
  private String vnp_BankTranNo;
  @Column(name = "vnp_OrderInfo")
  private String vnp_OrderInfo;
  @Column(name = "vnp_PayDate")
  private String vnp_PayDate;
  @Column(name = "vnp_ResponseCode")
  private String vnp_ResponseCode;
  @Column(name = "vnp_TmnCode")
  private String vnp_TmnCode;
  @Column(name = "vnp_TransactionNo")
  private String vnp_TransactionNo;
  @Column(name = "vnp_TxnRef")
  private String vnp_TxnRef;
  @Column(name = "vnp_SecureHash")
  private String vnp_SecureHash;
  @Column(name = "disabled")
  private int disabled;
}
