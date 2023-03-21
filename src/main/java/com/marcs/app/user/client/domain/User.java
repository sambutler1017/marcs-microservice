/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.user.client.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.marcs.common.enums.AccountStatus;
import com.marcs.common.enums.WebRole;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a user profile object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "User data object.")
public class User {

	@Schema(description = "The unique id of the user.")
	private int id;

	@Schema(description = "The user first name.")
	private String firstName;

	@Schema(description = "The user last name.")
	private String lastName;

	@Schema(description = "The user email address.")
	private String email;

	@Schema(description = "The web role of the user.")
	private WebRole webRole;

	@Schema(description = "The access of the user to the website.")
	private Boolean appAccess;

	@Schema(description = "User email reports enabled for the user.")
	private Boolean emailReportsEnabled;

	@Schema(description = "The password of the user.")
	private String password;

	@Schema(description = "What store the user belongs too, if exists.")
	private String storeId;

	@Schema(description = "The store name the user belongs too.")
	private String storeName;

	@Schema(description = "The user account status.")
	private AccountStatus accountStatus;

	@Schema(description = "When the user was hired.")
	private LocalDate hireDate;

	@Schema(description = "The users last login date and time.")
	private LocalDateTime lastLoginDate;

	@Schema(description = "When the user was created.")
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

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}
}
