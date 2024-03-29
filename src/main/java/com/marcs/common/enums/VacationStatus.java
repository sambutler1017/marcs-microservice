/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.common.enums;

/**
 * Enum for tracking vacation status.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum VacationStatus implements TextEnum {
    APPROVED("APPROVED"),
    DENIED("DENIED"),
    PENDING("PENDING"),
    EXPIRED("EXPIRED");

    private String textId;

    VacationStatus(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}
