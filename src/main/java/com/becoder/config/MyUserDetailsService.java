package com.becoder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.model.Userdtls;
import com.becoder.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Userdtls user=userRepo.findByUsername(username);
		if(ObjectUtils.isEmpty(user))
		{
			throw new UsernameNotFoundException("User not found: " + username);
		}
		return new MyUserDetails(user);
	}

}
