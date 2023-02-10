package com.marcs.app.auth.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.marcs.test.factory.annotations.MarcsDaoTest;
import com.marcs.test.factory.utility.MarcsDAOTestConfig;

/**
 * Test class for the Authenticate Dao.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@ContextConfiguration(classes = MarcsDAOTestConfig.class)
@MarcsDaoTest
public class AuthenticationDaoTest {

    @Autowired
    private AuthenticationDao dao;

    @Test
    public void testGetUserAuthPassword() {
        int test = 1;
    }
}
