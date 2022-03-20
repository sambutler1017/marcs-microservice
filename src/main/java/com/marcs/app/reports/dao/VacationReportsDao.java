package com.marcs.app.reports.dao;

import static com.marcs.app.vacation.mapper.VacationMapper.VACATION_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import com.marcs.app.vacation.client.domain.Vacation;
import com.marcs.app.vacation.client.domain.request.VacationGetRequest;
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
public class VacationReportsDao extends BaseDao {

        @Autowired
        public VacationReportsDao(DataSource source) {
                super(source);
        }

        /**
         * Get list of vacations for the current request.
         * 
         * @return {@link Vacation} object.
         * @throws Exception
         */
        public List<Vacation> getVacations(VacationGetRequest request) throws Exception {
                SqlParamBuilder builder = SqlParamBuilder.with(request).withParam("id", request.getId())
                                .withParam("userId", request.getUserId())
                                .withParam("regionalId", request.getRegionalId())
                                .withParam("storeId", request.getStoreId())
                                .withParamTextEnumCollection("status", request.getStatus());
                MapSqlParameterSource params = builder.build();
                return getPage(getSql("getVacations", params), params, VACATION_MAPPER);
        }
}
