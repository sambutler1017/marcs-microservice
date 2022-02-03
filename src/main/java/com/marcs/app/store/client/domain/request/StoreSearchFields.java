package com.marcs.app.store.client.domain.request;

import com.marcs.common.search.SearchField;

/**
 * Defines search fields for a store.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
public enum StoreSearchFields implements SearchField {
    STORE_ID("st.id"), STORE_NAME("st.name");

    private String column;

    StoreSearchFields(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
