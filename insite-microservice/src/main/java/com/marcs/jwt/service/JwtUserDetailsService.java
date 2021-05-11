package com.marcs.jwt.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marcs.app.user.client.UserClient;
import com.marcs.app.user.client.domain.UserCredentials;
import com.marcs.app.user.client.domain.request.UserCredentialsGetRequest;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserClient userClient;

	@Override
	public UserDetails loadUserByUsername(String userName) {
		UserCredentialsGetRequest request = new UserCredentialsGetRequest();
		request.setUsername(userName);
		UserCredentials userInfo = null;
		try {
			userInfo = userClient.getUserCredentials(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!userInfo.getUsername().equals(userName)) {
			throw new UsernameNotFoundException(userName);
		}

		return new User(userInfo.getUsername(), userInfo.getPassoword(), new ArrayList<>());
	}
}
