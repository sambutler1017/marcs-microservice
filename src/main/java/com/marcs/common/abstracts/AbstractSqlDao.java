package com.marcs.common.abstracts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import com.marcs.common.exceptions.SqlFragmentNotFoundException;
import com.marcs.service.activeProfile.ActiveProfile;
import com.marcs.sql.SqlClient;
import com.marcs.sql.domain.SqlFragmentData;
import com.marcs.sql.domain.SqlParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Abstract class for building the DAO classes and running queries against the
 * database
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Service
public abstract class AbstractSqlDao {

    private final String defaultSqlPath = "%s/resources/dao/%s.sql";

    private String sqlFilePath;

    @Autowired
    protected ActiveProfile activeProfile;

    @Autowired
    protected SqlClient sqlClient;

    public AbstractSqlDao() {
        sqlFilePath = new ActiveProfile().getEnvironmentUrl();
    }

    public AbstractSqlDao(String sqlFilePath) {
        this.sqlFilePath = sqlFilePath;
    }

    /**
     * Gets the sql based on the given fragment name.
     * 
     * @param fragmentName Name of the sql fragment to search for.
     * @return {@link AbstractSqlDao} instance containing the sql string.
     * @throws SqlFragmentNotFoundException If the fragment can not be found in the
     *                                      given file.
     * @throws IOException
     */
    protected SqlFragmentData getSql(String fragmentName) throws Exception {
        return getQueryFromFile(fragmentName, getChildClassName());
    }

    /**
     * Gets the sql based on the given fragment name.
     * 
     * @param fragmentName Name of the sql fragment to search for.
     * @return {@link AbstractSqlDao} instance containing the sql string.
     * @throws SqlFragmentNotFoundException If the fragment can not be found in the
     *                                      given file.
     * @throws IOException
     */
    protected SqlFragmentData getSql(String fragmentName, String fileName) throws Exception {
        return getQueryFromFile(fragmentName, fileName);
    }

    /**
     * Gets a {@link SqlParams} object to store the params to be used on the sql
     * query.
     * 
     * @param name  The name of the field to store the object under.
     * @param value The object to store in the params.
     * @return Instance of {@link SqlParams} object with the added param.
     */
    protected SqlParams params(String name, Object value) {
        return new SqlParams(name, value);
    }

    /**
     * Gets the sql based on the given fragment name and name of the file to search
     * in.
     * 
     * @param fragmentName Name of the sql fragment to search for.
     * @param fileName     Name of the file to search in.
     * @return {@link List<String>} of the query found.
     * @throws IOException If the file can't be found or Reader can't be closed.
     */
    private SqlFragmentData getQueryFromFile(String fragmentName, String fileName) throws IOException {
        String filePath = String.format(defaultSqlPath, sqlFilePath, getChildClassName());
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        SqlFragmentData result = new SqlFragmentData(br.lines().collect(Collectors.toList()), fragmentName);

        br.close();
        return result;
    }

    /**
     * This will get the name of the child class. This is used when finding what
     * file the sql fragment is contained in.
     * 
     * @return {@link String} of the filename
     * @see #getSql(String)
     */
    private String getChildClassName() {
        return this.getClass().getSimpleName();
    }
}
