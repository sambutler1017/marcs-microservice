/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.jwt.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Jwt type to confirm what type of jwt token is being used.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@Schema(description = "The user JWT type.")
public enum JwtType {
    WEB("WEB_JWT");

    private String textId;

    private JwtType(String textId) {
        this.textId = textId;
    }

    public String getTextId() {
        return textId;
    }
}
