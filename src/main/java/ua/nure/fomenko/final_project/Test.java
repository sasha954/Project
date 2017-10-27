package ua.nure.fomenko.final_project;

import ua.nure.fomenko.final_project.db.entity.Status;
import ua.nure.fomenko.final_project.util.MailHelper;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by fomenko on 10.09.2017.
 */
public class Test {





    public static void main(String[] args) {
        try {
            MailHelper.sendMail("fomenko954@gmail.com", "test", "Test message");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
