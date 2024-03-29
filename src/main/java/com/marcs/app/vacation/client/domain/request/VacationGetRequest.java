/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.vacation.client.domain.request;

import java.util.Set;

import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;
import com.marcs.common.page.domain.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Vacation Request object for getting vacations
 * 
 * @author Sam Butler
 * @since September 21, 2021
 */
@Schema(description = "Vacation get request object.")
public class VacationGetRequest implements PageParam {

    @Schema(description = "Set of vacation ids.")
    private Set<Integer> id;

    @Schema(description = "Set of regional manager ids.")
    private Set<Integer> regionalManagerId;

    @Schema(description = "Set of user ids.")
    private Set<Integer> userId;

    @Schema(description = "Set of vacation status.")
    private Set<VacationStatus> status;

    @Schema(description = "Set of store ids.")
    private Set<String> storeId;

    @Schema(description = "Set of web roles.")
    private Set<WebRole> webRole;

    @Schema(description = "The page size to be returned.")
    private int pageSize;

    @Schema(description = "The row offset of the data to start at.")
    private int rowOffset;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<Integer> getRegionalManagerId() {
        return regionalManagerId;
    }

    public void setRegionalManagerId(Set<Integer> regionalManagerId) {
        this.regionalManagerId = regionalManagerId;
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

    public int getRowOffset() {
        return rowOffset;
    }

    public void setRowOffset(int rowOffset) {
        this.rowOffset = rowOffset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
