/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.jwt.domain;

import com.marcs.environment.AppEnvironmentService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Jwt Pair class for storing the token and claims.
 * 
 * @author Sam Butler
 * @since August 22, 2022
 */
@Schema(description = "Jwt Pair data object.")
public final class JwtPair {

    @Schema(description = "Authentication token for the user.")
    private final String token;

    @Schema(description = "The claim set of the token.")
    private final Claims claimSet;

    public JwtPair(String token, AppEnvironmentService activeProfile) {
        this.token = token;
        this.claimSet = (Claims) Jwts.parser().setSigningKey(activeProfile.getSigningKey()).parse(token).getBody();
    }

    public String getToken() {
        return token;
    }

    public Claims getClaimSet() {
        return claimSet;
    }
}
