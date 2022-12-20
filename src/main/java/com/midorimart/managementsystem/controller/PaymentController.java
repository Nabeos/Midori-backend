package com.midorimart.managementsystem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.config.PaymentConfig;
import com.midorimart.managementsystem.model.payment.Payment;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Payment API")
@RequiredArgsConstructor
public class PaymentController {

  @PostMapping("/payment-management")
  public Map<String, Payment> pay(@RequestParam String vnp_OrderInfo,
      @RequestParam String amountStr, HttpServletRequest req, HttpServletResponse resp)
      throws UnsupportedEncodingException {
    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";
    String orderType = "100000";
    String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
    String vnp_IpAddr = PaymentConfig.getIpAddress(req);
    String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

    int amount = Integer.parseInt(amountStr) * 100;
    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(amount));
    vnp_Params.put("vnp_CurrCode", "VND");
    String bank_code = req.getParameter("bankcode");
    if (bank_code != null && !bank_code.isEmpty()) {
      vnp_Params.put("vnp_BankCode", bank_code);
    }
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
    vnp_Params.put("vnp_OrderType", orderType);

    String locate = "vn";
    if (locate != null && !locate.isEmpty()) {
      vnp_Params.put("vnp_Locale", locate);
    } else {
      vnp_Params.put("vnp_Locale", "vn");
    }
    vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_Returnurl);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());

    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    // Add Params of 2.0.1 Version
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
    // Billing
    // vnp_Params.put("vnp_Bill_Mobile", req.getParameter("txt_billing_mobile"));
    // vnp_Params.put("vnp_Bill_Email", req.getParameter("txt_billing_email"));
    // String fullName = (req.getParameter("txt_billing_fullname")).trim();
    // if (fullName != null && !fullName.isEmpty()) {
    // int idx = fullName.indexOf(' ');
    // String firstName = fullName.substring(0, idx);
    // String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
    // vnp_Params.put("vnp_Bill_FirstName", firstName);
    // vnp_Params.put("vnp_Bill_LastName", lastName);

    // }
    // vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
    // vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
    // vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
    // if (req.getParameter("txt_bill_state") != null &&
    // !req.getParameter("txt_bill_state").isEmpty()) {
    // vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
    // }
    // // Invoice
    // vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
    // vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
    // vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
    // vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
    // vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
    // vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
    // vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
    // Build data to hash and querystring
    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        // Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        // Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
    Payment payment = new Payment();
    payment.setCode("00");
    payment.setMessage("success");
    payment.setData(paymentUrl);
    Map<String, Payment> result = new HashMap<>();
    result.put("payment", payment);
    return result;
  }

  @GetMapping("/payment-management")
  public Map<String, String> vnPayQuery(@RequestParam String vnp_TxnRef, @RequestParam String vnp_TransDate,
      HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // vnp_Command = querydr
    String vnp_TmnCode = PaymentConfig.vnp_TmnCode;
    String vnp_IpAddr = PaymentConfig.getIpAddress(req);
    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", "2.1.0");
    vnp_Params.put("vnp_Command", "querydr");
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
    vnp_Params.put("vnp_TransDate", vnp_TransDate);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
    // Build data to hash and querystring
    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        // Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        // Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = PaymentConfig.vnp_apiUrl + "?" + queryUrl;
    URL url = new URL(paymentUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String inputLine;
    StringBuilder response = new StringBuilder();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    String Rsp = response.toString();
    String respDecode = URLDecoder.decode(Rsp, "UTF-8");
    String[] responseData = respDecode.split("&|\\=");
    Map<String, String> result = new HashMap<>();
    result.put("data", Arrays.toString(responseData));
    return result;
  }

  @GetMapping("")
  public void getIPN(@RequestParam String vnp_Amount,
      @RequestParam String vnp_OrderInfo,
      @RequestParam String vnp_ResponseCode,
      @RequestParam String vnp_TmnCode,
      @RequestParam String vnp_TxnRef,
      @RequestParam String vnp_SecureHashType,
      @RequestParam String vnp_SecureHash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    Map vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        // Build hash data
        if (!fieldName.equals("vnp_ReturnUrl") || fieldName.equals("vnp_IpAddr") || !fieldName.equals("vnp_Locale")
            || !fieldName.equals("vnp_OrderType") || !fieldName.equals("vnp_Version")
            || !fieldName.equals("vnp_Command")
            || !fieldName.equals("vnp_CurrCode")) {
          // hashData.append(fieldName);
          // hashData.append('=');
          // hashData.append(URLEncoder.encode(fieldValue,
          // StandardCharsets.US_ASCII.toString()));
          hashData.append(fieldValue);
        }
        // Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String signValue = PaymentConfig.hashAllFields(vnp_Params);
    if (signValue.equals(vnp_SecureHash)) {

      boolean checkOrderId = true; // viết function kt xem vnp_TxnRef có tồn tại trong database không
      boolean checkAmount = true; // viết function kiểm tra vnp_Amount is valid (Check vnp_Amount VNPAY returns
                                  // compared to the amount of the code (vnp_TxnRef) in the Your database).
      boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)
      if (checkOrderId) {
        if (checkAmount) {
          if ("00".equals(vnp_ResponseCode)) {
            // update order status to 1
          } else {
            // Giao dịch không thành công làm gì tiếp thì làm
          }
        } else {
          // bắn ra 1 cái exception hay 1 cái j đấy
        }
      } else {
        // bắn ra 1 cái exception hay 1 cái j đấy
      }
    }
  }
}
