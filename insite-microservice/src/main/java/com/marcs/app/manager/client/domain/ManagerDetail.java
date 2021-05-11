package com.marcs.app.manager.client.domain;

import java.util.List;

import com.marcs.app.vacation.client.domain.Vacation;

/**
 * Class to create a manager detail object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ManagerDetail {
	public int id;
	public int regionalId;
	public String name;
	public String storeId;
	public String storeName;
	public String hireDate;
	public boolean storeManager;
	public List<Vacation> vacations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRegionalId() {
		return regionalId;
	}

	public void setRegionalId(int regionalId) {
		this.regionalId = regionalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public boolean isStoreManager() {
		return storeManager;
	}

	public void setStoreManager(boolean storeManager) {
		this.storeManager = storeManager;
	}

	public List<Vacation> getVacations() {
		return vacations;
	}

	public void setVacations(List<Vacation> vacations) {
		this.vacations = vacations;
	}
}
