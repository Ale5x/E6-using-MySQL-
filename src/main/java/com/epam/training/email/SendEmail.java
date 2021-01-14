package com.epam.training.email;

import com.epam.training.exception.MailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    private final static Logger logger = LogManager.getLogger(SendEmail.class);

    private PropertyEmail email = new PropertyEmail();
    /*
       Отправка сообщения.
    */
    public void sendMail(String reception, String textMessage) throws MailException {
        try {
            final Properties properties = new Properties();
            properties.put(email.getAuth(), email.getAuthStatus());
            properties.put(email.getEnable(), email.getEnableStatus());
            properties.put(email.getHost(), email.getHostStatus());
            properties.put(email.getPort(), email.getPortStatus());

            String myAccountEmail = email.getAccountEmail();
            String myAccountPassword = email.getPassword();

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, myAccountPassword);
                }
            });
            Message message = prepareMessage(session, myAccountEmail, reception, textMessage);
            Transport.send(message);
        }catch (MessagingException e) {
            logger.error("Method senMail... Error... Email: " + reception);
            logger.error(e);
            throw new MailException(e);
        }
    }

    /*
        Формирование сообщения перед отправкой...
     */
    private Message prepareMessage(Session session, String myAccountEmail, String reception, String textMessage) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reception));
            message.setSubject("New a book in the library.");
            message.setText(textMessage);
            return message;
        } catch (Exception e) {
            logger.error("Method prepareMessage... Error...");
            logger.error(e);
        }
        return null;
    }
}
