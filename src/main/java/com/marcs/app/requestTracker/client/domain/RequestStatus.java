/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.requestTracker.client.domain;

import com.marcs.common.enums.TextEnum;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum to handle the status of a users request.
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Schema(description = "The status of the request.")
public enum RequestStatus implements TextEnum {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DENIED("DENIED");

    private String textId;

    RequestStatus(String textId) {
        this.textId = textId;
    }

    @Override
    public String getTextId() {
        return textId;
    }
}