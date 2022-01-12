package com.marcs.app.store.client.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class to represent a store object
 * 
 * @author Sam Butler
 * @since May 11, 2021
 */
public class Store {
	private String id;

	@JsonInclude(Include.NON_DEFAULT)
	private int regionalId;

	@JsonInclude(Include.NON_DEFAULT)
	private int managerId;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRegionalId() {
		return regionalId;
	}

	public void setRegionalId(int regionalId) {
		this.regionalId = regionalId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
