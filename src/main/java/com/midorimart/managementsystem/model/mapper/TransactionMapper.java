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
        .vnp_ResponseCode(getResponse(payment.getVnp_ResponseCode()))
        .vnp_TransactionNo(payment.getVnp_TransactionNo())
        .build();
  }

  private static String getResponse(String vnp_ResponseCode) {
    switch (vnp_ResponseCode) {
      case "00":
        return "Giao dịch thành công";
      case "24":
        return "Giao dịch không thành công do: Khách hàng hủy giao dịch";
      case "11":
        return "Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.";
      case "51":
        return "Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.";
      case "65":
        return "Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.";
      default:
        return "ERROR";
    }
  }
}
