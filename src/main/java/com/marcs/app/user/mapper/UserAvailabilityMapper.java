package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;

import com.marcs.app.user.client.domain.UserAvailability;
import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.common.abstracts.AbstractMapper;

/**
 * Mapper class to map a User status Object {@link UserStatus}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserAvailabilityMapper extends AbstractMapper<UserAvailability> {
    public static UserAvailabilityMapper USER_AVAILABILITY_MAPPER = new UserAvailabilityMapper();

    public UserAvailability mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserAvailability userAvailability = new UserAvailability();

        userAvailability.setId(rs.getInt(ID));
        userAvailability.setUserId(rs.getInt(USER_ID));
        userAvailability.setWeekDay(DayOfWeek.valueOf(rs.getString(WEEK_DAY)));
        userAvailability.setStartTime(parseTime(rs.getString(START_TIME)));
        userAvailability.setEndTime(parseTime(rs.getString(END_TIME)));
        userAvailability.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));

        return userAvailability;
    }
}