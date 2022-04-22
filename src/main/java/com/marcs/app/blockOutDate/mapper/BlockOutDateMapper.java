package com.marcs.app.blockOutDate.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.blockOutDate.client.domain.BlockOutDate;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a Block Out Date Object {@link BlockOutDate}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class BlockOutDateMapper implements RowMapper<BlockOutDate> {
	public static BlockOutDateMapper BLOCK_OUT_DATE_MAPPER = new BlockOutDateMapper();

	public BlockOutDate mapRow(ResultSet rs, int rowNum) throws SQLException {
		BlockOutDate blockOutDate = new BlockOutDate();
		blockOutDate.setId(rs.getInt("id"));
		blockOutDate.setInsertUserId(rs.getInt("insert_user_id"));
		blockOutDate.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
		blockOutDate.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
		blockOutDate.setInsertDate(rs.getTimestamp("insert_date").toLocalDateTime());
		return blockOutDate;
	}
}