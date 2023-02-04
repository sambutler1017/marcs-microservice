/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.requestTracker.client.domain;

import com.marcs.common.enums.TextEnum;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User Request Type
 * 
 * @author Sam Butler
 * @since January 12, 2023
 */
@Schema(description = "The type of request.")
public enum RequestType implements TextEnum {
    VACATION("VACATION", "Vacation"),
    STORE("STORE", "Store");

    private String textId;

    private String translation;

    RequestType(String textId, String translation) {
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
