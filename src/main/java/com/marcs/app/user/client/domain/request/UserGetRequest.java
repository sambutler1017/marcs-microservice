/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.user.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.enums.WebRole;
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
@Schema(description = "User get request object.")
public class UserGetRequest implements SearchParam, PageParam, SearchFieldParams<UserProfileSearchFields> {

    @Schema(description = "Set Unique user ids.")
    private Set<Integer> id;

    @Schema(description = "Set of first names.")
    private Set<String> firstName;

    @Schema(description = "Set of last names.")
    private Set<String> lastName;

    @Schema(description = "Set of emails.")
    private Set<String> email;

    @Schema(description = "Set of web roles.")
    private Set<WebRole> webRole;

    @Schema(description = "Set of Store ID's.")
    private Set<String> storeId;

    @Schema(description = "Set of Store Names.")
    private Set<String> storeName;

    @Schema(description = "Set of regional ID's")
    private Set<String> regionalId;

    @Schema(description = "Set of Account Status values.")
    private Set<AccountStatus> accountStatus;

    @Schema(description = "Set of integers of user ids to exclude.")
    private Set<Integer> excludedUserIds;

    @Schema(description = "Users that have email reports enabled.")
    private Boolean emailReportsEnabled;

    @Schema(description = "Search Param on search param fields.")
    private String search;

    @Schema(description = "Row Offset for pagenation.")
    private int rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private int pageSize;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(Set<String> firstName) {
        this.firstName = firstName;
    }

    public Set<String> getLastName() {
        return lastName;
    }

    public void setLastName(Set<String> lastName) {
        this.lastName = lastName;
    }

    public Set<String> getEmail() {
        return email;
    }

    public void setEmail(Set<String> email) {
        this.email = email;
    }

    public Set<WebRole> getWebRole() {
        return webRole;
    }

    public void setWebRole(Set<WebRole> webRole) {
        this.webRole = webRole;
    }

    public Set<String> getStoreId() {
        return storeId;
    }

    public void setStoreId(Set<String> storeId) {
        this.storeId = storeId;
    }

    public Set<String> getStoreName() {
        return storeName;
    }

    public void setStoreName(Set<String> storeName) {
        this.storeName = storeName;
    }

    public Set<String> getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(Set<String> regionalId) {
        this.regionalId = regionalId;
    }

    public Set<AccountStatus> getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Set<AccountStatus> accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Set<Integer> getExcludedUserIds() {
        return excludedUserIds;
    }

    public void setExcludedUserIds(Set<Integer> excludedUserIds) {
        this.excludedUserIds = excludedUserIds;
    }

    public Boolean getEmailReportsEnabled() {
        return emailReportsEnabled;
    }

    public void setEmailReportsEnabled(Boolean emailReportsEnabled) {
        this.emailReportsEnabled = emailReportsEnabled;
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
        return Arrays.asList(UserProfileSearchFields.values());
    }
}