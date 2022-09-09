package com.marcs.app.user.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.marcs.app.user.client.domain.User;
import com.marcs.common.abstracts.BaseDao;

/**
 * Class for handling the dao calls for a user credentials
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Repository
public class UserCredentialsDao extends BaseDao {

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    public UserCredentialsDao(DataSource source) {
        super(source);
    }

    /**
     * This will be called when new users are created so that they have default
     * passwords. This will only be called when someone else is creating a user
     * account for someone.
     * 
     * @param userId     The id to add the password for.
     * @param hashedPass Contains the hashed password.
     * @throws Exception
     */
    public void insertUserPassword(int userId, String hashedPass) throws Exception {
        MapSqlParameterSource params = parameterSource(USER_ID, userId).addValue(PASSWORD, hashedPass);
        post(getSql("insertUserPassword", params), params);
    }

    /**
     * Update the users password, for the given password.
     * 
     * @param userId     Id of the use that is being updated.
     * @param hashedPass The password to set on the user profile.
     * @return user associated to that id with the updated information
     * @throws Exception
     */
    public User updateUserPassword(int userId, String hashedPass) throws Exception {
        User userProfile = userProfileDao.getUserById(userId);

        MapSqlParameterSource params = parameterSource(PASSWORD, hashedPass).addValue(USER_ID, userProfile.getId());
        update(getSql("updateUserPassword", params), params);
        return userProfile;
    }
}
