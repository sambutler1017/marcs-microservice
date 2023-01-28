/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.app.blockoutdate.dao;

import static com.marcs.app.blockoutdate.mapper.BlockOutDateMapper.BLOCK_OUT_DATE_MAPPER;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.marcs.app.blockoutdate.client.domain.BlockOutDate;
import com.marcs.app.blockoutdate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.page.Page;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for block out dates
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class BlockOutDateDao extends BaseDao {

	public BlockOutDateDao(DataSource source) {
		super(source);
	}

	/**
	 * Endpoint to get a list of block out dates based on the filter request
	 * 
	 * @param request to filter stores on
	 * @return List of block out date objects {@link BlockOutDate}
	 */
	public Page<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) {
		MapSqlParameterSource params = SqlParamBuilder.with(request).usePagenation().withParam(ID, request.getId())
				.withParam(INSERT_USER_ID, request.getInsertUserId()).build();

		return getPage("getBlockOutDatesPage", params, BLOCK_OUT_DATE_MAPPER);
	}

	/**
	 * Method to update the given block out date with the passed in body and the
	 * given id.
	 * 
	 * @param id        The id of the block out date to update.
	 * @param blockDate The block out date body to be updated.
	 * @return {@link BlockOutDate} with the updated fields.
	 */
	public BlockOutDate updateBlockOutDateById(int id, BlockOutDate currentBlockOutDate, BlockOutDate blockDate) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id)
				.withParam(START_DATE, blockDate.getStartDate())
				.withParam(END_DATE, blockDate.getEndDate())
				.withParam(INSERT_USER_ID, blockDate.getInsertUserId()).build();

		update("updateBlockOutDateById", params);

		blockDate.setId(id);
		return blockDate;
	}

	/**
	 * This will create a new block out date with the passed in request body
	 * 
	 * @param blockDate The block out date that needs to be created.
	 * @return The block out date unique id.
	 */
	public int createBlockOutDate(BlockOutDate blockDate) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(START_DATE, blockDate.getStartDate())
				.withParam(END_DATE, blockDate.getEndDate()).withParam(INSERT_USER_ID, blockDate.getInsertUserId())
				.build();

		post("insertBlockOutDate", params, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * Delete a block out date for the given id.
	 * 
	 * @param id The id of the block out date to be deleted.
	 */
	public void deleteBlockOutDate(int id) {
		delete("deleteBlockOutDate", parameterSource(ID, id));
	}
}
