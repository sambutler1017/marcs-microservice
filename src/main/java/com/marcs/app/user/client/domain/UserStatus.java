package com.marcs.app.user.client.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.marcs.common.enums.AccountStatus;

/**
 * Class to create a user status object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserStatus {
    private int requestId;

    private int userId;

    private AccountStatus accountStatus;

    private Boolean appAccess;

    @JsonInclude(Include.NON_DEFAULT)
    private Integer updatedUserId;

    public UserStatus() {

    }

    public UserStatus(int userId, AccountStatus accountStatus, Boolean appAccess) {
        this.userId = userId;
        this.accountStatus = accountStatus;
        this.appAccess = appAccess;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Boolean isAppAccess() {
        return appAccess;
    }

    public void setAppAccess(Boolean appAccess) {
        this.appAccess = appAccess;
    }

    public Integer getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Integer updatedUserId) {
        this.updatedUserId = updatedUserId;
    }
}
