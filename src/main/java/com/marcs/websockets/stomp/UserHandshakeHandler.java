package com.marcs.websockets.stomp;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import com.marcs.websockets.client.domain.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * Custom handshake handler for assigning a unique id to a new connection.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(UserHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String randomId = UUID.randomUUID().toString();
        LOGGER.info("User connected to Websocket with ID '{}'", randomId);
        return new UserPrincipal(randomId);
    }

}
