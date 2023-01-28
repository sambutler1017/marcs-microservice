/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.subscription.stomp;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.marcs.jwt.utility.JwtHolder;
import com.marcs.subscription.client.domain.UserPrincipal;

/**
 * Custom handshake handler for assigning a unique id to a new connection.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
public class SubscriptionHandshakeHandler extends DefaultHandshakeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionHandshakeHandler.class);

    private JwtHolder jwtHolder;

    public SubscriptionHandshakeHandler(JwtHolder jwtHolder) {
        this.jwtHolder = jwtHolder;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String randomId = UUID.randomUUID().toString();

        LOGGER.info("User Client connected to socket with ID '{}'", randomId);
        return new UserPrincipal(randomId, jwtHolder.getUser());
    }
}
