package com.marcs.app.vacation.client.domain;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;

/**
 * Class to create a vacation object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class Vacation {

	private int id;

	private int userId;

	private String fullName;

	private WebRole webRole;

	private String storeId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDateTime startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDateTime endDate;

	private String notes;

	private LocalDateTime insertDate;

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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startString) {
		this.startDate = startString;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endString) {
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
