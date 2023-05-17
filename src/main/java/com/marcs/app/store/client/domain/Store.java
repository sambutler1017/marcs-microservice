/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.store.client.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to represent a store object
 * 
 * @author Sam Butler
 * @since May 11, 2021
 */
@Schema(description = "Store data object.")
public class Store {

	@Schema(defaultValue = "Unique id of the store.")
	private String id;

	@JsonInclude(Include.NON_DEFAULT)
	@Schema(description = "The regional manager id of the store.")
	private int regionalManagerId;

	@JsonInclude(Include.NON_DEFAULT)
	@Schema(description = "The manage id of the store.")
	private int managerId;

	@Schema(description = "The store name.")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRegionalManagerId() {
		return regionalManagerId;
	}

	public void setRegionalManagerId(int regionalManagerId) {
		this.regionalManagerId = regionalManagerId;
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
