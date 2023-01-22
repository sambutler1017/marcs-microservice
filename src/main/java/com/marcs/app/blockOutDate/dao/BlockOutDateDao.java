package com.marcs.app.blockOutDate.dao;

import static com.marcs.app.blockOutDate.mapper.BlockOutDateMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Sets;
import com.marcs.app.blockOutDate.client.domain.BlockOutDate;
import com.marcs.app.blockOutDate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.common.abstracts.BaseDao;
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
	public List<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) {
		SqlParamBuilder builder = SqlParamBuilder.with(request).withParam(ID, request.getId())
				.withParam(INSERT_USER_ID, request.getInsertUserId());

		MapSqlParameterSource params = builder.build();

		return getList("getBlockOutDates", params, BLOCK_OUT_DATE_MAPPER);
	}

	/**
	 * Method to update the given block out date with the passed in body and the
	 * given id.
	 * 
	 * @param id        The id of the block out date to update.
	 * @param blockDate The block out date body to be updated.
	 * @return {@link BlockOutDate} with the updated fields.
	 */
	public BlockOutDate updateBlockOutDateById(int id, BlockOutDate currentBlockOutDate, BlockOutDate blockDate)
			throws Exception {

		blockDate = mapNonNullBlockOutDateFields(blockDate, currentBlockOutDate);

		SqlParamBuilder builder = SqlParamBuilder.with().withParam(ID, id)
				.withParam(START_DATE, blockDate.getStartDate()).withParam(END_DATE, blockDate.getEndDate())
				.withParam(INSERT_USER_ID, blockDate.getInsertUserId());

		MapSqlParameterSource params = builder.build();
		update("updateBlockOutDateById", params);

		blockDate.setId(id);
		return blockDate;
	}

	/**
	 * This will create a new block out date with the passed in request body
	 * 
	 * @param blockDate The block out date that needs to be created.
	 * @return The block out date with the insert time stamp and unique id.
	 */
	public BlockOutDate createBlockOutDate(BlockOutDate blockDate) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParamBuilder builder = SqlParamBuilder.with().withParam(START_DATE, blockDate.getStartDate())
				.withParam(END_DATE, blockDate.getEndDate()).withParam(INSERT_USER_ID, blockDate.getInsertUserId());

		MapSqlParameterSource params = builder.build();

		post("insertBlockOutDate", params, keyHolder);
		return getBlockOutDates(new BlockOutDateGetRequest(Sets.newHashSet(keyHolder.getKey().intValue()))).get(0);
	}

	/**
	 * Delete a block out date for the given id.
	 * 
	 * @param id The id of the block out date to be deleted.
	 */
	public void deleteBlockOutDate(int id) {
		delete("deleteBlockOutDate", parameterSource(ID, id));
	}

	/**
	 * Maps non null block out date fields from the source to the desitnation.
	 * 
	 * @param destination Where the null fields should be replaced.
	 * @param source      Where to get the replacements for the null fields.
	 * @return {@link BlockOutDate} with the replaced fields.
	 */
	private BlockOutDate mapNonNullBlockOutDateFields(BlockOutDate destination, BlockOutDate source) {
		if(destination.getStartDate() == null) destination.setStartDate(source.getStartDate());
		if(destination.getEndDate() == null) destination.setEndDate(source.getEndDate());
		if(destination.getInsertUserId() == 0) destination.setInsertUserId(source.getInsertUserId());
		return destination;
	}
}
