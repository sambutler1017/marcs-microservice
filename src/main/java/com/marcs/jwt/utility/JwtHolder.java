package com.marcs.jwt.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcs.app.user.client.domain.User;
import com.marcs.common.enums.Environment;
import com.marcs.common.enums.WebRole;
import com.marcs.environment.AppEnvironmentService;
import com.marcs.jwt.domain.JwtPair;
import com.marcs.jwt.domain.JwtType;
import com.marcs.jwt.domain.UserJwtClaims;

import io.jsonwebtoken.Claims;

/**
 * JwtHolder class to store authentication token in a thread local instance to
 * be accessed. Although the JWT is held in a static thread local, the methods
 * are non-static so that JwtHolder can be mocked in tests.
 * 
 * @author Sam Butler
 * @since August 8, 2020
 */
@Service
public class JwtHolder {
	private static final ThreadLocal<JwtPair> TOKEN = new ThreadLocal<>();

	@Autowired
	private AppEnvironmentService appEnvironmentService;

	/**
	 * Set the token on the current thread local instance.
	 * 
	 * @param token The token to store.
	 */
	public void setToken(String token) {
		JwtPair pair = new JwtPair(token, appEnvironmentService);
		TOKEN.set(pair);
	}

	/**
	 * Clears the token from the current thread local instance.
	 */
	public void clearToken() {
		TOKEN.remove();
	}

	/**
	 * Gets the current JwtPair from the thread local instance.
	 * 
	 * @return {@link JwtPair} of the thread local.
	 */
	public JwtPair getPair() {
		return TOKEN.get();
	}

	/**
	 * Gets the claims from the token.
	 * 
	 * @return {@link Claims} object.
	 */
	public Claims getClaims() {
		return getPair().getClaimSet();
	}

	/**
	 * Parse the claims from the given token and for the given key value pair.
	 * 
	 * @param key The key to find.
	 * @return {@link Object} of the found key.
	 */
	public Object parse(String key) {
		return getClaims().get(key);
	}

	/**
	 * Checks to see if the token is stored locally on the current instance. It will
	 * check if the jwt pair is null to confirm this.
	 * 
	 * @return Boolean of the token status.
	 */
	public boolean isTokenAvaiable() {
		JwtPair pair = TOKEN.get();
		return pair != null;
	}

	/**
	 * Will get the environment from the instance token.
	 * 
	 * @return {@link Environment} object.
	 */
	public Environment getEnvironment() {
		return Environment.valueOf(parse(UserJwtClaims.ENVIRONMENT).toString());
	}

	/**
	 * Gets the jwt type of the token.
	 * 
	 * @return {@link JwtType} of the token.
	 */
	public JwtType getJwtType() {
		return JwtType.valueOf(parse(UserJwtClaims.JWT_TYPE).toString());
	}

	/**
	 * Get the current user Id.
	 * 
	 * @return int of the userId from the current token
	 */
	public int getUserId() {
		return Integer.parseInt(parse(UserJwtClaims.USER_ID).toString());
	}

	/**
	 * Get the current email.
	 * 
	 * @return String of the email from the current token
	 */
	public String getEmail() {
		return parse(UserJwtClaims.EMAIL).toString();
	}

	/**
	 * Get the current webRole.
	 * 
	 * @return String of the webRole from the current token
	 */
	public WebRole getWebRole() {
		return WebRole.valueOf(parse(UserJwtClaims.WEB_ROLE).toString());
	}

	/**
	 * Gets the reset password status.
	 * 
	 * @return int of the userId from the current token
	 */
	public boolean getResetPassword() {
		return Boolean.parseBoolean(parse(UserJwtClaims.PASSWORD_RESET).toString());
	}

	/**
	 * Will get a user object from the current user jwt.
	 * 
	 * @return {@link User} object.
	 */
	public User getUser() {
		User currentUser = new User();
		currentUser.setId(getUserId());
		currentUser.setEmail(getEmail());
		currentUser.setWebRole(getWebRole());
		currentUser.setFirstName(parse(UserJwtClaims.FIRST_NAME).toString());
		currentUser.setLastName(parse(UserJwtClaims.LAST_NAME).toString());
		return currentUser;
	}
}
