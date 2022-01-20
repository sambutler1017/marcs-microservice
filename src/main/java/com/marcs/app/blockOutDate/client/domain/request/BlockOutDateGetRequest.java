package com.marcs.app.blockOutDate.client.domain.request;

import java.util.Set;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
public class BlockOutDateGetRequest {

    private Set<Integer> id;

    private Set<Integer> insertUserId;

    public BlockOutDateGetRequest() {

    }

    public BlockOutDateGetRequest(Set<Integer> id) {
        this.id = id;
    }

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
}
