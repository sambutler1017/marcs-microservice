package com.marcs.common.enums;

/**
 * Enum for keeping track of notification types
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
public enum NotificationType implements TextEnum {
    USER("USER"), VACATION("VACATION");

    private String textId;

    NotificationType(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
