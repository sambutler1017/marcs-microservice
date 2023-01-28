/**
 * Copyright (c) 2023 Marcs App.
 * All rights reserved.
 */
package com.marcs.gateway.domain.interfaces;

import javax.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Base interface for request validators.
 * 
 * @author Sam Butler
 * @since August 24, 2022
 */
@Schema(description = "Contract for base request validator.")
public interface BaseRequestValidator {

    /**
     * Method to check to see if the request is valid.
     *
     * @param request The request that is being made to the endpint
     * @return {@link Boolean} of the validation status of the request.
     */
    public void validateRequest(HttpServletRequest req);
}
