package com.marcs.app.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.common.enums.AccountStatus;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapper class to map a User status Object {@link UserStatus}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserStatusMapper implements RowMapper<UserStatus> {
    public static UserStatusMapper USER_STATUS_MAPPER = new UserStatusMapper();

    public UserStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserStatus userStatus = new UserStatus();

        userStatus.setRequestId(rs.getInt("request_id"));
        userStatus.setUserId(rs.getInt("user_id"));
        userStatus.setAccountStatus(AccountStatus.valueOf(rs.getString("account_status")));
        userStatus.setAppAccess(rs.getBoolean("app_access"));
        userStatus.setUpdatedUserId(rs.getInt("updated_user_id"));

        return userStatus;
    }
}
