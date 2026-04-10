package com.example.demo.service;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKeyString;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${contact.target.email}")
    private String targetInquiryEmail;

    public void sendOtpEmail(String destinationEmail, String otp) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        
        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(apiKeyString);

        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

        sendSmtpEmail.setSender(new SendSmtpEmailSender().email(senderEmail).name("Portfolio Admin"));
        sendSmtpEmail.setTo(Collections.singletonList(new SendSmtpEmailTo().email(destinationEmail)));
        sendSmtpEmail.setSubject("Your Admin Login OTP");
        sendSmtpEmail.setHtmlContent("<h3>Welcome Back, Admin</h3><p>Your login OTP is: <b>" + otp + "</b></p><p>This code expires in 10 minutes.</p>");

        try {
            apiInstance.sendTransacEmail(sendSmtpEmail);
        } catch (ApiException e) {
            System.err.println("Exception when calling TransactionalEmailsApi#sendTransacEmail");
            e.printStackTrace();
        }
    }

    public void sendContactEmail(String name, String fromEmail, String message) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(apiKeyString);

        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

        sendSmtpEmail.setSender(new SendSmtpEmailSender().email(senderEmail).name("Portfolio Inquiry"));
        
        // Target is the user's email configured in properties
        sendSmtpEmail.setTo(Collections.singletonList(new SendSmtpEmailTo().email(targetInquiryEmail)));
        
        sendSmtpEmail.setSubject("New Portfolio Inquiry from " + name);
        
        String htmlContent = String.format(
            "<h3>New Contact Message</h3>" +
            "<p><b>Name:</b> %s</p>" +
            "<p><b>Email:</b> %s</p>" +
            "<p><b>Message:</b></p>" +
            "<div style='background: #f4f4f4; padding: 15px; border-radius: 8px;'>%s</div>",
            name, fromEmail, message.replace("\n", "<br>")
        );
        
        sendSmtpEmail.setHtmlContent(htmlContent);

        try {
            apiInstance.sendTransacEmail(sendSmtpEmail);
        } catch (ApiException e) {
            System.err.println("Exception when sending contact email");
            e.printStackTrace();
        }
    }
}
