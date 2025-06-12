package com.gallery.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gallery.dto.EmailResponse;


@Service
public class EmailService {

    private final SendGrid sendGrid;
    private final String fromEmail;

    public EmailService(@Value("${sendgrid.api.key}") String apiKey,
                        @Value("${sendgrid.from.email}") String fromEmail) {
        this.sendGrid = new SendGrid(apiKey);
        this.fromEmail = fromEmail;
    }

    public void sendRegistrationEmail(String to, String username) {
        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);
        String subject = "Welcome to Gallery Management System";
        Content content = new Content("text/plain", "Hi " + username + ",\n\nYour account has been created successfully!\n\nRegards,\nGalleryPro Team");
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);

        // System.out.println("Email sent: {}"+ response.getStatusCode());
        } catch (Exception ex) {
            // log error (omitted for brevity)
        }
    }

    public void sendContactFormEmail(String userFullName, String userEmail, String userSubject, String userMessage) {
        Email from = new Email(fromEmail);
        Email toEmail = new Email(fromEmail); // Send to your own email
        String subject = "Contact Form: " + userSubject;

        String emailContent = String.format(
            "New contact form submission:\n\n" +
            "Name: %s\n" +
            "Email: %s\n" +
            "Subject: %s\n\n" +
            "Message:\n%s\n\n" +
            "---\n" +
            "This email was sent from the Gallery Management System contact form.",
            userFullName, userEmail, userSubject, userMessage
        );

        Content content = new Content("text/plain", emailContent);
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to send contact form email", ex);
        }
    }
}
