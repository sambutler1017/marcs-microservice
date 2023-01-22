package com.marcs.common.abstracts;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.marcs.common.page.Page;
import com.opengamma.elsql.ElSqlBundle;
import com.opengamma.elsql.ElSqlConfig;

/**
 * Abstract class for building the DAO classes and running queries against the
 * database.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Service
@Scope("prototype")
public abstract class AbstractSqlDao extends AbstractSqlGlobals {

    private final NamedParameterJdbcTemplate template;
    private final ElSqlBundle bundle;

    public AbstractSqlDao() {
        this.template = null;
        this.bundle = null;
    }

    public AbstractSqlDao(NamedParameterJdbcTemplate template, ElSqlConfig config) {
        this.template = template;
        this.bundle = ElSqlBundle.of(config, this.getClass());
    }

    /**
     * Gets the default template for the datasource.
     * 
     * @return {@link NamedParameterJdbcTemplate} object.
     */
    public NamedParameterJdbcTemplate getTemplate() {
        return template;
    }

    /**
     * Does a get on the database for a single record. It will return the top most
     * record if multiple rows are returned.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     * @param mapper The mapper to return the data as.
     * @return Object of the returned data.
     */
    public <T> T get(String sql, MapSqlParameterSource params, RowMapper<T> mapper) {
        return getTemplate().queryForObject(sql, params, mapper);
    }

    /**
     * Does a get on the database for a single record. It will return the top most
     * record if multiple rows are returned. It will return the type of the passed
     * in class.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     * @param clazz  The class to map the data as.
     * @return Object of the returned data.
     */
    public <T> T get(String sql, MapSqlParameterSource params, Class<T> clazz) {
        return getTemplate().queryForObject(sql, params, clazz);
    }

    /**
     * Querys the database for a list of data. It will return the data as a list of
     * the called object.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     * @param mapper The mapper to return the data as.
     * @return List of the returned data.
     */
    public <T> List<T> getList(String sql, MapSqlParameterSource params, RowMapper<T> mapper) {
        return getTemplate().query(sql, params, mapper);
    }

    /**
     * Querys the database for a page of data. It will return the data as a list of
     * the called object.
     * 
     * @param <T>    The object type of the method to cast the rows too.
     * @param name   The sql Fragment Name.
     * @param params Params to be inserted into the query.
     * @param mapper The mapper to return the data as.
     * @return List of the returned data.
     */
    public <T> Page<T> getPage(String name, MapSqlParameterSource params, RowMapper<T> mapper) {
        return getPage(name + "TotalCount", name, params, mapper);
    }

    /**
     * Querys the database for a page of data. It will return the data as a list of
     * the called object.
     * 
     * @param <T>           The object type of the method to cast the rows too.
     * @param totalCountSql The total count sql fragment.
     * @param sql           The sql Fragment Name.
     * @param params        Params to be inserted into the query.
     * @param mapper        The mapper to return the data as.
     * @return List of the returned data.
     */
    private <T> Page<T> getPage(String totalCountSql, String sql, MapSqlParameterSource params, RowMapper<T> mapper) {
        int totalCount = get(getSql(totalCountSql, params), params, Integer.class);
        List<T> list = getList(getSql(sql, params), params, mapper);
        return new Page<T>(totalCount, list);
    }

    /**
     * Does an insertion into the database with the given sql and params. It will
     * also get the auto incremented id of the table with the key holder.
     * 
     * @param sql       The sql to run against the database.
     * @param params    Params to be inserted into the query.
     * @param keyHolder used to get the auto increment id.
     */
    public int post(String sql, MapSqlParameterSource params, KeyHolder keyHolder) {
        return getTemplate().update(sql, params, keyHolder);
    }

    /**
     * Does an insertion into the database with the given sql and params.
     * 
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     */
    public int post(String sql, MapSqlParameterSource params) {
        return getTemplate().update(sql, params);
    }

    /**
     * Performs a delete on the database for the given sql.
     * 
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     */
    public int delete(String sql, MapSqlParameterSource params) {
        return getTemplate().update(sql, params);
    }

    /**
     * Performs an update against the database.
     * 
     * @param sql    The sql to run against the database.
     * @param params Params to be inserted into the query.
     */
    public int update(String sql, MapSqlParameterSource params) {
        return getTemplate().update(sql, params);
    }

    /**
     * Will execute the given sql string against the active database.
     * 
     * @param sql The sql to be run.
     */
    protected void execute(String sql) {
        getTemplate().update(sql, new MapSqlParameterSource());
    }

    /**
     * Gets the sql fragement for the given name and filters out any parameters that
     * don't exist in the fragment.
     * 
     * @param name   The name of the sql fragement.
     * @param params The params to filter out of the query.
     * @return {@link String} of the filtered query.
     */
    protected String getSql(String name, SqlParameterSource params) {
        return bundle.getSql(name, params).trim();
    }

    /**
     * Gets the raw sql fragement string for the given name.
     * 
     * @param name The name of the sql fragement.
     * @return {@link String} of the sql fragment.
     */
    protected String getSql(String name) {
        return bundle.getSql(name).trim();
    }

    /**
     * Creates a new {@link MapSqlParameterSource} object with the given name and
     * value.
     * 
     * @param name  Name of the map to store the value under.
     * @param value What value to store in the map.
     * @return {@link MapSqlParameterSource} instance
     */
    protected MapSqlParameterSource parameterSource(String name, Object value) {
        return new MapSqlParameterSource(name, value);
    }
}
