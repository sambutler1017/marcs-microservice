package com.marcs.app.vacation.client.domain.request;

import java.util.Set;

import com.marcs.common.enums.VacationStatus;
import com.marcs.common.search.CommonParam;

/**
 * Vacation Request object for getting vacations
 * 
 * @author Sam Butler
 * @since September 21, 2021
 */
public class VacationRequest implements CommonParam {

    private Set<Integer> id;

    private Set<Integer> regionalId;

    private Set<Integer> userId;

    private Set<VacationStatus> status;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<Integer> getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(Set<Integer> regionalId) {
        this.regionalId = regionalId;
    }

    public Set<Integer> getUserId() {
        return userId;
    }

    public void setUserId(Set<Integer> userId) {
        this.userId = userId;
    }

    public Set<VacationStatus> getStatus() {
        return status;
    }

    public void setStatus(Set<VacationStatus> status) {
        this.status = status;
    }
}
