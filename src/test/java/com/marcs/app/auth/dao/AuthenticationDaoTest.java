package com.marcs.app.auth.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.marcs.test.factory.annotations.MarcsDaoTest;
import com.marcs.test.factory.utility.MarcsDAOTestConfig;

/**
 * Test class for the Authenticate Dao.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@Sql("/scripts/auth/authenticationDao/init.sql")
@ContextConfiguration(classes = MarcsDAOTestConfig.class)
@MarcsDaoTest
public class AuthenticationDaoTest {

    @Autowired
    private AuthenticationDao dao;

    @Test
    public void testGetUserAuthPassword() {
        Optional<String> userAuthPassword = dao.getUserAuthPassword("test@mail.com");

        assertTrue(userAuthPassword.isPresent(), "User Auth Password Present");
        assertTrue(BCrypt.checkpw("password", userAuthPassword.get()), "Password Check");
    }

    @Test
    public void testGetUserAuthPasswordEmailNotExist() {
        Optional<String> userAuthPassword = dao.getUserAuthPassword("fake@mail.com");

        assertFalse(userAuthPassword.isPresent(), "User Auth Password NOT Present");
    }

    @Test
    public void testGetUserAuthPassUserNotApproved() {
        Optional<String> userAuthPassword = dao.getUserAuthPassword("noAccess@mail.com");

        assertFalse(userAuthPassword.isPresent(), "User Auth Password NOT Present");
    }
}
