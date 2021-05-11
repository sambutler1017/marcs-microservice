package com.marcs.app.vacation.client.domain;

/**
 * Class to create a vacation object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class Vacation {
	public int id;
	public String startDate;
	public String endDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startString) {
		this.startDate = startString;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endString) {
		this.endDate = endString;
	}
}
