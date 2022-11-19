package com.midorimart.managementsystem.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.Order;
import com.midorimart.managementsystem.entity.OrderDetail;
import com.midorimart.managementsystem.entity.Product;
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.EmailDetails;
import com.midorimart.managementsystem.repository.ProductRepository;
import com.midorimart.managementsystem.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class EmailSenderServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final ProductRepository productRepository;

    private List<MimeMessage> queue = new ArrayList<>();

    public void push(Order order) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = sendSuccessfulOrderNotice(order);
        queue.add(message);
    }

    @Scheduled(fixedRate = 2000, initialDelay = 10000)
    public void run() {
        int success = 0, error = 0;
        while (!queue.isEmpty()) {
            MimeMessage message = queue.remove(0);
            try {
                mailSender.send(message);
                success++;
            } catch (Exception e) {
                error++;
            }
        }
        System.out.println("Sent: " + success + ", Error: " + error);
    }

    @Override
    public MimeMessage sendSuccessfulOrderNotice(Order order) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String body = "<p>Xin chào " + order.getFullName() + ",</br>"
                + "Đơn hàng " + order.getOrderNumber() + " đã được giao thành công vào hồi "
                + order.getDeliveryTimeRange() + " "
                + order.getDeliveryDate().substring(0, 11)
                + ".</p></br>"
                + "<p>Vui lòng đăng nhập <a href='#' style='color:red'>Midori Mart</a> để có thể xác nhận sản phẩm cũng như tiến hành hoàn trả trong vòng 3 ngày.</br>"
                + " Nếu sau 3 ngày quý khách không xác nhận thì chính sách hoàn trả sẽ hết hiệu lực.</p>";
        emailDetails.setSubject("Đơn hàng " + order.getOrderNumber() + " đã giao thành công");
        emailDetails.setMsgBody(body);
        emailDetails.setRecipient(order.getEmail());
        return sendEmail(emailDetails);
    }

    public MimeMessage sendEmail(EmailDetails emailDetails) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("midorimartapp@gmail.com", "Midori Mart");
        helper.setText(emailDetails.getMsgBody(), true);
        helper.setSubject(emailDetails.getSubject());
        helper.setTo(emailDetails.getRecipient());
        queue.add(message);
        return message;
    }

    @Override
    public MimeMessage sendAcceptedEmail(Order order) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String bodyCart = "";
        for (OrderDetail od : order.getCart()) {
            Product product = productRepository.findById(od.getId()).get();
            bodyCart += "<tr>"+product.getTitle()+"</tr>";
            bodyCart += "<tr>"+product.getSku()+"</tr>";
            bodyCart += "<tr>"+od.getPrice()+"</tr>";
            bodyCart += "<tr>"+od.getQuantity()+"</tr>";
            bodyCart += "<tr>"+od.getTotalMoney()+"</tr>";
        }
        String body = "<!DOCTYPE html>"
        +"<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><meta http-equiv='X-UA-Compatible' content='ie=edge'>"
            +"<title>Static Template</title>"
            +"<link rel='stylesheet' href='index.css'>"
            +"</head>"
            +"<body>"
            +"<div>"
            +"<h3>Cảm ơn quý khách <span>"+order.getFullName()+"</span> đã đặt hàng tại MidoriMart,</h3>"
            +"<div>MidoriMart rất vui thông báo đơn hàng "+order.getOrderNumber()+" của quý khách đã được tiếp nhận và đang trong quá trình xử lý. MidoriMart sẽ thông báo đến quý khách ngay khi hàng chuẩn bị được giao.</div>"
            +"<div>"
            +"<h4 style='border-bottom: 1px solid lightgray; padding-bottom: 10px'><span style='color:#0c6e21'>THÔNG TIN ĐƠN HÀNG "+order.getOrderNumber()+"</span> "+order.getOrderDate()+"</h4>"
            +"<div style=display:flex'>"
            +"<div style='margin-left: 10px; margin-right: 100px'>"
            +"<span style='font-weight: bold'> Thông tin thanh toán</span>"
            +"<div>"+order.getFullName()+"</div>"
            +"<div>"+order.getEmail()+"</div>"
            +"<div>"+order.getPhoneNumber()+"</div>"
            +"</div><div ><span style='font-weight: bold'> Địa chỉ giao hàng</span><div>"+order.getAddress()+"</div>"
            +"</div></div><div style='margin-left: 10px; margin-top: 10px'>"
            +"<span style='font-weight: bold'>Thời gian giao hàng dự kiến:</span> Dự kiến giao hàng "+order.getDeliveryDate()+" "+order.getDeliveryTimeRange()
            +"</div><h4 style='order-bottom: 1px solid lightgray; padding-bottom: 10px; color:#0c6e21'>CHI TIẾT ĐƠN HÀNG</h4><div>"
            +"<table>"
            +"  <tr>"
            +"    <th>Sản phẩm</th>"
            +"    <th>Mã sản phẩm</th>"
            +"    <th>Giá tiền</th>"
            +"    <th>Số lượng</th>"
            +"     <th>Tổng tạm</th>"
            +"  </tr>"

            +bodyCart

            +" </table></div><div style='text-align: end; margin-top: 10px'><div>Tạm tính:  "+order.getTotalMoney()+"</div>"
            +"  <div>Phí vận chuyển:  35.000đ</div>"
            +"<div style='font-weight: bold'>Tổng giá trị đơn hàng: "+(order.getTotalMoney()+35000)+"</div></div></div></div>"
            +"</html>";
        emailDetails.setSubject("Đơn hàng " + order.getOrderNumber() + " đã được chấp nhận");
        emailDetails.setMsgBody(body);
        emailDetails.setRecipient(order.getEmail());
        return sendEmail(emailDetails);
    }

    @Override
    public MimeMessage sendRejectedEmail(Order order) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String body = "<p>Xin chào " + order.getFullName() + ",</br>"
                + "Đơn hàng " + order.getOrderNumber() + " đã bị từ chối từ người bán"
                + "<p>Vui lòng đăng nhập <a href='#' style='color:red'>Midori Mart</a> để có thể xem chi tiết.</p></br>"
                + "<p>Nếu có thắc mắc xin vui lòng liên hệ</p>";
        emailDetails.setSubject("Đơn hàng " + order.getOrderNumber() + " bị từ chối");
        emailDetails.setMsgBody(body);
        emailDetails.setRecipient(order.getEmail());
        return sendEmail(emailDetails);
    }

    @Override
    public MimeMessage sendVerificationEmail(User user) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String toAddress=user.getEmail();

        String subject = "Reset password link";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Midori Mart";

        content=content.replace("[[name]]", user.getFullname());
        String verifyURL="http://localhost:3000/emailverification/"+user.getVerificationCode();
        content=content.replace("[[URL]]", verifyURL);

        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(content);
        emailDetails.setRecipient(toAddress);
        return sendEmail(emailDetails);

    }

    @Override
    public MimeMessage sendResetPasswordEmail(User user, String randomCode) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String toAddress=user.getEmail();

        String subject = "Reset password link";
        String content = "Dear [[name]],<br>"
                + "Your password has been reset as requested.<br>"
                + "Your new password is: [[password]] <br>"
                + "Please re login and change your password immediately. <br> "
                + "Thank you,<br>"
                + "Midori Mart";

        content=content.replace("[[name]]", user.getFullname());
        content=content.replace("[[password]]", randomCode);

        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(content);
        emailDetails.setRecipient(toAddress);
        return sendEmail(emailDetails);
    }



}
