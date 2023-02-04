/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.vacation.client.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a vacation object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Vacation data object.")
public class Vacation {

	@Schema(description = "Unique id of the vacation.")
	private int id;

	@Schema(description = "The user id the vacation belongs too.")
	private int userId;

	@Schema(description = "The full name of the user.")
	private String fullName;

	@Schema(description = "The web role of the user.")
	private WebRole webRole;

	@Schema(description = "The store id of the vacation.")
	private String storeId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Schema(description = "The start date of the vacation.")
	private LocalDate startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Schema(description = "The end date of the vacation.")
	private LocalDate endDate;

	@Schema(description = "Notes on the vacation.")
	private String notes;

	@Schema(description = "When the vacation was created.")
	private LocalDateTime insertDate;

	@Schema(description = "The status of the vaction.")
	private VacationStatus status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startString) {
		this.startDate = startString;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endString) {
		this.endDate = endString;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public VacationStatus getStatus() {
		return status;
	}

	public void setStatus(VacationStatus status) {
		this.status = status;
	}

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}
}
