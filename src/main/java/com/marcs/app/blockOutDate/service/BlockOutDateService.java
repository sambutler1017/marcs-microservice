package com.marcs.app.blockOutDate.service;

import java.util.List;

import com.google.common.collect.Sets;
import com.marcs.app.blockOutDate.client.domain.BlockOutDate;
import com.marcs.app.blockOutDate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.app.blockOutDate.dao.BlockOutDateDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	 * @throws Exception
	 */
	public List<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) throws Exception {
		return dao.getBlockOutDates(request);
	}

	/**
	 * Get a single block out date information by id.
	 * 
	 * @param request to filter stores on
	 * @return Block out date object {@link BlockOutDate}
	 * @throws Exception
	 */
	public BlockOutDate getBlockOutDateById(int id) throws Exception {
		try {
			return getBlockOutDates(new BlockOutDateGetRequest(Sets.newHashSet(id))).get(0);
		} catch (Exception e) {
			throw new Exception(String.format("Block out date id '%d' does not exist!", id));
		}

	}
}
