package com.midorimart.managementsystem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "vnp_Amount")
  private String vnp_Amount;
  @Column(name = "vnp_BankCode")
  private String vnp_BankCode;
  private double amount;
  @Column(name = "vnp_CardHolder")
  private String vnp_CardHolder;
  @Column(name = "vnp_CardNumber")
  private String vnp_CardNumber;
  @Column(name = "vnp_FeeAmount")
  private String vnp_FeeAmount;
  @Column(name = "vnp_Message")
  private String vnp_Message;
  @Column(name = "vnp_Issuer")
  private String vnp_Issuer;
  @Column(name = "vnp_OrderInfo")
  private String vnp_OrderInfo;
  @Column(name = "vnp_PayDate")
  private Date vnp_PayDate;
  @Column(name = "vnp_ResponseCode")
  private String vnp_ResponseCode;
  @Column(name = "vnp_TmnCode")
  private String vnp_TmnCode;
  @Column(name = "vnp_Trace")
  private String vnp_Trace;
  @Column(name = "vnp_TransactionNo")
  private String vnp_TransactionNo;
  @Column(name = "vnp_TransactionStatus")
  private String vnp_TransactionStatus;
  @Column(name = "vnp_TransactionType")
  private String vnp_TransactionType;
  @Column(name = "vnp_TxnRef")
  private String vnp_TxnRef;
  @Column(name = "vnp_SecureHash")
  private String vnp_SecureHash;
}
