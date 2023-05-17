/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.app.auth.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcs.annotations.interfaces.Client;
import com.marcs.app.auth.client.domain.AuthToken;
import com.marcs.app.auth.client.domain.request.AuthenticationRequest;
import com.marcs.app.auth.service.AuthenticationService;

/**
 * Client method for authentication of a user.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Client
public class AuthenticationClient {

    @Autowired
    private AuthenticationService service;

    /**
     * Verifies user credentials passed as a JWTRequest
     *
     * @param email    Entered email at login.
     * @param password Password entered at login.
     */
    public AuthToken authenticate(String email, String password) {
        return service.authenticate(new AuthenticationRequest(email, password));
    }
}
