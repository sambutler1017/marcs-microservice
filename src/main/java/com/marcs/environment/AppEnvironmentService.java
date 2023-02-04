/**
 * Copyright of Marcs App. All rights reserved.
 */
package com.marcs.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.marcs.common.enums.Environment;

/**
 * Used to set and get the property files and active profile for the application
 * based on the current environment the application is running in.
 *
 * @author Sam Butler
 * @since July 22, 2021
 */
@Service
public class AppEnvironmentService {
    private static final String ACTIVE_PROFILE = "APP_ENVIRONMENT";

    @Value("${security.signing-key}")
    private String JWT_SIGNING_KEY;

    @Value("${security.sendgrid-key}")
    private String SENDGRID_API_KEY;

    /**
     * This method gets the current environment
     *
     * @return string of the environment currently running
     */
    public Environment getEnvironment() {
        String env = System.getenv(ACTIVE_PROFILE);
        return env != null ? Environment.valueOf(env) : Environment.LOCAL;
    }

    /**
     * Gets the signing key for jwt tokens
     * 
     * @return String of the signing key to use.
     */
    public String getSigningKey() {
        return JWT_SIGNING_KEY;
    }

    /**
     * Gets the signing key property for sending emails
     * 
     * @return {@link String} of the key
     */
    public String getSendGridSigningKey() {
        return SENDGRID_API_KEY;
    }
}
