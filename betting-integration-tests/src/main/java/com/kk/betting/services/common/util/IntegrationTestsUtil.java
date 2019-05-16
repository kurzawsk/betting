package com.kk.betting.services.common.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by KK on 2016-12-28.
 */
public class IntegrationTestsUtil {

    private static final String MAIL_USER_PROPERTY = "mail.username";
    private static final String MAIL_PASSWORD_PROPERTY = "mail.password";
    private static final String MAIL_RECIPIENTS_PROPERTY = "mail.recipients";

    public static void sendEmail(String subject, String content) {


        Properties properties = System.getProperties();
        String[] recipients = properties.getProperty(MAIL_RECIPIENTS_PROPERTY).split(",");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(properties.getProperty(MAIL_USER_PROPERTY), properties.getProperty(MAIL_PASSWORD_PROPERTY));
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty(MAIL_USER_PROPERTY)));
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
