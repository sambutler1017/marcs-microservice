package com.marcs.app.blockOutDate.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.blockOutDate.client.domain.BlockOutDate;
import com.marcs.app.blockOutDate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.app.blockOutDate.service.BlockOutDateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestApiController
@RequestMapping("api/block-out-date-app/block-out-dates")
public class BlockOutDateController {

	@Autowired
	private BlockOutDateService service;

	/**
	 * Endpoint to get a list of block out dates based on the filter request
	 * 
	 * @param request to filter stores on
	 * @return List of block out date objects {@link BlockOutDate}
	 * @throws Exception
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) throws Exception {
		return service.getBlockOutDates(request);
	}

	/**
	 * This will create a new block out date with the passed in request body
	 * 
	 * @param blockDate The block out date that needs to be created.
	 * @return The block out date with the insert time stamp and unique id.
	 * @throws Exception
	 */
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	public BlockOutDate createBlockOutDate(@RequestBody BlockOutDate blockDate) throws Exception {
		return service.createBlockOutDate(blockDate);
	}
}
