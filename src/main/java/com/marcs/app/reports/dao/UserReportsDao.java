package com.marcs.app.reports.dao;

import static com.marcs.app.user.mapper.UserProfileMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class UserReportsDao extends BaseDao {

        public UserReportsDao(DataSource source) {
                super(source);
        }

        /**
         * Get users based on given request filter.
         * 
         * @param request of the user
         * @return User profile object {@link User}
         * @throws Exception
         */
        public List<User> getUsers(UserGetRequest request) throws Exception {
                MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
                                .withParam(ID, request.getId()).withParam(EMAIL, request.getEmail())
                                .withParam(STORE_ID, request.getStoreId())
                                .withParam(REGIONAL_ID, request.getRegionalId())
                                .withParam(FIRST_NAME, request.getFirstName())
                                .withParam(LAST_NAME, request.getLastName())
                                .withParam(STORE_NAME, request.getStoreName())
                                .withParam(EMAIL_REPORTS_ENABLED, request.getEmailReportsEnabled())
                                .withParam("excludedUserIds", request.getExcludedUserIds())
                                .withParamTextEnumCollection(ACCOUNT_STATUS, request.getAccountStatus())
                                .withParamTextEnumCollection(WEB_ROLE_TEXT_ID, request.getWebRole()).build();
                return getList("getUsers", params, USER_MAPPER);
        }
}
