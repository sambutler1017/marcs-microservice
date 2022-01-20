package com.marcs.app.blockOutDate.client;

import java.util.List;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.blockOutDate.client.domain.BlockOutDate;
import com.marcs.app.blockOutDate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.app.blockOutDate.rest.BlockOutDateController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class exposes the block out date endpoint's to other app's to pull data
 * across the platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class BlockOutDateClient {

	@Autowired
	private BlockOutDateController controller;

	/**
	 * Endpoint to get a list of block out dates based on the filter request
	 * 
	 * @param request to filter block out dates on
	 * @return List of block out date objects {@link BlockOutDate}
	 * @throws Exception
	 */
	public List<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) throws Exception {
		return controller.getBlockOutDates(request);
	}

	/**
	 * Get a single block out date information by id.
	 * 
	 * @param request to filter stores on
	 * @return Block out date object {@link BlockOutDate}
	 * @throws Exception
	 */
	public BlockOutDate getBlockOutDateById(int id) throws Exception {
		return controller.getBlockOutDateById(id);
	}

	/**
	 * This will create a new block out date with the passed in request body
	 * 
	 * @param blockDate The block out date that needs to be created.
	 * @return The block out date with the insert time stamp and unique id.
	 * @throws Exception
	 */
	public BlockOutDate createBlockOutDate(BlockOutDate blockDate) throws Exception {
		return controller.createBlockOutDate(blockDate);
	}

	/**
	 * Delete a block out date for the given id.
	 * 
	 * @param id The id of the block out date to be deleted.
	 * @throws Exception
	 */
	public void deleteBlockOutDate(int id) throws Exception {
		controller.deleteBlockOutDate(id);
	}
}
