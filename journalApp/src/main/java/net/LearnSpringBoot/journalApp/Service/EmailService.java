package net.LearnSpringBoot.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Slf4j
public class EmailService {

   @Autowired
    private JavaMailSender javaMailSender;

public void  SendEmail(String to,String subject,String body){
    try{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);
        javaMailSender.send(mail);

    } catch (Exception e) {
       log.error("error while sending mail",e); ;
    }
}
}
