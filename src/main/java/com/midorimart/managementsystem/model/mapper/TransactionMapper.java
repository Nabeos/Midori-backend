package com.midorimart.managementsystem.model.mapper;

import com.midorimart.managementsystem.entity.Payment;
import com.midorimart.managementsystem.model.payment.TransactionDTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionMapper {
  public static TransactionDTO toTransactionDTO(Payment payment) {
    return TransactionDTO.builder()
    .order(payment.getOrder().getOrderNumber())
    .vnp_Amount(payment.getVnp_Amount())
    .vnp_BankCode(payment.getVnp_BankCode())
    .vnp_BankTranNo(payment.getVnp_BankTranNo())
    .vnp_CardType(payment.getVnp_CardType())
    .vnp_OrderInfo(payment.getVnp_OrderInfo())
    .vnp_PayDate(payment.getVnp_PayDate())
    .vnp_ResponseCode(payment.getVnp_ResponseCode())
    .vnp_TransactionNo(payment.getVnp_TransactionNo())
    .build();
  }
}
