/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.jwt.utility;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcs.app.featureaccess.client.FeatureAccessClient;
import com.marcs.app.user.client.UserProfileClient;
import com.marcs.app.user.client.domain.Application;
import com.marcs.app.user.client.domain.User;
import com.marcs.common.date.LocalDateFormatter;
import com.marcs.environment.AppEnvironmentService;
import com.marcs.jwt.domain.JwtType;
import com.marcs.jwt.domain.UserJwtClaims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Token util to create and manage jwt tokens.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Service
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 18000000;

    @Autowired
    private AppEnvironmentService activeProfile;

    @Autowired
    private UserProfileClient userClient;

    @Autowired
    private FeatureAccessClient featureAccessClient;

    /**
     * Pulls the email (Subject Field) from the token
     * 
     * @param token The token being inspected
     * @return String of the subject field
     */
    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Pulls the expiration date from a given token
     * 
     * @param token The token being inspected
     * @return A Date object
     */
    public LocalDateTime getExpirationDateFromToken(String token) {
        return LocalDateFormatter.convertDateToLocalDateTime(getClaimFromToken(token, Claims::getExpiration));
    }

    /**
     * Get Specfic claims from a token based on the passed in resolver
     * 
     * @param <T>            Object type
     * @param token          Token to be inspected
     * @param claimsResolver Claims resolver
     * @return The generic type passed in of the claims
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Pulls all the claims off of a given token
     * 
     * @param token The token to inspect and pull the claims from
     * @return Claims object is returned
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(activeProfile.getSigningKey()).parseClaimsJws(token).getBody();
    }

    /**
     * generate token for reset password as false by default.
     * 
     * @param user User info to be added to the token
     * @return String of the new JWT token
     */
    public String generateToken(User user) {
        return generateToken(user, false);
    }

    /**
     * Sets up the fields to be added to the token. Also takes in a reset param that
     * will say if the token is for a reset password.
     * 
     * @param user  User info to be added to the token
     * @param reset If this is a reset password token.
     * @return String of the new JWT token
     * @throws Exception
     */
    public String generateToken(User user, boolean reset) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(UserJwtClaims.USER_ID, user.getId());
        claims.put(UserJwtClaims.FIRST_NAME, user.getFirstName());
        claims.put(UserJwtClaims.LAST_NAME, user.getLastName());
        claims.put(UserJwtClaims.EMAIL, user.getEmail());
        claims.put(UserJwtClaims.WEB_ROLE, user.getWebRole());
        claims.put(UserJwtClaims.ENVIRONMENT, activeProfile.getEnvironment());
        claims.put(UserJwtClaims.JWT_TYPE, JwtType.WEB);
        claims.put(UserJwtClaims.APPS, userClient.getUserAppsById(user.getId()).stream()
                .filter(v -> (v.isAccess() && v.isEnabled())).map(Application::getName).collect(Collectors.toList()));
        claims.put(UserJwtClaims.ACCESS, featureAccessClient.getFeatureAccess(user.getWebRole().getRank()));
        claims.put(UserJwtClaims.PASSWORD_RESET, reset);

        if(user.getStoreId() != null) {
            claims.put("storeId", user.getStoreId());
        }

        return doGenerateToken(claims);
    }

    /**
     * Generate a token based on the given Claims and subject
     * 
     * @param claims  The claims/fields to be added to the token
     * @param subject The main subject to be added to the field
     * @return String of the generated JWT token
     */
    private String doGenerateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, activeProfile.getSigningKey()).compact();
    }
}
