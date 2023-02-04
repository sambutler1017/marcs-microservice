/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.blockoutdate.client.domain.request;

import java.util.Set;

import com.marcs.common.page.domain.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Schema(description = "Block out date get request.")
public class BlockOutDateGetRequest implements PageParam {

    @Schema(description = "Set of unique ids.")
    private Set<Integer> id;

    @Schema(description = "Set of insert user ids.")
    private Set<Integer> insertUserId;

    @Schema(description = "Page size of the return data.")
    private int pageSize;

    @Schema(description = "Row offset to start the data from.")
    private int rowOffset;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<Integer> getInsertUserId() {
        return insertUserId;
    }

    public void setInsertUserId(Set<Integer> insertUserId) {
        this.insertUserId = insertUserId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public void setRowOffset(int rowOffset) {
        this.rowOffset = rowOffset;
    }
}
