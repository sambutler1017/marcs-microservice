package com.marcs.app.store.client.domain.request;

import java.util.Set;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
public class StoreGetRequest {
    private Set<String> id;

    private Set<Integer> regionalId;

    private Set<Integer> managerId;

    private Set<String> name;

    public Set<String> getId() {
        return id;
    }

    public void setId(Set<String> id) {
        this.id = id;
    }

    public Set<Integer> getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(Set<Integer> regionalId) {
        this.regionalId = regionalId;
    }

    public Set<Integer> getManagerId() {
        return managerId;
    }

    public void setManagerId(Set<Integer> managerId) {
        this.managerId = managerId;
    }

    public Set<String> getName() {
        return name;
    }

    public void setName(Set<String> name) {
        this.name = name;
    }
}
