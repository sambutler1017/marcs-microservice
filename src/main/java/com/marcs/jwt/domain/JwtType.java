package com.marcs.jwt.domain;

/**
 * Jwt type to confirm what type of jwt token is being used.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
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
