package com.becoder.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.becoder.model.Userdtls;

public class MyUserDetails implements UserDetails{

	private Userdtls user;
	
	public MyUserDetails() {
		super();
	}
	

	public MyUserDetails(Userdtls user) {
		super();
		this.user = user;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority grant=new SimpleGrantedAuthority("USER");
		return Arrays.asList(grant);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}


	public Userdtls getUser() {
		return user;
	}


	public void setUser(Userdtls user) {
		this.user = user;
	}

	
	
}
