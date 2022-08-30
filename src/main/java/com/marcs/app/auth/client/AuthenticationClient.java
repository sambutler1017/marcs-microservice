package com.marcs.app.auth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.auth.client.domain.AuthToken;
import com.marcs.app.auth.client.domain.request.AuthenticationRequest;
import com.marcs.app.auth.rest.AuthenticationController;

/**
 * Client method for authentication of a user.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Client
public class AuthenticationClient {

    @Autowired
    private AuthenticationController controller;

    /**
     * Verifies user credentials passed as a JWTRequest
     *
     * @param email    - Entered email at login.
     * @param password - Password entered at login.
     * @throws Exception
     */
    public ResponseEntity<AuthToken> authenticateUser(String email, String password) throws Exception {
        return controller.authenticateUser(new AuthenticationRequest(email, password));
    }

    /**
     * Reauthenticates a user and generates a new token.
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     * @throws Exception
     */
    public ResponseEntity<AuthToken> reauthenticateUser() throws Exception {
        return controller.reauthenticateUser();
    }
}
