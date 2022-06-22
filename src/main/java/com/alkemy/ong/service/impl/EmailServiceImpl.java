package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.EmailService;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${alkemy.ong.email.sender}")
    private String emailSender;
    @Value("${alkemy.ong.email.apikey}")
    private String sendGridApiKey;
    @Value("${alkemy.ong.email.templateid}")
    private String templateID;

    @Override
    public void sendEmailTo(String to) throws IOException{

        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        String subject = "Gracias!";
        Content content = new Content("text/html", "escribiralgo");
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        mail.setTemplateId(templateID);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(request.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}