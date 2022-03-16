package com.marcs.jwt.config;

import static com.marcs.jwt.config.JwtGlobals.VOID_ENDPOINTS;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.marcs.common.enums.Environment;
import com.marcs.common.exceptions.BaseException;
import com.marcs.jwt.utility.JwtTokenUtil;
import com.marcs.service.activeProfile.ActiveProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JWT token validator class
 *
 * @author Sam butler
 * @since Dec 5, 2020
 */
@Component
public class JwtTokenValidator {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ActiveProfile activeProfile;

    /**
     * Checks to see if the token on the request is valid. If it is not valid then
     * it wil throw an exception, otherwise it wil continue. It will confirm that
     * the token is in the right environment, check that it has the correct fields,
     * that it is not expired, and the token signature is valid.
     *
     * @param request - The request that is being made to the endpint
     * @throws IOException If the jwt token is not valid.
     */
    public void validate(HttpServletRequest request) throws IOException {
        if (isVoidEndpoint(request.getRequestURI(), request.getMethod())) {
            return;
        }

        final String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer: ")) {
            String jwtToken = tokenHeader.substring(7).trim();

            isCorrectEnvironment(jwtToken);
            hasCorrectFields(jwtToken);
            isReauthenticating(request.getRequestURI(), jwtToken);

        } else {
            doesTokenExist(tokenHeader);
        }
    }

    /**
     * Checks to see if the endpoint that was called is a void endpoint or not.
     *
     * @param URI  - URI of the endpoint that was called
     * @param type - What type of request was made (GET, POST, etc.)
     * @return boolean based on if it is a void endpoint or not
     */
    public boolean isVoidEndpoint(String URI, String type) {
        for (Map.Entry<String, List<String>> entry : VOID_ENDPOINTS.entrySet()) {
            if (entry.getKey().equals(URI)) {
                for (String endpointType : entry.getValue()) {
                    if (endpointType.equals("ANY")) {
                        return true;
                    }
                    if (endpointType.equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This will check if the endpoint that was called was the /reauthenticate. If
     * it was then we don't care if the token is expired and it will return.
     * Otherwise check if the token is expired.
     *
     * @param endpoint The endpoint that was called.
     * @param token    The token to confirm if it is expired if need be.
     * @throws BaseException Throws exception if the token is expired.
     */
    private void isReauthenticating(String endpoint, String token) throws BaseException {
        if (!endpoint.equals("/reauthenticate")) {
            isTokenExpired(token);
        }
    }

    /**
     * Checks to see if the token that the request pulled is expired. If it is then
     * it will throw an exception.
     *
     * @param token The token to confirm if it is expired or not.
     * @throws BaseException Throws exception if the token is expired.
     */
    private void isTokenExpired(String token) throws BaseException {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new BaseException("JWT Token is Expired. Please re-authenticate.");
        }
    }

    /**
     * Checks to see if a token exists or if the token does not contain the bearer
     * keyword.
     *
     * @param tokenHeader Header to of the token.
     * @throws BaseException Throws exception based on status of token.
     */
    private void doesTokenExist(String tokenHeader) throws BaseException {
        if (tokenHeader != null) {
            throw new BaseException("JWT Token does not begin with 'Bearer:'");
        } else {
            throw new BaseException("Missing JWT Token.");
        }
    }

    /**
     * Checks to see if it has the required fields on the token.
     *
     * @param token - Token to confirm field claims on
     * @throws IOException Throws exception if it can't read the fields or if it is
     *                     an invalid token.
     */
    private void hasCorrectFields(String token) throws IOException {
        try {
            jwtTokenUtil.getIdFromToken(token);
            jwtTokenUtil.getExpirationDateFromToken(token);
            jwtTokenUtil.getAllClaimsFromToken(token);
        } catch (Exception e) {
            throw new BaseException("Could not process JWT token. Invalid signature!");
        }
    }

    /**
     * Validates that the environemnt is correct.
     *
     * @param token to be parsed
     * @throws BaseException
     */
    private void isCorrectEnvironment(String token) throws BaseException {
        Environment environment = Environment.valueOf(jwtTokenUtil.getAllClaimsFromToken(token).get("env").toString());

        if (!activeProfile.getEnvironment().equals(environment)) {
            throw new BaseException("JWT token doesn't match accessing environment!");
        }
    }
}
