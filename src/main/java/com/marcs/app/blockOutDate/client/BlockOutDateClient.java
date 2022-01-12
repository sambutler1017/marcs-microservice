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
	 * @param request to filter stores on
	 * @return List of block out date objects {@link BlockOutDate}
	 * @throws Exception
	 */
	public List<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) throws Exception {
		return controller.getBlockOutDates(request);
	}
}
