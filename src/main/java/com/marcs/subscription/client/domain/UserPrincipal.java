/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.subscription.client.domain;

import java.security.Principal;

import com.marcs.app.user.client.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User principal for identifying users on a websocket.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
@Schema(description = "User Principal for subscription identification.")
public class UserPrincipal implements Principal {

    @Schema(description = "The unique identifer of the use session")
    private String name;

    @Schema(description = "The user attached to the session.")
    private User user;

    public UserPrincipal(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
