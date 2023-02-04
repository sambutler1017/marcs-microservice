/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.store.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcs.common.page.domain.PageParam;
import com.marcs.common.search.SearchField;
import com.marcs.common.search.SearchFieldParams;
import com.marcs.common.search.SearchParam;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Schema(description = "Store get request object.")
public class StoreGetRequest implements SearchParam, PageParam, SearchFieldParams<StoreSearchFields> {

    @Schema(description = "Set of unique store ids.")
    private Set<String> id;

    @Schema(description = "Set of Regional ids.")
    private Set<Integer> regionalId;

    @Schema(description = "Set of Manager ids.")
    private Set<Integer> managerId;

    @Schema(description = "Set of store names.")
    private Set<String> name;

    @Schema(description = "Search criteria for the store request.")
    private String search;

    @Schema(description = "The page size to be returned.")
    private int pageSize;

    @Schema(description = "The row offset of the data to start at.")
    private int rowOffset;

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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(StoreSearchFields.values());
    }
}
