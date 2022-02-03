package com.marcs.app.user.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcs.common.enums.AccountStatus;
import com.marcs.common.enums.WebRole;
import com.marcs.common.search.SearchField;
import com.marcs.common.search.SearchFieldParams;
import com.marcs.common.search.SearchParam;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
public class UserGetRequest implements SearchParam, SearchFieldParams<UserProfileSearchFields> {

    private Set<Integer> id;

    private Set<String> firstName;

    private Set<String> lastName;

    private Set<String> email;

    private Set<WebRole> webRole;

    private Set<String> storeId;

    private Set<String> storeName;

    private Set<String> regionalId;

    private Set<AccountStatus> accountStatus;

    private Set<Integer> excludedUserIds;

    private Boolean notificationsEnabled;

    private String search;

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

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(UserProfileSearchFields.values());
    }
}