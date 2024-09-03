package org.example.gestrecrutements.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String toEmail, String subject, String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setTo(toEmail);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendAcceptanceEmail(String to, String candidateName, String offerTitle) {
        String subject = "Application Accepted - " + offerTitle;
        String message = "Dear " + candidateName + ",\n\n" +
                "Congratulations! Your application for the position of " + offerTitle + " has been accepted.\n" +
                "We are excited to move forward with you.\n\n" +
                "Best regards,\nCompany Name";

        sendMail(to, subject, message);
    }

    @Override
    public void sendRejectionEmail(String to, String candidateName, String offerTitle) {
        String subject = "Application Rejected - " + offerTitle;
        String message = "Dear " + candidateName + ",\n\n" +
                "We regret to inform you that your application for the position of " + offerTitle + " has been rejected.\n" +
                "We appreciate your interest in our company and encourage you to apply for future positions.\n\n" +
                "Best regards,\nCompany Name";

        sendMail(to, subject, message);
    }
}
