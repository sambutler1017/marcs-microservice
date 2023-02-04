/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.common.search;

import java.util.List;

public interface SearchFieldParams<T extends SearchField> {
    public List<SearchField> getSearchFields();
}
