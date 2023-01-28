/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.common.abstracts;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.opengamma.elsql.ElSqlConfig;

/**
 * Base DAO class containing the shared methods between daos.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
public abstract class BaseDao extends AbstractSqlDao {

    public BaseDao(DataSource source) {
        super(new NamedParameterJdbcTemplate(source), ElSqlConfig.MYSQL);
    }
}
