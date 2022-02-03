package com.marcs.app.user.dao;

import javax.sql.DataSource;

import com.marcs.app.auth.client.domain.AuthPassword;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.abstracts.BaseDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

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
     * @param userId   The id to add the password for.
     * @param authPass Contains the hashed password and salt value.
     * @throws Exception
     */
    public void insertUserPassword(int userId, AuthPassword authPass) throws Exception {
        MapSqlParameterSource params = parameterSource("userId", userId)
                .addValue("password", authPass.getPassword()).addValue("salt", authPass.getSalt());
        post(getSql("insertUserPassword", params), params);
    }

    /**
     * Update the users password, for the given password.
     * 
     * @param userId   Id of the use that is being updated.
     * @param password The password to set on the user profile.
     * @param salt     The salt value that was appended to the password.
     * @return user associated to that id with the updated information
     * @throws Exception
     */
    public User updateUserPassword(int userId, AuthPassword authPassword) throws Exception {
        User userProfile = userProfileDao.getUserById(userId);

        MapSqlParameterSource params = parameterSource("password", authPassword.getPassword())
                .addValue("id", userProfile.getId()).addValue("salt", authPassword.getSalt());
        update(getSql("updateUserPassword", params), params);
        return userProfile;
    }
}
