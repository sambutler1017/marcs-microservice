package com.marcs.common.enums;

/**
 * Enum for keeping track of notification types
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
public enum NotificationType {
    USER(1), VACATION(2);

    private int id;

    NotificationType(int type) {
        this.id = type;
    }

    public int id() {
        return id;
    }

    public static NotificationType getNotificationType(int id) {
        for (NotificationType w : NotificationType.values())
            if (w.id == id)
                return w;
        return USER;
    }

    public int getValue() {
        return id;
    }
}
