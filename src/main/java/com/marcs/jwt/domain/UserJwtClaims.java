package com.marcs.jwt.domain;

/**
 * Global jwt claim fields.
 * 
 * @author Sam Butler
 * @since August 22,2022
 */
public abstract class UserJwtClaims {
    // User JWT Claims
    public static final String USER_ID = "userId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String WEB_ROLE = "webRole";
    public static final String ENVIRONMENT = "env";
    public static final String PASSWORD_RESET = "passwordReset";
    public static final String APPS = "apps";
    public static final String ACCESS = "access";

    // All Claims
    public static final String JWT_TYPE = "jwtType";
}
