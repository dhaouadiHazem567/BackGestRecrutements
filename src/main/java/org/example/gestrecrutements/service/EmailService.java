package org.example.gestrecrutements.service;

public interface EmailService {

    void sendMail(String toEmail, String subject, String body);
    void sendAcceptanceEmail(String to, String candidateName, String offerTitle);
    void sendRejectionEmail(String to, String candidateName, String offerTitle);

}
