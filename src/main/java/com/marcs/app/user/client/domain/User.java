package com.marcs.app.user.client.domain;

import java.time.LocalDateTime;

import com.marcs.common.enums.AccountStatus;
import com.marcs.common.enums.WebRole;

/**
 * Class to create a user profile object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class User {
	private int id;

	private String firstName;

	private String lastName;

	private String email;

	private WebRole webRole;

	private Boolean appAccess;

	private Boolean emailReportsEnabled;

	private String password;

	private String storeId;

	private String storeName;

	private AccountStatus accountStatus;

	private LocalDateTime lastLoginDate;

	private LocalDateTime hireDate;

	private LocalDateTime insertDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	public Boolean isAppAccess() {
		return appAccess;
	}

	public void setAppAccess(Boolean appAccess) {
		this.appAccess = appAccess;
	}

	public Boolean isEmailReportsEnabled() {
		return emailReportsEnabled;
	}

	public void setEmailReportsEnabled(Boolean emailReportsEnabled) {
		this.emailReportsEnabled = emailReportsEnabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public LocalDateTime getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDateTime hireDate) {
		this.hireDate = hireDate;
	}

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}
}
