package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author karensaroc
 */
public class Email {

    // Sender's email ID needs to be mentioned
    private final String SUBJECT = "Secure System PIN";
    private final String EMAIL_PATH = "web/resources/DB/emailInfo/emailAccount.txt";
    private final String PATH = "web/resources/DB/emailInfo/mypwd.txt";

    // Recipient's email ID needs to be mentioned.
    private String to;

    public Email(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void sendGmail(String content) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(getEntrance(EMAIL_PATH), getEntrance(PATH));
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("SecureSystem@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(SUBJECT);
            message.setText(content);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getEntrance(String path) {
        String currentLine = "";
        File file = new File(path);

        if (file.exists()) {
            BufferedReader brL;
            try {
                brL = new BufferedReader(new FileReader(path));
                currentLine = brL.readLine();
                brL.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return currentLine;
    }
}
