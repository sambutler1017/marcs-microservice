package com.marcs.app.user.dao;

import static com.marcs.app.user.mapper.UserAvailabilityMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.marcs.app.user.client.domain.UserAvailability;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.page.Page;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class UserAvailabilityDao extends BaseDao {

    public UserAvailabilityDao(DataSource source) {
        super(source);
    }

    /**
     * Get list of user availability by user id
     * 
     * @param id The user id
     * @return Page of user availability
     */
    public Page<UserAvailability> getUserAvailabilityById(int id) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(USER_ID, id).build();
        return getPage("getUserAvailabilityPage", params, USER_AVAILABILITY_MAPPER);
    }

    /**
     * Insert a user availability for the given user id.
     * 
     * @param userAvailability The avaiablity object for a user.
     * @return The created availability object with the attached creation id.
     */
    public UserAvailability createUserAvailability(UserAvailability userAvailability) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().useAllParams()
                .withParam(USER_ID, userAvailability.getUserId())
                .withParam(WEEK_DAY, userAvailability.getWeekDay().name())
                .withParam(START_TIME, userAvailability.getStartTime())
                .withParam(END_TIME, userAvailability.getEndTime()).build();

        post("createUserAvailability", params, keyHolder);
        userAvailability.setId(keyHolder.getKey().intValue());
        return userAvailability;
    }
}