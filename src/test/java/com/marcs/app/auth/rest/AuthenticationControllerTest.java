package com.marcs.app.auth.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import com.marcs.InsiteMicroserviceApplication;
import com.marcs.annotations.interfaces.ControllerJwt;
import com.marcs.app.auth.client.domain.AuthToken;
import com.marcs.app.auth.client.domain.request.AuthenticationRequest;
import com.marcs.app.auth.service.AuthenticationService;
import com.marcs.app.featureaccess.client.FeatureAccessClient;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.Application;
import com.marcs.test.factory.abstracts.BaseControllerTest;
import com.marcs.test.factory.annotations.MarcsRestTest;

/**
 * Test class for the Authenticate Controller.
 * 
 * @author Sam Butler
 * @since August 23, 2022
 */
@ContextConfiguration(classes = InsiteMicroserviceApplication.class)
@MarcsRestTest
public class AuthenticationControllerTest extends BaseControllerTest {

    @MockBean
    private AuthenticationService service;

    @MockBean
    private UserProfileClient userProfileClient;

    @MockBean
    private FeatureAccessClient featureAccessClient;

    @Test
    public void testAuthenticate() throws Exception {
        when(service.authenticate(any(AuthenticationRequest.class))).thenReturn(new AuthToken());

        AuthenticationRequest request = new AuthenticationRequest("test@mail.com", "testPassword");
        check(post("/api/authenticate", request, AuthToken.class), serializedNonNull());
    }

    @Test
    @ControllerJwt
    public void testReAuthenticate() throws Exception {
        when(service.reauthenticate()).thenReturn(new AuthToken());
        when(userProfileClient.getUserApps()).thenReturn(List.of(new Application()));
        when(featureAccessClient.getFeatureAccess(anyInt())).thenReturn(new HashMap<>());
        check(post("/api/reauthenticate", AuthToken.class), serializedNonNull());
    }

    @Test
    public void testReAuthenticateNoToken() {
        check(post("/api/reauthenticate"), error(HttpStatus.UNAUTHORIZED, "Missing JWT Token."));
    }

}
