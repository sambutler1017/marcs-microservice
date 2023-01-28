/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.store.client.domain.request;

import com.marcs.common.search.SearchField;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines search fields for a store.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
@Schema(description = "The store search fields.")
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
