package com.marcs.app.user.client.domain.request;

import com.marcs.common.search.SearchField;

/**
 * Defines search fields for a user.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
public enum UserProfileSearchFields implements SearchField {
    STORE_ID("st.id"), STORE_NAME("st.name"), FIRST_NAME("up.first_name"), LAST_NAME("up.last_name");

    private String column;

    UserProfileSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
