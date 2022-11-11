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
import com.midorimart.managementsystem.model.EmailDetails;
import com.midorimart.managementsystem.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class EmailSenderServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    private List<MimeMessage> queue = new ArrayList<>();

    public void push(Order order) throws UnsupportedEncodingException, MessagingException{
        MimeMessage message = sendSuccessfulOrderNotice(order);
        queue.add(message);
    }

    @Scheduled(fixedRate = 2000, initialDelay = 10000)
    public void run(){
        int success = 0, error = 0;
        while(!queue.isEmpty()){
            MimeMessage message = queue.remove(0);
            try{
            mailSender.send(message);
            success++;
            }catch(Exception e){
                error++;
            }
        }
        System.out.println("Sent: "+success+", Error: "+error);
    }

    public MimeMessage sendEmail(EmailDetails emailDetails) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("midorimartapp@gmail.com", "Midori Mart");
        helper.setText(emailDetails.getMsgBody(), true);
        helper.setSubject(emailDetails.getSubject());
        helper.setTo(emailDetails.getRecipient());
        return message;
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
}
