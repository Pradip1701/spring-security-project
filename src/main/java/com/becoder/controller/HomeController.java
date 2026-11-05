package com.becoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.UserRequest;
import com.becoder.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/")
	public ResponseEntity<?> login(HttpServletRequest http)
	{
		//String id=http.getSession().getId();
		return new ResponseEntity<>("Hello becoder your dashboard",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRequest req)
	{
		String token=userService.login(req);
		if(token==null)
		{
			return new ResponseEntity<>("invalid credentials",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	
}
