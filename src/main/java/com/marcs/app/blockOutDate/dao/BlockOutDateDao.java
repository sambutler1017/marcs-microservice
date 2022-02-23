package com.marcs.app.blockOutDate.dao;

import static com.marcs.app.blockOutDate.mapper.BlockOutDateMapper.BLOCK_OUT_DATE_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import com.google.common.collect.Sets;
import com.marcs.app.blockOutDate.client.domain.BlockOutDate;
import com.marcs.app.blockOutDate.client.domain.request.BlockOutDateGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for block out dates
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class BlockOutDateDao extends BaseDao {

	@Autowired
	public BlockOutDateDao(DataSource source) {
		super(source);
	}

	/**
	 * Endpoint to get a list of block out dates based on the filter request
	 * 
	 * @param request to filter stores on
	 * @return List of block out date objects {@link BlockOutDate}
	 * @throws Exception
	 */
	public List<BlockOutDate> getBlockOutDates(BlockOutDateGetRequest request) throws Exception {
		SqlParamBuilder builder = SqlParamBuilder.with(request).withParam("id", request.getId())
				.withParam("insertUserId", request.getInsertUserId());

		MapSqlParameterSource params = builder.build();

		return getPage(getSql("getBlockOutDates", params), params, BLOCK_OUT_DATE_MAPPER);
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
	public BlockOutDate updateBlockOutDateById(int id, BlockOutDate currentBlockOutDate, BlockOutDate blockDate)
			throws Exception {

		blockDate = mapNonNullBlockOutDateFields(blockDate, currentBlockOutDate);

		SqlParamBuilder builder = SqlParamBuilder.with().withParam("startDate", blockDate.getStartDate())
				.withParam("endDate", blockDate.getEndDate())
				.withParam("insertUserId", blockDate.getInsertUserId()).withParam("id", id);
		MapSqlParameterSource params = builder.build();
		update(getSql("updateBlockOutDateById", params), params);

		blockDate.setId(id);
		return blockDate;
	}

	/**
	 * This will create a new block out date with the passed in request body
	 * 
	 * @param blockDate The block out date that needs to be created.
	 * @return The block out date with the insert time stamp and unique id.
	 * @throws Exception
	 */
	public BlockOutDate createBlockOutDate(BlockOutDate blockDate) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParamBuilder builder = SqlParamBuilder.with().withParam("startDate", blockDate.getStartDate())
				.withParam("endDate", blockDate.getEndDate())
				.withParam("insertUserId", blockDate.getInsertUserId());

		MapSqlParameterSource params = builder.build();

		post(getSql("insertBlockOutDate"), params, keyHolder);
		return getBlockOutDates(new BlockOutDateGetRequest(Sets.newHashSet(keyHolder.getKey().intValue()))).get(0);
	}

	/**
	 * Delete a block out date for the given id.
	 * 
	 * @param id The id of the block out date to be deleted.
	 * @throws Exception
	 */
	public void deleteBlockOutDate(int id) throws Exception {
		delete(getSql("deleteBlockOutDate"), parameterSource("id", id));
	}

	/**
	 * Maps non null block out date fields from the source to the desitnation.
	 * 
	 * @param destination Where the null fields should be replaced.
	 * @param source      Where to get the replacements for the null fields.
	 * @return {@link BlockOutDate} with the replaced fields.
	 */
	private BlockOutDate mapNonNullBlockOutDateFields(BlockOutDate destination, BlockOutDate source) {
		if (destination.getStartDate() == null)
			destination.setStartDate(source.getStartDate());
		if (destination.getEndDate() == null)
			destination.setEndDate(source.getEndDate());
		if (destination.getInsertUserId() == 0)
			destination.setInsertUserId(source.getInsertUserId());
		return destination;
	}
}
