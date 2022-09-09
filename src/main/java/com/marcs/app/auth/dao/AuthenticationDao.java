package com.marcs.app.auth.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.exceptions.UserNotFoundException;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2021
 */
@Repository
public class AuthenticationDao extends BaseDao {

    @Autowired
    public AuthenticationDao(DataSource source) {
        super(source);
    }

    /**
     * Get the hashed password associated to the user.
     * 
     * @param email The email assocaited with the user.
     * @return {@link String} hashed password
     * @throws Exception If there is not user for the given email.
     */
    public String getUserAuthPassword(String email) throws Exception {
        try {
            return get(getSql("getUserHashedPassword"), parameterSource(EMAIL, email), String.class);
        }
        catch(Exception e) {
            throw new UserNotFoundException(String.format("User not found for email: %s", email));
        }
    }
}
