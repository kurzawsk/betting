package com.kk.betting.services.common.action;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.kk.betting.services.common.service.ConfigurationProvider;
import com.kk.betting.services.common.util.CommonConstants;
import com.kk.betting.services.common.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by KK on 2017-06-19.
 */
@Singleton
public class EmailSender {

    public static final String DEFAULT_SUBJECT = "[BETBOT] ";
    private static final String DEFAULT_CONTENT_TYPE = "text/plain";
    private static final Log log = LogFactory.getLog(EmailSender.class);

    @Resource(lookup = "java:jboss/mail/Default")
    private Session mailSession;

    private Queue<EmailMessage> messagesToResend;

    @PostConstruct
    public void init() {
        messagesToResend = new LinkedList<>();
    }

    @Schedule(minute = "20", hour = "*", persistent = false)
    public void tryResendCachedEmails() {

        List<EmailMessage> successfullySent = Lists.newArrayList();
        for (EmailMessage emailMessage : messagesToResend) {
            try {
                boolean sent = sendEmail(emailMessage.getSubject(), emailMessage.getContent(), emailMessage.getType());
                if (sent) {
                    successfullySent.add(emailMessage);
                }
            } catch (Exception e) {
                log.error("Exception occurred while resending email", e);
                break;
            }
        }

        messagesToResend.removeAll(successfullySent);
    }

    public boolean sendEmail(String subject, String content) {
        return sendEmail(subject, content, DEFAULT_CONTENT_TYPE);
    }


    public boolean sendEmail(String content) {
        return sendEmail(DEFAULT_SUBJECT, content);
    }

    public boolean sendEmail(List<String> content, String subject) {
        if (!content.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String contentElement : content) {
                sb.append(contentElement).append(CommonConstants.NEW_LINE_SYMBOL);
            }
            return sendEmail(subject, sb.toString());
        }
        return false;
    }

    public boolean sendEmail(List<String> content) {
        return sendEmail(content, DEFAULT_SUBJECT + DateUtil.getCurrentDateInFormat("yyyy-MM-dd"));
    }

    public boolean sendEmail(String subject, String content, String type) {
        String emailRecipients;
        try {
            if (!Boolean.valueOf(ConfigurationProvider.getProperty(CommonConstants.SEND_EMAIL_PROPERTY))) {
                log.warn("Sending emails is disabled.");
                return false;
            }

            emailRecipients = ConfigurationProvider.getProperty(CommonConstants.EMAIL_RECIPIENTS_PROPERTY);

            if (Strings.isNullOrEmpty(emailRecipients)) {
                log.warn("Empty recipients list. Email will not be sent.");
                return false;
            }

            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(
                    ConfigurationProvider.getProperty(CommonConstants.EMAIL_SENDER_PROPERTY));
            List<InternetAddress> recipients = new ArrayList<>();
            for (String emailRecipient : emailRecipients.split(",")) {
                recipients.add(new InternetAddress(emailRecipient));
            }

            Address[] to = recipients.toArray(new Address[recipients.size()]);
            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, to);
            m.setSubject(subject);
            m.setContent(content, type);
            Transport.send(m);
        } catch (Exception e) {
            log.error("Exception occurred while sending email", e);
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setContent(content);
            emailMessage.setType(type);
            emailMessage.setSubject(subject);
            if (!messagesToResend.contains(emailMessage)) {
                log.warn("Adding email to cache to resend, subject: " + subject);
                messagesToResend.add(emailMessage);
            }
            return false;


        }
        return true;

    }


    private static class EmailMessage {
        private String subject;
        private String type;
        private String content;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EmailMessage)) return false;

            EmailMessage that = (EmailMessage) o;

            if (!subject.equals(that.subject)) return false;
            return type.equals(that.type) && content.equals(that.content);

        }

        @Override
        public int hashCode() {
            int result = subject.hashCode();
            result = 31 * result + type.hashCode();
            result = 31 * result + content.hashCode();
            return result;
        }
    }
}
