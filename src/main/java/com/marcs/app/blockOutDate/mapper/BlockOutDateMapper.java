package com.marcs.app.blockOutDate.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
		blockOutDate.setStartDate(LocalDate.parse(rs.getDate("start_date").toString()));
		blockOutDate.setEndDate(LocalDate.parse(rs.getDate("end_date").toString()));
		blockOutDate.setInsertUserId(rs.getInt("insert_user_id"));
		blockOutDate.setInsertDate(LocalDate.parse(rs.getDate("insert_date").toString()));
		return blockOutDate;
	}
}