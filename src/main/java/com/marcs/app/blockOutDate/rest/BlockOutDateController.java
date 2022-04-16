package com.marcs.app.blockOutDate.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.blockOutDate.client.domain.BlockOutDate;
import com.marcs.app.blockOutDate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.app.blockOutDate.service.BlockOutDateService;
import com.marcs.app.blockOutDate.service.ManageBlockOutDateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestApiController
@RequestMapping("api/block-out-date-app/block-out-dates")
public class BlockOutDateController {

	@Autowired
	private BlockOutDateService blockOutDateService;

	@Autowired
	private ManageBlockOutDateService manageBlockOutDateService;

	/**
	 * Endpoint to get a list of block out dates based on the filter request
	 * 
	 * @param request to filter block out dates on
	 * @return List of block out date objects {@link BlockOutDate}
	 * @throws Exception
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) throws Exception {
		return blockOutDateService.getBlockOutDates(request);
	}

	/**
	 * Get a single block out date information by id.
	 * 
	 * @param request to filter stores on
	 * @return Block out date object {@link BlockOutDate}
	 * @throws Exception
	 */
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public BlockOutDate getBlockOutDateById(@PathVariable int id) throws Exception {
		return blockOutDateService.getBlockOutDateById(id);
	}

	/**
	 * Method to update the given block out date with the passed in body and the
	 * given id.
	 * 
	 * @param id        The id of the block out date to update.
	 * @param blockDate The block out date body to be updated.
	 * @return {@link BlockOutDate} with the updated fields.
	 * @throws Exception
	 */
	@PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public BlockOutDate updateBlockOutDateById(@PathVariable int id, @RequestBody BlockOutDate blockDate)
			throws Exception {
		return manageBlockOutDateService.updateBlockOutDateById(id, blockDate);
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
		return manageBlockOutDateService.createBlockOutDate(blockDate);
	}

	/**
	 * Delete a block out date for the given id.
	 * 
	 * @param id The id of the block out date to be deleted.
	 * @throws Exception
	 */
	@DeleteMapping(path = "/{id}")
	public void deleteBlockOutDate(@PathVariable int id) throws Exception {
		manageBlockOutDateService.deleteBlockOutDate(id);
	}
}
