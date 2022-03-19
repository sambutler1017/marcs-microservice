package com.marcs.app.reports.dao;

import static com.marcs.app.user.mapper.UserProfileMapper.USER_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class UserReportsDao extends BaseDao {

        @Autowired
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
                SqlParamBuilder builder = SqlParamBuilder.with(request).useAllParams().withParam("id", request.getId())
                                .withParam("email", request.getEmail()).withParam("storeId",
                                                request.getStoreId())
                                .withParam("regionalId", request.getRegionalId()).withParam("firstName",
                                                request.getFirstName())
                                .withParam("lastName", request.getLastName()).withParam("storeName",
                                                request.getStoreName())
                                .withParam("emailReportsEnabled", request.getEmailReportsEnabled())
                                .withParamTextEnumCollection("accountStatus", request.getAccountStatus())
                                .withParam("excludedUserIds", request.getExcludedUserIds())
                                .withParamTextEnumCollection("webRole", request.getWebRole());
                MapSqlParameterSource params = builder.build();
                return getPage(getSql("getUsers", params), params, USER_MAPPER);
        }
}
