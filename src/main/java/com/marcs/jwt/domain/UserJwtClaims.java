/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.jwt.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Global jwt claim fields.
 * 
 * @author Sam Butler
 * @since August 22,2022
 */
@Schema(description = "User JWT Claim variables.")
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
    public static final String STORE_ID = "storeId";

    // All Claims
    public static final String JWT_TYPE = "jwtType";
}
