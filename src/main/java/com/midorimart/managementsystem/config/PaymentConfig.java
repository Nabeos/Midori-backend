package com.midorimart.managementsystem.config;

import java.util.Random;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentConfig {
    public static final String vnp_IpAddr = "0:0:0:0:0:0:0:1";
    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String vnp_TmnCode = "3ENL7XVD";
    public static final String vnp_HashSecret = "SEPCMVJRZAXICFBFLRUYYCJGLDOEUDCI";
    public static final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_CurrCode = "VND";
}
