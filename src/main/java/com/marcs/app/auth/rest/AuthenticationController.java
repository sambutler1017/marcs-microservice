package com.marcs.app.auth.rest;

import static org.springframework.http.MediaType.*;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marcs.annotations.interfaces.RestApiController;
import com.marcs.app.auth.client.domain.AuthToken;
import com.marcs.app.auth.client.domain.request.AuthenticationRequest;
import com.marcs.app.auth.service.AuthenticationService;

/**
 * Generates a JWT after being passed a request
 *
 * @author Sam Butler
 * @since August 1, 2021
 */
@RequestMapping("/api")
@RestApiController
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    /**
     * Generates a JWT token from a request
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     * @throws Exception - if authentication request does not match a user.
     */
    @PostMapping(path = "/authenticate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthToken> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        return ResponseEntity.ok(service.authenticate(authenticationRequest));
    }

    /**
     * Reauthenticates a user and generates a new token.
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     * @throws Exception If user does not exist.
     */
    @PostMapping(path = "/reauthenticate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthToken> reauthenticateUser() throws Exception {
        return ResponseEntity.ok(service.reauthenticate());
    }

    /**
     * Reauthenticates a user and generates a new token.
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     * @throws Exception If user does not exist.
     */
    @GetMapping(path = "/test/time", produces = APPLICATION_JSON_VALUE)
    public LocalDateTime tempMethod() throws Exception {
        return LocalDateTime.now();
    }

}
