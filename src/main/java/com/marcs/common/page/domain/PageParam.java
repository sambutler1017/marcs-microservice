package com.marcs.common.page.domain;

import com.marcs.common.search.CommonParam;

/**
 * Page Param to add to get request objects.
 * 
 * @author Sam Butler
 * @since January 22, 2023
 */
public interface PageParam extends CommonParam {
    public int getRowOffset();

    public int getPageSize();
}
