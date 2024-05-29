package com.example.shabbyshackinn.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendBookingConfirmedEmail(String to, String subject, String customerFirstName, String customerLastName, int bookingNumber, String bookingStartDate, String bookingEndDate, int amountPaid) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("ShabbyShackInn@ShabbyShack.Inn");
            helper.setTo(to);
            helper.setSubject(subject);


            Context context = new Context();
            context.setVariable("customer_name", customerFirstName + " " + customerLastName);
            context.setVariable("booking_number", bookingNumber);
            context.setVariable("booking_startdate", bookingStartDate);
            context.setVariable("booking_endDate", bookingEndDate);
            context.setVariable("amount_paid", amountPaid);
            String htmlContent = templateEngine.process("bookingConfiramtionEmail.html", context);

            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }
}
