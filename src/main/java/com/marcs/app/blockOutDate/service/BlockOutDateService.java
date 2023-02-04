/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.blockoutdate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcs.app.blockoutdate.client.domain.BlockOutDate;
import com.marcs.app.blockoutdate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.app.blockoutdate.dao.BlockOutDateDao;
import com.marcs.common.page.Page;

/**
 * Block out date Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class BlockOutDateService {

	@Autowired
	private BlockOutDateDao dao;

	/**
	 * Endpoint to get a list of block out dates based on the filter request
	 * 
	 * @param request to filter stores on
	 * @return List of block out date objects {@link BlockOutDate}
	 */
	public Page<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) {
		return dao.getBlockOutDates(request);
	}

	/**
	 * Get a single block out date information by id.
	 * 
	 * @param request to filter stores on
	 * @return Block out date object {@link BlockOutDate}
	 */
	public BlockOutDate getBlockOutDateById(int id) throws Exception {
		try {
			BlockOutDateGetRequest request = new BlockOutDateGetRequest();
			request.setId(Sets.newHashSet(id));
			return getBlockOutDates(request).getList().get(0);
		}
		catch(Exception e) {
			throw new Exception(String.format("Block out date id '%d' does not exist!", id));
		}

	}
}
