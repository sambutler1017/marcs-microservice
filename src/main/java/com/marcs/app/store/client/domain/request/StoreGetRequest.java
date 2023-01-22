package com.marcs.app.store.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcs.common.page.domain.PageParam;
import com.marcs.common.search.SearchField;
import com.marcs.common.search.SearchFieldParams;
import com.marcs.common.search.SearchParam;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
public class StoreGetRequest implements SearchParam, PageParam, SearchFieldParams<StoreSearchFields> {
    private Set<String> id;

    private Set<Integer> regionalId;

    private Set<Integer> managerId;

    private Set<String> name;

    private String search;

    private int pageSize;

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
