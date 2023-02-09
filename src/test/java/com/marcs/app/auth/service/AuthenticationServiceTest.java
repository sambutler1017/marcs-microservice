package com.marcs.app.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.marcs.app.auth.client.domain.AuthToken;
import com.marcs.app.auth.client.domain.request.AuthenticationRequest;
import com.marcs.app.auth.dao.AuthenticationDao;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.User;
import com.marcs.app.user.client.domain.request.UserGetRequest;
import com.marcs.jwt.utility.JwtHolder;
import com.marcs.jwt.utility.JwtTokenUtil;

/**
 * Test class for the Authenticate Service.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

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

    @Test
    public void testAuthenticateUser() {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("fake@mail.com");
        authRequest.setPassword("testPassword");

        when(dao.getUserAuthPassword(anyString()))
                .thenReturn(Optional.of("$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd."));
        when(userProfileClient.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(userLoggingIn));
        when(userProfileClient.updateUserLastLoginToNow(anyInt())).thenReturn(userLoggingIn);

        AuthToken authToken = service.authenticate(authRequest);

        verify(dao).getUserAuthPassword(anyString());
        verify(userProfileClient).getUsers(any(UserGetRequest.class));
        verify(userProfileClient).updateUserLastLoginToNow(1);
        verify(jwtTokenUtil).generateToken(userLoggingIn);
        assertNotNull(authToken, "Auth Token is valid");
    }
}
