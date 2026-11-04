package com.becoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.becoder.dto.UserRequest;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public String login(UserRequest req) {
		
		Authentication authentication=authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));

		if(authentication.isAuthenticated())
		{
			//return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";
		    return jwtService.generateToken(req.getUsername());
		}
		return null;
	}

}
