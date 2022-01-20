package com.marcs.app.blockOutDate.dao;

import static com.marcs.app.blockOutDate.mapper.BlockOutDateMapper.BLOCK_OUT_DATE_MAPPER;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Sets;
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
		return sqlClient.getPage(getSql("getBlockOutDates"),
				params("id", request.getId()).addValue("insertUserId", request.getInsertUserId()),
				BLOCK_OUT_DATE_MAPPER);
	}

	/**
	 * This will create a new block out date with the passed in request body
	 * 
	 * @param blockDate The block out date that needs to be created.
	 * @return The block out date with the insert time stamp and unique id.
	 * @throws Exception
	 */
	public BlockOutDate createBlockOutDate(BlockOutDate blockDate) throws Exception {
		Optional<Integer> autoId = sqlClient.post(getSql("insertBlockOutDate"),
				params("startDate", blockDate.getStartDate()).addValue("endDate", blockDate.getEndDate())
						.addValue("insertUserId", blockDate.getInsertUserId()));
		if (autoId.isPresent()) {
			return getBlockOutDates(new BlockOutDateGetRequest(Sets.newHashSet(autoId.get()))).get(0);
		} else {
			throw new Exception("Could not create block out date");
		}
	}

	/**
	 * Delete a block out date for the given id.
	 * 
	 * @param id The id of the block out date to be deleted.
	 * @throws Exception
	 */
	public void deleteBlockOutDate(int id) throws Exception {
		sqlClient.delete(getSql("deleteBlockOutDate"), params("id", id));
	}
}
