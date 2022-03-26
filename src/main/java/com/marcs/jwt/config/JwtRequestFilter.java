package com.marcs.jwt.config;

import static com.marcs.jwt.config.JwtGlobals.VOID_ENDPOINTS;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * All Request get filtered here to confirm that it has a valid jwt token before
 * accessing data. If the request should not be filtered then the
 * {@link #shouldNotFilter(HttpServletRequest)} will catch it and continue to
 * the endpoint call.
 * 
 * @author Sam butler
 * @since Aug 6, 2021
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenValidator JWTValidator;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        if (JWTValidator.isWebSocketValidation(req)) {
            if (validSocketSession(req.getQueryString())) {
                chain.doFilter(req, res);
            }
        } else {
            JWTValidator.validateRequest(req);
            chain.doFilter(req, res);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<HttpMethod> voidEndpointMethodList = VOID_ENDPOINTS.get(request.getRequestURI());
        HttpMethod requestMethodType = HttpMethod.valueOf(request.getMethod());

        return voidEndpointMethodList != null && voidEndpointMethodList.contains(requestMethodType);
    }

    /**
     * Validates the called socket session to confirm the passed in token is valid.
     * 
     * @param token The token to authenticate.
     * @return The status of the session validation.
     */
    private boolean validSocketSession(String token) {
        try {
            JWTValidator.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}