package com.marcs.app.auth.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.marcs.app.auth.client.domain.AuthToken;
import com.marcs.app.auth.client.domain.request.AuthenticationRequest;
import com.marcs.app.auth.dao.AuthenticationDao;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.common.exceptions.BaseException;
import com.marcs.common.exceptions.InvalidCredentialsException;
import com.marcs.common.util.TimeZoneUtil;
import com.marcs.jwt.utility.JwtHolder;
import com.marcs.jwt.utility.JwtTokenUtil;

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
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserProfileClient userProfileClient;

    /**
     * Generates a JWT token from a request
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     * @throws Exception - if authentication request does not match a user.
     */
    public AuthToken authenticate(AuthenticationRequest request) throws Exception {
        User user = verifyUser(request.getEmail(), request.getPassword());

        String token = jwtTokenUtil.generateToken(user);
        return new AuthToken(token, LocalDateTime.now(TimeZoneUtil.defaultZone()),
                             jwtTokenUtil.getExpirationDateFromToken(token), user);
    }

    /**
     * Will re-authenticate the logged in user and give a new token. If the user id
     * can not be returned from the current token, it will error and return null.
     * 
     * @return {@link AuthToken} from the token.
     * @throws Exception If the user for that id does not exist.
     */
    public AuthToken reauthenticate() throws Exception {
        User u = userProfileClient.getUserById(jwtHolder.getUserId());
        userProfileClient.updateUserLastLoginToNow(u.getId());

        String token = jwtTokenUtil.generateToken(u);
        return new AuthToken(token, LocalDateTime.now(TimeZoneUtil.defaultZone()),
                             jwtTokenUtil.getExpirationDateFromToken(token), u);
    }

    /**
     * Verifies user credentials.
     *
     * @param email    Entered email at login.
     * @param password Password entered at login.
     * @throws Exception Throw an exception if the credentials do not match.
     */
    public User verifyUser(String email, String password) throws Exception {
        if(BCrypt.checkpw(password, authDao.getUserAuthPassword(email))) {
            User authUser = getAuthenticatedUser(email);
            return userProfileClient.updateUserLastLoginToNow(authUser.getId());
        }
        else {
            throw new InvalidCredentialsException(email);
        }
    }

    /**
     * Gets the user id from the jwtholder that needs to be reauthenticated.
     * 
     * @return {@link User} from the token.
     * @throws Exception If the user for that id does not exist
     */
    public User getUserToAuthenticate() throws Exception {
        return validateUserAccess(userProfileClient.getUserById(jwtHolder.getUserId()));
    }

    /**
     * Get a user based on their email address. Used when a user has sucessfully
     * authenticated.
     * 
     * @param email The email to search for.
     * @return {@link User} object of the authenticated user.
     * @throws Exception
     */
    private User getAuthenticatedUser(String email) throws Exception {
        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet(email));
        return userProfileClient.getUsers(request).get(0);
    }

    /**
     * Checks to see if the user has app access before authenticating them.
     * 
     * @param u The user to validate.
     * @return {@link User} object that they were authenticated.
     */
    private User validateUserAccess(User u) {
        if(!u.isAppAccess()) {
            throw new BaseException("User does not have app access!");
        }
        return u;
    }
}
