package com.marcs.app.blockOutDate.dao;

import java.util.List;

import com.marcs.app.blockOutDate.client.domain.BlockOutDate;
import com.marcs.app.blockOutDate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.common.abstracts.AbstractSqlDao;

import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for block out dates
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class BlockOutDateDao extends AbstractSqlDao {

	/**
	 * Endpoint to get a list of block out dates based on the filter request
	 * 
	 * @param request to filter stores on
	 * @return List of block out date objects {@link BlockOutDate}
	 * @throws Exception
	 */
	public List<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) throws Exception {
		return null;
	}
}
