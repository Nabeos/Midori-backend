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
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.EmailDetails;
import com.midorimart.managementsystem.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class EmailSenderServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

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
        String body = "<p>Xin chào " + order.getFullName() + ",</br>"
                + "Đơn hàng " + order.getOrderNumber() + " đã được chấp nhận. Đơn hàng sẽ được giao vào hồi "
                + order.getDeliveryTimeRange() + " "
                + order.getDeliveryDate().substring(0, 11)
                + ".</p></br>"
                + "<p>Rất mong sự hiện diện của quý khách vào thời điểm đã đề cập ở trên</br>"
                + "</p>"
                + "<h3>Midori mart,</h3></br>"
                + "<h3>Thân</h3>";
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
        String verifyURL="http://localhost:3000/emailverification?code="+user.getVerificationCode();
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
