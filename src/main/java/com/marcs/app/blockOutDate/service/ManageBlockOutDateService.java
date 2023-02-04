/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.blockoutdate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcs.app.blockoutdate.client.domain.BlockOutDate;
import com.marcs.app.blockoutdate.dao.BlockOutDateDao;
import com.marcs.common.enums.WebRole;
import com.marcs.jwt.utility.JwtHolder;

/**
 * Block out date Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class ManageBlockOutDateService {

	@Autowired
	private BlockOutDateDao dao;

	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private BlockOutDateService blockOutDateService;

	/**
	 * Method to update the given block out date with the passed in body and the
	 * given id.
	 * 
	 * @param id        The id of the block out date to update.
	 * @param blockDate The block out date body to be updated.
	 * @return {@link BlockOutDate} with the updated fields.
	 */
	public BlockOutDate updateBlockOutDateById(int id, BlockOutDate blockDate) throws Exception {
		blockDate.setInsertUserId(jwtHolder.getUserId());
		blockDate.setId(id);
		return dao.updateBlockOutDateById(id, blockOutDateService.getBlockOutDateById(id), blockDate);
	}

	/**
	 * This will create a new block out date with the passed in request body
	 * 
	 * @param blockDate The block out date that needs to be created.
	 * @return The block out date with the insert time stamp and unique id.
	 */
	public BlockOutDate createBlockOutDate(BlockOutDate blockDate) throws Exception {
		if(jwtHolder.getWebRole().getRank() < WebRole.DISTRICT_MANAGER.getRank()) {
			throw new Exception(String.format(	"User with role '%s' does not have permission to create block out dates!",
												jwtHolder.getWebRole().toString()));
		}

		blockDate.setInsertUserId(jwtHolder.getUserId());
		return blockOutDateService.getBlockOutDateById(dao.createBlockOutDate(blockDate));
	}

	/**
	 * Delete a block out date for the given id.
	 * 
	 * @param id The id of the block out date to be deleted.
	 */
	public void deleteBlockOutDate(int id) throws Exception {
		blockOutDateService.getBlockOutDateById(id);
		dao.deleteBlockOutDate(id);
	}
}
