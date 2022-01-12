package com.marcs.app.auth.service;

import com.marcs.app.auth.client.domain.AuthPassword;
import com.marcs.app.auth.dao.AuthenticationDao;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.jwt.utility.JwtHolder;
import com.marcs.service.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authorization Service takes a user request and checks the values entered for
 * credentials against known values in the database. If correct credentials are
 * passed, it will grant access to the user requested.
 *
 * @author Sam Butler
 * @since August 2, 2021
 */
@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationDao authDao;

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private UserProfileClient userClient;

    /**
     * Verifies user credentials passed as a JWTRequest
     *
     * @param email    Entered email at login.
     * @param password Password entered at login.
     * @throws Exception Throw an exception if the credentials do not match.
     */
    public User verifyUser(String email, String password) throws Exception {
        return authDao.authenticateUser(email,
                PasswordUtil.hashPassword(new AuthPassword(password, authDao.getUserAuthPassword(email).getSalt())));
    }

    /**
     * Gets the user id from the jwtholder that needs to be reauthenticated.
     * 
     * @return {@link User} from the token.
     * @throws Exception If the user for that id does not exist
     */
    public User getUserToAuthenticate() throws Exception {
        return userClient.getUserById(jwtHolder.getRequiredUserId());
    }
}
