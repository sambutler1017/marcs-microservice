package com.marcs.app.user.client.domain.request;

public class UserCredentialsGetRequest {
	private int userId = -1;
	private String username;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
