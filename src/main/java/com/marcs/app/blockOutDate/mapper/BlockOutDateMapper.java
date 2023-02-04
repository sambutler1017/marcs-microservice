/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.blockoutdate.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.blockoutdate.client.domain.BlockOutDate;
import com.marcs.common.abstracts.AbstractMapper;

/**
 * Mapper class to map a Block Out Date Object {@link BlockOutDate}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class BlockOutDateMapper extends AbstractMapper<BlockOutDate> {
	public static BlockOutDateMapper BLOCK_OUT_DATE_MAPPER = new BlockOutDateMapper();

	public BlockOutDate mapRow(ResultSet rs, int rowNum) throws SQLException {
		BlockOutDate blockOutDate = new BlockOutDate();
		blockOutDate.setId(rs.getInt(ID));
		blockOutDate.setInsertUserId(rs.getInt(INSERT_USER_ID));
		blockOutDate.setStartDate(parseDate(rs.getString(START_DATE)));
		blockOutDate.setEndDate(parseDate(rs.getString(END_DATE)));
		blockOutDate.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));
		return blockOutDate;
	}
}