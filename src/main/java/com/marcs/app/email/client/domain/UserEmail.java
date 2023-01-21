package com.marcs.app.email.client.domain;

import java.time.LocalDateTime;

import com.sendgrid.Email;

/**
 * User Email class request that is used to send an email to users.
 */
public class UserEmail {
    private Email from;

    private Email recipient;

    private String subject;

    private String body;

    private LocalDateTime sentDate;

    public UserEmail() {
    }

    public UserEmail(Email recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public Email getFrom() {
        return from;
    }

    public void setFrom(Email from) {
        this.from = from;
    }

    public Email getRecipient() {
        return recipient;
    }

    public void setRecipient(Email recipient) {
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
