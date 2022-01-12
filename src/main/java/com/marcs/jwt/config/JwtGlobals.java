package com.marcs.jwt.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT global variables class
 * 
 * @author Sam butler
 * @since Dec 5, 2020
 */
public class JwtGlobals {

    // Void JWT Endpoints
    public static final Map<String, List<String>> VOID_ENDPOINTS = new HashMap<String, List<String>>() {
        private static final long serialVersionUID = 1L;
        {
            // Authentication Endpoint
            put("/authenticate", Arrays.asList("ANY"));

            // Forgot Password Endpoint in Mail App
            put("/api/mail-app/email/forgot-password", Arrays.asList("POST"));

            // Forgot Password Endpoint in User App
            put("/api/user-app/user-profile/forgot-password", Arrays.asList("POST"));

            // Create User Endpoint in User App
            put("/api/user-app/user-profile", Arrays.asList("POST"));

            // Get Stores Endpoint in Store App
            put("/api/store-app/stores", Arrays.asList("GET"));

            // Get Email Exist status Endpoint in User App
            put("/api/user-app/user-profile/check-email", Arrays.asList("GET"));
        }
    };

}
