package com.marcs.app.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.marcs.app.auth.client.domain.AuthToken;
import com.marcs.app.auth.client.domain.request.AuthenticationRequest;
import com.marcs.app.auth.dao.AuthenticationDao;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.exceptions.type.InvalidCredentialsException;
import com.marcs.exceptions.type.UserNotFoundException;
import com.marcs.jwt.utility.JwtHolder;
import com.marcs.jwt.utility.JwtTokenUtil;
import com.marcs.test.factory.annotations.MarcsServiceTest;

/**
 * Test class for the Authenticate Service.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@MarcsServiceTest
public class AuthenticationServiceTest {

    private static final String HASHED_PASS = "$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd.";

    @Mock
    private AuthenticationDao dao;

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserProfileClient userProfileClient;

    @InjectMocks
    private AuthenticationService service;

    private AuthenticationRequest authRequest;

    private User userLoggingIn;

    @BeforeEach
    public void setup() {
        userLoggingIn = new User();
        userLoggingIn.setId(1);

        authRequest = new AuthenticationRequest();
        authRequest.setEmail("fake@mail.com");
        authRequest.setPassword("testPassword");
    }

    @Test
    public void testAuthenticateUser() {
        when(dao.getUserAuthPassword(anyString())).thenReturn(Optional.of(HASHED_PASS));
        when(userProfileClient.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(userLoggingIn));
        when(userProfileClient.updateUserLastLoginToNow(anyInt())).thenReturn(userLoggingIn);

        AuthToken authToken = service.authenticate(authRequest);

        verify(dao).getUserAuthPassword(anyString());
        verify(userProfileClient).getUsers(any(UserGetRequest.class));
        verify(userProfileClient).updateUserLastLoginToNow(1);
        verify(jwtTokenUtil).generateToken(userLoggingIn);
        assertNotNull(authToken, "Auth Token is valid");
    }

    @Test
    public void testAuthenticateUserInvalidPassword() {
        authRequest.setPassword("WrongPassword!");
        when(dao.getUserAuthPassword(anyString())).thenReturn(Optional.of(HASHED_PASS));

        InvalidCredentialsException ex = assertThrows(InvalidCredentialsException.class,
                                                      () -> service.authenticate(authRequest));

        verify(dao).getUserAuthPassword(anyString());
        verify(userProfileClient, never()).getUsers(any(UserGetRequest.class));
        verify(userProfileClient, never()).updateUserLastLoginToNow(anyInt());
        verify(jwtTokenUtil, never()).generateToken(any());
        assertEquals("Invalid Credentials for user email: 'fake@mail.com'", ex.getMessage(), "Exception");
    }

    @Test
    public void testAuthenticateUserNotFound() {
        when(dao.getUserAuthPassword(anyString())).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> service.authenticate(authRequest));

        verify(dao).getUserAuthPassword(anyString());
        verify(userProfileClient, never()).getUsers(any(UserGetRequest.class));
        verify(userProfileClient, never()).updateUserLastLoginToNow(anyInt());
        verify(jwtTokenUtil, never()).generateToken(any());
        assertEquals("User not found or does not have access for email: 'fake@mail.com'", ex.getMessage(), "Exception");
    }

    @Test
    public void testReauthenticateUser() {
        when(jwtHolder.getUserId()).thenReturn(1);
        when(userProfileClient.getUserById(anyInt())).thenReturn(userLoggingIn);

        AuthToken authToken = service.reauthenticate();

        verify(userProfileClient).getUserById(1);
        verify(userProfileClient).updateUserLastLoginToNow(1);
        verify(jwtTokenUtil).generateToken(userLoggingIn);
        assertNotNull(authToken, "Auth Token is valid");
    }
}
