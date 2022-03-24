package com.marcs.websockets.client.domain;

public enum WebNotificationType {
    MESSAGE("message");

    private String text;

    WebNotificationType(String type) {
        this.text = type;
    }

    public String text() {
        return text;
    }
}
