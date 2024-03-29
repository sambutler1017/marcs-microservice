/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.reports.dao;

import static com.marcs.app.vacation.mapper.VacationMapper.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class VacationReportsDao extends BaseDao {

        public VacationReportsDao(DataSource source) {
                super(source);
        }

        /**
         * Get list of vacations for the current request.
         * 
         * @return {@link Vacation} object.
         */
        public List<Vacation> getVacations(VacationGetRequest request) throws Exception {
                MapSqlParameterSource params = SqlParamBuilder.with(request).withParam(ID, request.getId())
                                .withParam(USER_ID, request.getUserId())
                                .withParam(REGIONAL_MANAGER_ID, request.getRegionalManagerId())
                                .withParam(STORE_ID, request.getStoreId())
                                .withParamTextEnumCollection(WEB_ROLE_TEXT_ID, request.getWebRole())
                                .withParamTextEnumCollection(STATUS, request.getStatus()).build();
                return getList("getVacations", params, VACATION_MAPPER);
        }
}
