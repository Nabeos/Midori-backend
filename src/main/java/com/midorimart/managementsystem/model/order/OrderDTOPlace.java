package com.midorimart.managementsystem.model.order;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.midorimart.managementsystem.annotation.PhoneNumberValid;
import com.midorimart.managementsystem.model.address.dto.AddressDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTOPlace {
    @NotEmpty(message = "Thiếu tên")
    private String fullName;
    @Email(message = "Email không hợp lệ")
    private String email;
    @PhoneNumberValid
    private String phoneNumber;
    @NotNull(message = "Thiếu phương thức giao dịch")
    private int receiveProductsMethod;
    @NotNull(message = "Thiếu địa chỉ")
    private AddressDTOResponse address;
    private List<OrderDetailDTOPlace> cart;
    @NotNull(message = "Thiếu phương thức thanh toán")
    private int paymentMethod;
    @NotEmpty(message = "Thiếu ngày giao")
    private String deliveryDate;
    @NotEmpty(message = "Thiếu giờ giao")
    private String deliveryTimeRange;
    private String note;
    private float totalBill;
}
