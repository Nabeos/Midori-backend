package com.midorimart.managementsystem.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
  private String order;
  private String vnp_Amount;
  private String vnp_BankCode;
  private String vnp_CardType;
  private String vnp_BankTranNo;
  private String vnp_OrderInfo;
  private String vnp_PayDate;
  private String vnp_ResponseCode;
  private String vnp_TransactionNo;
}
