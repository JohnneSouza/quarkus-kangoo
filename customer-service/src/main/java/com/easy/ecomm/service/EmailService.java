package com.easy.ecomm.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EmailService {

    @Inject
    Mailer mailer;

    public void sendActivationEmail(String email){
        mailer.send(Mail.withText(email, "Confirm your account on Easy&Com", "Thank you"));
    }

}
