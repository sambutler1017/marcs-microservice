package com.marcs.app.email.client.domain;

import java.time.LocalDateTime;

/**
 * User Email class request that is used to send an email to users.
 */
public class UserEmail {
    private String from;

    private String recipient;

    private String subject;

    private String body;

    private LocalDateTime sentDate;

    public UserEmail() {}

    public UserEmail(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

}
