/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.auth.dao;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.marcs.common.abstracts.BaseDao;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2021
 */
@Repository
public class AuthenticationDao extends BaseDao {

    public AuthenticationDao(DataSource source) {
        super(source);
    }

    /**
     * Get the hashed password associated to the user.
     * 
     * @param email The email assocaited with the user.
     * @return {@link String} hashed password
     */
    public Optional<String> getUserAuthPassword(String email) {
        return getOptional("getUserHashedPassword", parameterSource(EMAIL, email), String.class);
    }
}
