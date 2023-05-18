package com.marcs.test.factory.abstracts;

import java.lang.reflect.AnnotatedElement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.marcs.annotations.interfaces.ControllerJwt;
import com.marcs.app.user.client.domain.User;
import com.marcs.jwt.utility.JwtTokenUtil;

/**
 * Base Test class for controllers performing rest endpoint calls.
 * 
 * @author Sam Butler
 * @since July 27, 2021
 */
public abstract class BaseControllerTest extends RequestTestUtil {

    @LocalServerPort
    protected int randomServerPort;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private HttpHeaders headers;

    @BeforeEach
    public void setup(TestInfo info) {
        headers = new HttpHeaders();
        checkControllerJwtAnnotation(info);
    }

    @AfterEach
    public void clearHeaders() {
        headers = new HttpHeaders();
    }

    /**
     * Checks to see if the currently running test has a {@link ControllerJwt}
     * annotation present either on the method or the class itself. If it does then
     * it will set the authorization headers based on the set values. If no
     * {@link ControllerJwt} is present then it will NOT set the authorization
     * headers or or content-type.
     * 
     * @param info The test information for the currently running test.
     */
    public void checkControllerJwtAnnotation(TestInfo info) {
        ControllerJwt annClass = getJwtControllerAnnotation(info.getTestClass().get());
        ControllerJwt annMethod = getJwtControllerAnnotation(info.getTestMethod().get());

        if(annMethod != null) {
            setHeaders(annMethod);
        }
        else if(annClass != null) {
            setHeaders(annClass);
        }
    }

    /**
     * Perform a GET call on the given api and expect an error.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected ResponseEntity<Object> get(String api) {
        return get(api, Object.class);
    }

    /**
     * Perform a GET call on the given api.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> get(String api, Class<T> responseType) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return exchange(api, HttpMethod.GET, requestEntity, responseType);
    }

    /**
     * Perform a POST call on the given api.
     * 
     * @param api The endpoint to consume.
     * @return Response entity of the returned data.
     */
    protected ResponseEntity<Object> post(String api) {
        return post(api, Object.class);
    }

    /**
     * Perform a POST call on the given api.
     * 
     * @param api     The endpoint to consume.
     * @param request The request to send with the post.
     * @return Response entity of the returned data.
     */
    protected ResponseEntity<Object> post(String api, Object request) {
        return post(api, request, Object.class);
    }

    /**
     * Perform a POST call on the given api.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> post(String api, Class<T> responseType) {
        return post(api, null, responseType);
    }

    /**
     * Perform a POST call on the given api.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param request      The request to send with the post.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> post(String api, Object request, Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(request, headers);
        return exchange(api, HttpMethod.POST, requestEntity, responseType);
    }

    /**
     * Perform a PUT call on the given api.
     * 
     * @param api The endpoint to consume.
     * @return Response entity of the returned data.
     */
    protected ResponseEntity<Object> put(String api) {
        return put(api, Object.class);
    }

    /**
     * Perform a PUT call on the given api.
     * 
     * @param api     The endpoint to consume.
     * @param request The request to send with the post.
     * @return Response entity of the returned data.
     */
    protected ResponseEntity<Object> put(String api, Object request) {
        return put(api, request, Object.class);
    }

    /**
     * Perform a PUT call on the given api.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> put(String api, Class<T> responseType) {
        return put(api, null, responseType);
    }

    /**
     * Perform a PUT call on the given api.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param request      The request to send with the post.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> put(String api, Object request, Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(request, headers);
        return exchange(api, HttpMethod.PUT, requestEntity, responseType);
    }

    /**
     * Perform a DELETE call on the given api.
     * 
     * @param api The endpoint to consume.
     * @return Response entity of the returned data.
     */
    protected ResponseEntity<Object> delete(String api) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        return exchange(api, HttpMethod.DELETE, requestEntity, Object.class);
    }

    /**
     * Make an exchange call through the rest template.
     * 
     * @param <T>    Typed parameter of the response type.
     * @param api    The api to hit.
     * @param method The method to perform on the endpoint.
     * @param entity The entity instance to pass.
     * @param clazz  The class to return the response as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> exchange(String api, HttpMethod method, HttpEntity<?> entity, Class<T> clazz) {
        return testRestTemplate.exchange(buildUrl(api), method, entity, clazz);
    }

    /**
     * Build out the absolute path for the api. Rest endpoint test creates own local
     * web server to run. Therefore the default host name is localhost and a random
     * port number.
     * 
     * @param api The api to build.
     * @return Completed url with the attached api.
     */
    private String buildUrl(String api) {
        return String.format("http://localhost:%d%s", randomServerPort, api);
    }

    /**
     * Set headers on instance class. Used for authenticated endpoint calls.
     * 
     * @param jwtController The annotation containing the user information.
     */
    private void setHeaders(ControllerJwt jwtController) {
        User u = new User();
        u.setId(jwtController.userId());
        u.setFirstName(jwtController.firstName());
        u.setLastName(jwtController.lastName());
        u.setEmail(jwtController.email());
        u.setWebRole(jwtController.webRole());

        String token = jwtTokenUtil.generateToken(u);
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
    }

    /**
     * Gets the annotation instance of the element type. If none is found, it will
     * return null.
     * 
     * @param elementType     The element type (Class, method, etc.)
     * @param annotationClass What the annotation class is.
     * @return The {@link ControllerJwt} instance.
     */
    private ControllerJwt getJwtControllerAnnotation(AnnotatedElement elementType) {
        return AnnotatedElementUtils.findMergedAnnotation(elementType, ControllerJwt.class);
    }
}
