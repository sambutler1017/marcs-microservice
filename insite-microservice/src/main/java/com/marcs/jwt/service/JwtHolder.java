package com.marcs.jwt.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Component("JwtHolder")
public class JwtHolder {

	private String token;

	private JwtParser jwtParser = Jwts.parser().setSigningKey("marcsmicroservice");

	public String getToken() {
		if (isTokenNull())
			setRequest();
		return token;
	}

	public int getRequiredUserId() {
		if (isTokenNull())
			setRequest();
		return Integer.parseInt(jwtParser.parseClaimsJws(this.token).getBody().get("userId").toString());
	}

	public void setRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		this.token = request.getHeader("Authorization").split(" ")[1];
	}

	public boolean isTokenNull() {
		return this.token == null;
	}

}
