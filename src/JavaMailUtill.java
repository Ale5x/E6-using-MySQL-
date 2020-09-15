import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailUtill {

    private String book;
    private String textMessage;

    /*
    Отправки сообщения пользователям о появлении новой книги в библиотеке.
     */
    public void sendMail(String reception, String book, String textMessage) throws Exception {
        this.book = book;
        this.textMessage = textMessage;
        final Properties proporties = new Properties();
        proporties.put("mail.smtp.auth", "true");
        proporties.put("mail.smtp.starttls.enable", "true");
        proporties.put("mail.smtp.host", "smtp.gmail.com");
        proporties.put("mail.smtp.port", "587");

        String myAccountEmail = "projectjava20@gmail.com";
        String myAccountPassword = "1999AlG1999";

        Session session = Session.getInstance(proporties, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, myAccountPassword);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, reception);
        Transport.send(message);
       // System.out.println("Message send successfully");
    }

    /*
    Формирование сообщения пользователям о появлении новой книги...
     */
    private Message prepareMessage(Session session, String myAccountEmail, String reception) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reception));
            message.setSubject("New a book in the library.");
            message.setText("Hello."  + "\n" + textMessage + "\n\"" + book + "\"");
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}