package ua.nure.fomenko.final_project.util;

import ua.nure.fomenko.final_project.exception.AppException;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by fomenko on 25.09.2017.
 */
public class MailHelper {

    private static final String userName = "fomenko954@gmail.com";
    private static final String userPassword = "frnpkwcykndnfqxr";

    public static void sendMail(String mail, String subj, String msg) throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        message.setSubject(subj);
        message.setText(msg);
        Transport.send(message);
    }



    private static Session getSession() {
        Session session = Session.getDefaultInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, userPassword);
            }
        });
        return session;
    }

    private static Properties getProperties() {
       try {
           Properties properties = new Properties();
           InputStream inputStream = MailHelper.class.getClassLoader().getResourceAsStream("mail.properties");
           properties.load(inputStream);

           return properties;
       } catch (IOException e) {
           throw new AppException("Cannot find mail.properties in classpath", e);
       }
    }
}
