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
import com.midorimart.managementsystem.utils.DateHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class EmailSenderServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final ProductRepository productRepository;
    private int shippingFee = 30000;
    private List<MimeMessage> queue = new ArrayList<>();

    public void push(Order order) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = sendSuccessfulOrderNotice(order);
        queue.add(message);
    }

    // send email after 1s
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

    // Send email when order status is success
    @Override
    public MimeMessage sendSuccessfulOrderNotice(Order order) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String verifyUri = "http://localhost:3000/guestnotificationrefund/" + order.getOrderNumber() + "/"
                + order.getOrderCode();
        String bodyCart = buildBodyCart(order);
        String orderDate = DateHelper.convertDate(order.getOrderDate()).substring(0, 10);
        String orderDay = orderDate.split("-")[0];
        String oMonth = orderDate.split("-")[1];
        String oYear = orderDate.split("-")[2];
        String orderDeliveryDate = order.getDeliveryDate().substring(0, 10);
        String odDay = orderDeliveryDate.split("-")[2];
        String odMonth = orderDeliveryDate.split("-")[1];
        String odYear = orderDeliveryDate.split("-")[0];
        String orderTime = DateHelper.convertDate(order.getOrderDate()).substring(10);
        String body = "<!DOCTYPE html>"
                + "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><meta http-equiv='X-UA-Compatible' content='ie=edge'>"
                + "<title>Static Template</title>"
                + "<style>"
                + " table {"
                + "   font-family: arial, sans-serif;"
                + " border-collapse: collapse;"
                + " width: 100%;}"
                + "td,th {"
                + "border: 1px solid #dddddd;"
                + "   text-align: left;"
                + "    padding: 8px;}"
                + "tr {"
                + " font-size: 13px;}"
                + "                  tr:nth-child(even) {"
                + "background-color: #dddddd;}"
                + "#cancelOrderButton {"
                + " border: 1px solid #EAB308;"
                + "   background: #EAB308;"
                + "   border-radius: 5px;"
                + "   padding: 10px;"
                + "   color: white;"
                + "   font-size: 15px;"
                + "   text-decoration: none; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div>"
                + "<h3>C???m ??n qu?? kh??ch <span>" + order.getFullName() + "</span> ???? ?????t h??ng t???i Midori Mart,</h3>"
                + "<div>Midori Mart r???t vui th??ng b??o ????n h??ng " + order.getOrderNumber()
                + " c???a qu?? kh??ch ???? ???????c giao th??nh c??ng. Qu?? kh??ch h??y ki???m tra l???i ????n h??ng c???a m??nh.</div>"
                + "<div>"
                + "    <h4 style='border-bottom: 1px solid lightgray; padding-bottom: 10px'><span style='color:#0c6e21'>TH??NG TIN ????N H??NG "
                + order.getOrderNumber() + "</span> (Ng??y " + orderDay + " Th??ng " + oMonth + " N??m " + oYear + " "
                + orderTime + ")</h4>"
                + "<div style='display:flex'>"
                + "<div style='margin-left: 10px; margin-right: 100px'>"
                + "<span style='font-weight: bold'> Th??ng tin thanh to??n</span>"
                + "<div>" + order.getFullName() + "</div>"
                + "<div>" + order.getEmail() + "</div>"
                + "<div>" + order.getPhoneNumber() + "</div>"
                + "</div><div ><span style='font-weight: bold'> ?????a ch??? giao h??ng</span><div>" + order.getAddress().split(";")[3]
                + "</div>"
                + "</div></div><div style='margin-left: 10px; margin-top: 10px'>"
                + "<span style='font-weight: bold'>Th???i gian giao h??ng d??? ki???n:</span>  "
                + odDay + "/" + odMonth + "/" + odYear + " " + order.getDeliveryTimeRange()
                + "</div><h4 style='order-bottom: 1px solid lightgray; padding-bottom: 10px; color:#0c6e21'>CHI TI???T ????N H??NG</h4><div>"
                + "<table>"
                + "  <tr>"
                + "    <th>S???n ph???m</th>"
                + "    <th>M?? s???n ph???m</th>"
                + "    <th>Gi?? ti???n</th>"
                + "    <th>S??? l?????ng</th>"
                + "     <th>T???ng t???m</th>"
                + "  </tr>"

                + bodyCart

                + " </table></div>"
                +" <div style='text-align: end; margin-top: 10px'>"
                + "<div style='font-weight: bold'>T???ng gi?? tr??? ????n h??ng: " + order.getTotalMoney()
                + "??</div></div>"
                + "<div style='display: flex; justify-content: center; margin-top: 10px'>"
                + "<a href='" + verifyUri + "' id='cancelOrderButton'>Tr??? h??ng/Ho??n ti???n</a></div>"
                + "</div></div>"
                + "</html>";
        emailDetails.setSubject("????n h??ng " + order.getOrderNumber() + " ???? giao h??ng th??nh c??ng");
        emailDetails.setMsgBody(body);
        emailDetails.setRecipient(order.getEmail());
        return sendEmail(emailDetails);
    }

    public MimeMessage sendEmail(EmailDetails emailDetails) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("midorimartapp@gmail.com", "MidoriMart");
        helper.setText(emailDetails.getMsgBody(), true);
        helper.setSubject(emailDetails.getSubject());
        helper.setTo(emailDetails.getRecipient());
        queue.add(message);
        return message;
    }

    // Send email to customer after shopkeeper accepted order
    @Override
    public MimeMessage sendAcceptedEmail(Order order) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String bodyCart = buildBodyCart(order);
        String orderDate = DateHelper.convertDate(order.getOrderDate()).substring(0, 10);
        String orderDay = orderDate.split("-")[0];
        String oMonth = orderDate.split("-")[1];
        String oYear = orderDate.split("-")[2];
        String orderDeliveryDate = order.getDeliveryDate().substring(0, 10);
        String odDay = orderDeliveryDate.split("-")[2];
        String odMonth = orderDeliveryDate.split("-")[1];
        String odYear = orderDeliveryDate.split("-")[0];
        String orderTime = DateHelper.convertDate(order.getOrderDate()).substring(10);
        String body = "<!DOCTYPE html>"
                + "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><meta http-equiv='X-UA-Compatible' content='ie=edge'>"
                + "<title>Static Template</title>"
                + "<style>"
                + " table {"
                + "   font-family: arial, sans-serif;"
                + " border-collapse: collapse;"
                + " width: 100%;}"
                + "td,th {"
                + "border: 1px solid #dddddd;"
                + "   text-align: left;"
                + "    padding: 8px;}"
                + "tr {"
                + " font-size: 13px;}"
                + "                  tr:nth-child(even) {"
                + "background-color: #dddddd;}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div>"
                + "<h3>C???m ??n qu?? kh??ch <span>" + order.getFullName() + "</span> ???? ?????t h??ng t???i Midori Mart,</h3>"
                + "<div>Midori Mart r???t vui th??ng b??o ????n h??ng " + order.getOrderNumber()
                + " c???a qu?? kh??ch ???? ???????c ti???p nh???n v?? ??ang trong qu?? tr??nh x??? l??. Midori Mart s??? th??ng b??o ?????n qu?? kh??ch ngay khi h??ng chu???n b??? ???????c giao.</div>"
                + "    <h4 style='border-bottom: 1px solid lightgray; padding-bottom: 10px'><span style='color:#0c6e21'>TH??NG TIN ????N H??NG "
                + order.getOrderNumber() + "</span> (Ng??y " + orderDay + " Th??ng " + oMonth + " N??m " + oYear + " "
                + orderTime + ")</h4>"
                + "<div style='display:flex'>"
                + "<div style='margin-left: 10px; margin-right: 100px'>"
                + "<span style='font-weight: bold'> Th??ng tin thanh to??n</span>"
                + "<div>" + order.getFullName() + "</div>"
                + "<div>" + order.getEmail() + "</div>"
                + "<div>" + order.getPhoneNumber() + "</div>"
                + "</div><div ><span style='font-weight: bold'> ?????a ch??? giao h??ng</span><div>" + order.getAddress().split(";")[3]
                + "</div>"
                + "</div></div><div style='margin-left: 10px; margin-top: 10px'>"
                + "<span style='font-weight: bold'>Th???i gian giao h??ng d??? ki???n:</span> "
                + odDay + "/" + odMonth + "/" + odYear + " " + order.getDeliveryTimeRange()
                + "</div><h4 style='order-bottom: 1px solid lightgray; padding-bottom: 10px; color:#0c6e21'>CHI TI???T ????N H??NG</h4><div>"
                + "<table>"
                + "  <tr>"
                + "    <th>S???n ph???m</th>"
                + "    <th>M?? s???n ph???m</th>"
                + "    <th>Gi?? ti???n</th>"
                + "    <th>S??? l?????ng</th>"
                + "    <th>T???ng t???m</th>"
                + "  </tr>"

                + bodyCart

                + " </table></div>"
                +" <div style='text-align: end; margin-top: 10px'>"
                + "<div style='font-weight: bold'>T???ng gi?? tr??? ????n h??ng: " + order.getTotalMoney()
                + "??</div></div></div></div>"
                + "</html>";
        emailDetails.setSubject("????n h??ng " + order.getOrderNumber() + " ???? ???????c ch???p nh???n");
        emailDetails.setMsgBody(body);
        emailDetails.setRecipient(order.getEmail());
        return sendEmail(emailDetails);
    }

    private String buildBodyCart(Order order) {
        String bodyCart = "";
        for (OrderDetail od : order.getCart()) {
            Product product = productRepository.findById(od.getProduct().getId()).get();
            bodyCart += "<tr>";
            bodyCart += "<td>" + product.getTitle() + "</td>";
            bodyCart += "<td>" + product.getSku() + "</td>";
            bodyCart += "<td>" + od.getPrice() + "??</td>";
            bodyCart += "<td>" + od.getQuantity() + "</td>";
            bodyCart += "<td>" + od.getTotalMoney() + "??</td>";
            bodyCart += "</tr>";
        }
        return bodyCart;
    }

    // Send reject email after shopkeeper rejected order
    @Override
    public MimeMessage sendRejectedEmail(Order order) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String orderDate = DateHelper.convertDate(order.getOrderDate()).substring(0, 10);
        String orderDay = orderDate.split("-")[0];
        String oMonth = orderDate.split("-")[1];
        String oYear = orderDate.split("-")[2];
        String orderDeliveryDate = order.getDeliveryDate().substring(0, 10);
        String odDay = orderDeliveryDate.split("-")[2];
        String odMonth = orderDeliveryDate.split("-")[1];
        String odYear = orderDeliveryDate.split("-")[0];
        String orderTime = DateHelper.convertDate(order.getOrderDate()).substring(10);
        String body = "<!DOCTYPE html>"
                + "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<meta http-equiv='X-UA-Compatible' content='ie=edge'><title>Static Template</title>"
                + "<style>"
                + " table {"
                + "   font-family: arial, sans-serif;"
                + " border-collapse: collapse;"
                + " width: 100%;}"
                + "td,th {"
                + "border: 1px solid #dddddd;"
                + "   text-align: left;"
                + "    padding: 8px;}"
                + "tr {"
                + " font-size: 13px;}"
                + "                  tr:nth-child(even) {"
                + "background-color: #dddddd;}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "  <div>"
                + "    <h3>Xin l???i qu?? kh??ch <span>" + order.getFullName()
                + "</span> do ????n h??ng ???? ?????t t???i Midori Mart b??? h???y,</h3>"
                + "    <div>Midori Mart r???t ti???c khi ph???i th??ng b??o ????n h??ng " + order.getOrderNumber()
                + " c???a qu?? kh??ch ???? b??? h???y do kh??ng c??n m???t h??ng n??y trong kho. Mong qu?? kh??ch th??ng c???m cho Midori Mart!<br/> M???i b???n ti???p t???c mua s???m t???i <a href='#' style='color:red'>Midori Mart</a> </div>"
                + "  <div>"
                + "    <h4 style='border-bottom: 1px solid lightgray; padding-bottom: 10px'><span style='color:#0c6e21'>TH??NG TIN ????N H??NG "
                + order.getOrderNumber() + "</span> (Ng??y " + orderDay + " Th??ng " + oMonth + " N??m " + oYear + " "
                + orderTime + ")</h4>"
                + "    <div style='display:flex'>"
                + "  <div style='margin-left: 10px; margin-right: 100px'>"
                + "    <span style='font-weight: bold'> Th??ng tin thanh to??n</span>"
                + "    <div>" + order.getFullName() + "</div>"
                + "    <div>" + order.getEmail() + "</div>"
                + "    <div>" + order.getPhoneNumber() + "</div>"
                + "  </div>"
                + "  <div >"
                + "    <span style='font-weight: bold'> ?????a ch??? giao h??ng</span>"
                + "    <div>" + order.getAddress().split(";")[3] + "</div>"
                + "  </div>"
                + "  </div>"
                + "  <div style='margin-left: 10px; margin-top: 10px'>"
                + "    <span style='font-weight: bold'>Th???i gian giao h??ng d??? ki???n:</span> "
                + odDay + "/" + odMonth + "/" + odYear + " " + order.getDeliveryTimeRange()
                + "  </div>"
                + "  <h4 style='border-bottom: 1px solid lightgray; padding-bottom: 10px; color:#0c6e21'>CHI TI???T ????N H??NG</h4>"
                + "  <div>"
                + "    <table>"
                + "      <tr>"
                + "        <th>S???n ph???m</th>"
                + "        <th>M?? s???n ph???m</th>"
                + "        <th>Gi?? ti???n</th>"
                + "        <th>S??? l?????ng</th>"
                + "        <th>T???ng t???m</th>"
                + "      </tr>"
                + buildBodyCart(order)
                + "     </table>"
                + "  </div>"
                + "  <div style='text-align: end; margin-top: 10px'>"
                + "    <div style='font-weight: bold'>T???ng gi?? tr??? ????n h??ng: " + order.getTotalMoney()
                + "??</div>"
                + "  </div>"
                + "</div>"
                + "  </div>"
                + "</html>";
        emailDetails.setSubject("????n h??ng " + order.getOrderNumber() + " ???? b??? t??? ch???i");
        emailDetails.setMsgBody(body);
        emailDetails.setRecipient(order.getEmail());
        return sendEmail(emailDetails);
    }

    @Override
    public MimeMessage sendVerificationEmail(User user) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String toAddress = user.getEmail();

        String subject = "Reset password link";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Midori Mart";

        content = content.replace("[[name]]", user.getFullname());
        String verifyURL = "http://localhost:3000/emailverification/" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);

        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(content);
        emailDetails.setRecipient(toAddress);
        return sendEmail(emailDetails);

    }

    // Send reset password email
    @Override
    public MimeMessage sendResetPasswordEmail(User user, String randomCode)
            throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String toAddress = user.getEmail();

        String subject = "Reset password link";
        String content = "Dear [[name]],<br>"
                + "Your password has been reset as requested.<br>"
                + "Your new password is: [[password]] <br>"
                + "Please re login and change your password immediately. <br> "
                + "Thank you,<br>"
                + "Midori Mart";

        content = content.replace("[[name]]", user.getFullname());
        content = content.replace("[[password]]", randomCode);

        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(content);
        emailDetails.setRecipient(toAddress);
        return sendEmail(emailDetails);
    }

    // Send email after customer placed order successfully
    @Override
    public MimeMessage sendPlaceOrderNotice(Order order) throws UnsupportedEncodingException, MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String verifyUri = "http://localhost:3000/guestnotification/" + order.getOrderNumber() + "/"
                + order.getOrderCode();
        String bodyCart = buildBodyCart(order);
        String orderDate = DateHelper.convertDate(order.getOrderDate()).substring(0, 10);
        String orderDay = orderDate.split("-")[0];
        String oMonth = orderDate.split("-")[1];
        String oYear = orderDate.split("-")[2];
        String orderTime = DateHelper.convertDate(order.getOrderDate()).substring(10);
        String orderDeliveryDate = order.getDeliveryDate().substring(0, 10);
        String odDay = orderDeliveryDate.split("-")[2];
        String odMonth = orderDeliveryDate.split("-")[1];
        String odYear = orderDeliveryDate.split("-")[0];
        String body = "<!DOCTYPE html>"
                + "<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><meta http-equiv='X-UA-Compatible' content='ie=edge'>"
                + "<title>Static Template</title>"
                + "<style>"
                + " table {"
                + "   font-family: arial, sans-serif;"
                + " border-collapse: collapse;"
                + " width: 100%;}"
                + "td,th {"
                + "border: 1px solid #dddddd;"
                + "   text-align: left;"
                + "    padding: 8px;}"
                + "tr {"
                + " font-size: 13px;}"
                + "                  tr:nth-child(even) {"
                + "background-color: #dddddd;}"
                + "#cancelOrderButton {"
                + " border: 1px solid #d32a14;"
                + "   background: #d32a14;"
                + "   border-radius: 5px;"
                + "   padding: 10px;"
                + "   color: white;"
                + "   font-size: 15px;"
                + "   text-decoration: none;}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div>"
                + "<h3>C???m ??n qu?? kh??ch <span>" + order.getFullName() + "</span> ???? ?????t h??ng t???i Midori Mart,</h3>"
                + "<div>Midori Mart r???t vui th??ng b??o ????n h??ng " + order.getOrderNumber()
                + " c???a qu?? kh??ch <span style='font-weight:bold'>???? ???????c ?????t Th??nh C??ng v?? ??ang ch??? X??c Nh???n</span>. Midori Mart s??? th??ng b??o ?????n qu?? kh??ch ngay khi h??ng chu???n b??? ???????c giao.</div>"
                + "<div>"
                + "    <h4 style='border-bottom: 1px solid lightgray; padding-bottom: 10px'><span style='color:#0c6e21'>TH??NG TIN ????N H??NG "
                + order.getOrderNumber() + "</span> (Ng??y " + orderDay + " Th??ng " + oMonth + " N??m " + oYear + " "
                + orderTime + ")</h4>"
                + "<div style='display:flex'>"
                + "<div style='margin-left: 10px; margin-right: 100px'>"
                + "<span style='font-weight: bold'> Th??ng tin thanh to??n</span>"
                + "<div>" + order.getFullName() + "</div>"
                + "<div>" + order.getEmail() + "</div>"
                + "<div>" + order.getPhoneNumber() + "</div>"
                + "</div><div ><span style='font-weight: bold'> ?????a ch??? giao h??ng</span><div>" + order.getAddress().split(";")[3]
                + "</div>"
                + "</div></div><div style='margin-left: 10px; margin-top: 10px'>"
                + "<span style='font-weight: bold'>Th???i gian giao h??ng d??? ki???n:</span> "
                + odDay + "/" + odMonth + "/" + odYear + " " + order.getDeliveryTimeRange()
                + "</div><h4 style='order-bottom: 1px solid lightgray; padding-bottom: 10px; color:#0c6e21'>CHI TI???T ????N H??NG</h4><div>"
                + "<table>"
                + "  <tr>"
                + "    <th>S???n ph???m</th>"
                + "    <th>M?? s???n ph???m</th>"
                + "    <th>Gi?? ti???n</th>"
                + "    <th>S??? l?????ng</th>"
                + "     <th>T???ng t???m</th>"
                + "  </tr>"

                + bodyCart

                + " </table></div>"
                +" <div style='text-align: end; margin-top: 10px'>"
                + "<div style='font-weight: bold'>T???ng gi?? tr??? ????n h??ng: " +order.getTotalMoney()
                + "??</div></div>"
                + "<div style='display: flex; justify-content: center; margin-top: 10px'><a id='cancelOrderButton' href='"
                + verifyUri + "'>H???y ????n H??ng</a></div>"
                + "</div></div>"
                + "</html>";
        emailDetails
                .setSubject("????n h??ng " + order.getOrderNumber() + " ???? ?????t h??ng Th??nh C??ng v?? ??ang ch??? ???????c x??c nh???n");
        emailDetails.setMsgBody(body);
        emailDetails.setRecipient(order.getEmail());
        return sendEmail(emailDetails);
    }

}
