/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.requestTracker.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to represent a request object.
 * 
 * @author Sam Butler
 * @since May 11, 2021
 */
@Schema(description = "User Request object.")
public class UserRequest<T> {

	@Schema(description = "The unique request id.")
	private int requestId;

	@Schema(description = "The user id the request belongs too.")
	private int userId;

	@Schema(description = "The request type.")
	private RequestType type;

	@Schema(description = "The request status.")
	private RequestStatus status;

	@Schema(description = "Any notes attached to the requeset.")
	private String notes;

	@Schema(description = "The request data.")
	private T requestData;

	@Schema(description = "When the user request was inserted.")
	private LocalDateTime insertDate;

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

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public T getRequestData() {
		return requestData;
	}

	public void setRequestData(T requestData) {
		this.requestData = requestData;
	}

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}
}
