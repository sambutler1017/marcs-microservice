package com.marcs.app.user.client.domain;

import com.marcs.service.enums.StoreRegion;
import com.marcs.service.enums.WebRole;

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

	private StoreRegion storeRegion;

	private int appAccess;

	private String username;
	
	private String passoword;

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

	public StoreRegion getStoreRegion() {
		return storeRegion;
	}

	public void setStoreRegion(StoreRegion storeRegion) {
		this.storeRegion = storeRegion;
	}

	public int getAppAccess() {
		return appAccess;
	}

	public void setAppAccess(int appAccess) {
		this.appAccess = appAccess;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassoword() {
		return passoword;
	}

	public void setPassoword(String passoword) {
		this.passoword = passoword;
	}
}
