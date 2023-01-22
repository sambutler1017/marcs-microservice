package com.marcs.app.blockOutDate.client.domain.request;

import java.util.Set;

import com.marcs.common.page.domain.PageParam;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
public class BlockOutDateGetRequest implements PageParam {

    private Set<Integer> id;

    private Set<Integer> insertUserId;

    private int pageSize;

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
