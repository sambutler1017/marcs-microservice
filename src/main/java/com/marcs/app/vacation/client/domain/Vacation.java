package com.marcs.app.vacation.client.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcs.common.enums.VacationStatus;
import com.marcs.common.enums.WebRole;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date endDate;

	private String notes;

	private Date insertDate;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startString) {
		this.startDate = startString;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endString) {
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

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
}
