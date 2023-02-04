/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.vacation.client.domain.request;

import java.util.Set;

import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;
import com.marcs.common.search.CommonParam;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Vacation Request object for getting vacations
 * 
 * @author Sam Butler
 * @since September 21, 2021
 */
@Schema(description = "Vacation get request object.")
public class VacationGetRequest implements CommonParam {

    @Schema(description = "Set of vacation ids.")
    private Set<Integer> id;

    @Schema(description = "Set of regional ids.")
    private Set<Integer> regionalId;

    @Schema(description = "Set of user ids.")
    private Set<Integer> userId;

    @Schema(description = "Set of vacation status.")
    private Set<VacationStatus> status;

    @Schema(description = "Set of store ids.")
    private Set<String> storeId;

    @Schema(description = "Set of web roles.")
    private Set<WebRole> webRole;

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

    public Set<String> getStoreId() {
        return storeId;
    }

    public void setStoreId(Set<String> storeId) {
        this.storeId = storeId;
    }

    public Set<WebRole> getWebRole() {
        return webRole;
    }

    public void setWebRole(Set<WebRole> webRole) {
        this.webRole = webRole;
    }
}
