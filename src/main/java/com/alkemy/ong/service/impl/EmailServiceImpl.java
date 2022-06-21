package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.EmailService;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.io.IOException;


public class EmailServiceImpl implements EmailService {

    @Autowired
    private Environment env;

    @Value("${alkemy.ong.email.sender}")
    private String emailSender;

    @Override
    public void sendEmailTo(String to) {

        String apiKey = env.getProperty("EMAIL_API_KEY");

        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content = new Content("text/css", "body")


        String subjetc = "Somos Mas ONG y te damos la bienvenida!";

        Mail mail = new Mail(fromEmail, subjetc, toEmail, content);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/welcome");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(request.getHeaders());
        } catch (IOException ex) {
            System.out.println("Error trying to send Welcome Email");
        }

    }
}
