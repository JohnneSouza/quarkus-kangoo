package dev.kangoo.core.email;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EmailService {

    @Inject
    Mailer mailer;

    public void sendActivationEmail(String email, String activationKey){
        mailer.send(Mail.withText(email, "Confirm your account on Kangoo", "Thank you, your key:" +
                activationKey));
    }

}
