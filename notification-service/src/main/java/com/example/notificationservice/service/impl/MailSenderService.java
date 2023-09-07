package com.example.notificationservice.service.impl;

import com.example.notificationservice.model.dto.request.UserRegisterEmailRequestDto;
import com.example.notificationservice.model.dto.response.PanasResultResponseDto;
import com.example.notificationservice.model.entity.DailySubscribeUser;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    @Retry(name = "sendNotification",fallbackMethod = "fallbackForSendNotification")
    public ResponseEntity<String> sendMail(UserRegisterEmailRequestDto request) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(request.getEmail());
            helper.setSubject("Confirm account!");

            String confirmationLink = "http://localhost:8081/user/auth/confirm/" + request.getToken();
            String emailContent = "<html><body><p>Please click the following link to confirm your account:</p>"
                    + "<a href=\"" + confirmationLink + "\">Confirm Account</a>"
                    + "</body></html>";

            helper.setText(emailContent, true);

            javaMailSender.send(message);
            log.info("Email sent: " + request.getEmail());

            return new ResponseEntity<>("Email sent successfully.", HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while sending the email: {}", e.getMessage());
            throw new RuntimeException("Email could not be sent");
        }
    }

    public ResponseEntity<String> fallbackForSendNotification(UserRegisterEmailRequestDto request,Throwable t){
        log.error("Error occurred while sending email for -> {}", request.getEmail(), t);

        return new ResponseEntity<>("The server is busy. Please try again later.",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RabbitListener(queues = "thirdStepQueue")
    public void sendSubscriptionMail(DailySubscribeUser user) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        String startDate = user.getStartDate();
        String endDate = user.getEndDate();
        String email = user.getEmail();

        try {
            helper.setTo(user.getEmail());

            String emailContent = generateSubscribeEmailContent(email,startDate,endDate);

            helper.setText(emailContent, true);

            javaMailSender.send(message);
            log.info("Email sent: " + user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String generateSubscribeEmailContent(String email, String startDate, String endDate) {
        return "<html><body style='font-family: Arial, sans-serif;'>"
                + "<h1 style='color: #4CAF50;'>🎉 Subscription Successful! 🎉</h1>"
                + "<p>Hello <strong>" + email + "</strong> 👋,</p>"
                + "<p>Your subscription has been successfully completed.</p>"
                + "<p>Your payment has been received, and your subscription period has started.</p>"
                + "<p><strong>Start Date:</strong> " + startDate + "</p>"
                + "<p><strong>End Date:</strong> " + endDate + "</p>"
                + "<p>Thank you! 🙌</p>"
                + "</body></html>";
    }

    public void sendPanasMail(DailySubscribeUser request) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(request.getEmail());
            helper.setSubject("Participate in the PANAS Survey");

            // URL to redirect the user
            String subscriptionLink = "http://localhost:8084/subscription/" + request.getEmail();

            String emailContent = "<html><body>"
                    + "<h1 style='color: blue;'>Welcome to the PANAS Survey!</h1>"
                    + "<p style='font-size: 18px;'>We are delighted that you have chosen to participate in our survey on Positive and Negative affect. Your responses will be of great value.</p>"
                    + "<div style='margin-top: 20px;'>"
                    + "<a style='background-color: #4CAF50; color: white; padding: 14px 20px; margin: 8px 0; border: none; cursor: pointer; width: 100%; text-align: center;' href='" + subscriptionLink + "'>"
                    + "Click Here to Start the Survey"
                    + "</a>"
                    + "</div>"
                    + "</body></html>";

            helper.setText(emailContent, true);

            javaMailSender.send(message);
            log.info("Email sent: " + request.getEmail());

        } catch (Exception e) {
            log.error("An error occurred while sending the email: {}", e.getMessage());
            throw new RuntimeException("Email could not be sent");
        }
    }


    public void sendWeeklyPanasAnalysis(DailySubscribeUser dailySubscribeUser) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(dailySubscribeUser.getEmail());
            helper.setSubject("Your Weekly PANAS Analysis is Ready!");

            // URL to redirect the user
            String link = "http://localhost:8084/subscription/week-result/" + dailySubscribeUser.getEmail();

            String emailContent = "<html><body>"
                    + "<div style='margin: 20px; padding: 20px; font-family: Arial, sans-serif;'>"
                    + "<h2 style='color: #4CAF50;'>Hello!</h2>"
                    + "<p>We're excited to share your weekly PANAS analysis with you. 👩‍⚕️🔍</p>"
                    + "<p>To view your results and discover ways to improve your well-being, please click the button below:</p>"
                    + "<div style='text-align: center;'>"
                    + "<a style='background-color: #4CAF50; color: white; padding: 14px 30px; margin: 20px auto; border-radius: 5px; text-decoration: none;' href='" + link + "'>"
                    + "View My Analysis 📊"
                    + "</a>"
                    + "</div>"
                    + "<p>Best regards,<br>Your Well-Being Team </p>"
                    + "</div>"
                    + "</body></html>";

            helper.setText(emailContent, true);

            javaMailSender.send(message);
            log.info("Email sent: " + dailySubscribeUser.getEmail());

        } catch (Exception e) {
            log.error("An error occurred while sending the email: {}", e.getMessage());
            throw new RuntimeException("Email could not be sent");
        }
    }
}
