package com.marcs.app.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.marcs.app.requestTracker.client.domain.RequestStatus;
import com.marcs.app.requestTracker.client.domain.RequestType;
import com.marcs.app.requestTracker.client.domain.UserRequest;
import com.marcs.common.abstracts.AbstractMapper;
import com.marcs.common.mapping.DefaultMapper;

/**
 * Request Vacation for user request objects.
 * 
 * @author Sam Butler
 * @since January 12, 2023
 */
@Service
public class UserRequestMapper<T> extends AbstractMapper<UserRequest<T>> {
    private UserRequest<T> newInstance;
    private Class<T> type;

    public UserRequestMapper() {}

    public UserRequestMapper(Class<T> clazz) {
        this.newInstance = new UserRequest<T>();
        this.type = clazz;
    }

    public UserRequest<T> mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRequest<T> request = newInstance;
        request.setRequestId(rs.getInt(REQUEST_ID));
        request.setUserId(rs.getInt(USER_ID));
        request.setType(RequestType.valueOf(rs.getString(TYPE)));
        request.setStatus(RequestStatus.valueOf(rs.getString(STATUS)));
        request.setNotes(rs.getString(NOTES));
        request.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));

        try {
            request.setRequestData(DefaultMapper.objectMapper().readValue(rs.getString(DATA), this.type));
        }
        catch(Exception e) {
            request.setRequestData(null);
        }
        return request;
    }
}