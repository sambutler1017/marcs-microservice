package com.marcs.app.auth.dao;

import static com.marcs.app.auth.mapper.AuthPasswordMapper.AUTH_PASSWORD_MAPPER;
import static com.marcs.app.user.mapper.UserProfileMapper.USER_MAPPER;

import java.io.IOException;

import javax.sql.DataSource;

import com.marcs.app.auth.client.domain.AuthPassword;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.exceptions.InvalidCredentialsException;
import com.marcs.common.exceptions.SqlFragmentNotFoundException;
import com.marcs.common.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

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
     * Not an exposed endpoint, strictly used by the authentication controller to
     * autheticate a user.
     * 
     * @param email    To search for in the database
     * @param password The password to validate against
     * @return User object if the user credentials are correct.
     * @throws SqlFragmentNotFoundException
     * @throws IOException
     */
    public User authenticateUser(String email, String password) throws Exception {
        try {
            return get(getSql("authenticateUser"),
                    parameterSource("email", email).addValue("password", password), USER_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCredentialsException("Invalid Credentials!");
        }
    }

    /**
     * Get the hashed password and salt value associated to the user.
     * 
     * @param email The email assocaited with the user.
     * @return {@link long} of the 10 digit salt value.
     * @throws Exception If there is not user for the given email.
     */
    public AuthPassword getUserAuthPassword(String email) throws Exception {
        try {
            return get(getSql("getUserAuthenticationSalt"), parameterSource("email", email),
                    AUTH_PASSWORD_MAPPER);
        } catch (Exception e) {
            throw new UserNotFoundException(String.format("User not found for email: %s", email));
        }
    }
}
