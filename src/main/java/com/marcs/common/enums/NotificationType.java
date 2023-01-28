/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.common.enums;

/**
 * Enum for keeping track of notification types
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
public enum NotificationType implements TextEnum {
    USER("USER", "User"),
    VACATION("VACATION", "Vacation"),
    REQUEST_TRACKER("REQUEST_TRACKER", "Request Tracker");

    private String textId;

    private String translation;

    NotificationType(String textId, String translation) {
        this.textId = textId;
        this.translation = translation;
    }

    @Override
    public String getTextId() {
        return textId;
    }

    public String getTranslation() {
        return translation;
    }
}
