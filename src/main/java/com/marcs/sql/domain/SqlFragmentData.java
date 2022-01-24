package com.marcs.sql.domain;

import java.util.List;

/**
 * Sql Fragment object for storing the sql's stream contents.
 * 
 * @author Sam Butler
 * @since January 24, 2022
 */
public class SqlFragmentData {

    private List<String> sql;

    private String fragment;

    public SqlFragmentData() {

    }

    public SqlFragmentData(List<String> sql, String fragment) {
        this.sql = sql;
        this.fragment = fragment;
    }

    public List<String> getSql() {
        return sql;
    }

    public void setSql(List<String> sql) {
        this.sql = sql;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }
}
