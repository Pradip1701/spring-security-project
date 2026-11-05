package com.becoder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	
	//BCryptPasswordEncoder is used in Spring Security to securely hash user passwords before storing them in 
	//the database and verify passwords during login.
	
	//जेव्हा आपण password ला hash करतो, तेव्हा मूळ password एका वेगळ्या स्वरूपात convert होतो.
	//actully if db store password like this $2a$10$EC8vw2Ynkbdz8n/WglrZ1ezMSD1lAMdrDLtCtpeAJK.kBsUhMabWW
	//then this class convert like 1234
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.csrf(customizer->customizer.disable());
		
		//CSRF protection makes Spring require a hidden token on every POST, PUT and DELETE. Disabling it turns 
		//that check off.

       // Example: with CSRF on, a POST /notes from Postman fails with 403 because there is no token. 
		//now we disable it so every request not needs it, so the request goes through.
		
       //It is fine while learning, and it is usually disabled for stateless JWT APIs. For a browser app that
		//uses sessions, you would normally keep it on.
		
		
		http.authorizeHttpRequests(request->
		request.requestMatchers("/login","/register").permitAll().anyRequest().authenticated());//This is the authorization rule: every 
		                                                                        //request, to any URL, needs a logged-in user.
		
		//Example: calling GET /notes without logging in is blocked. After login it works.
		
		
		//http.formLogin(Customizer.withDefaults());
		
		//This turns on form-based login with Spring’s default settings. It adds the 
		//This enables form-based login. provides ready-made login page at /login and POST /login handling.

        //Customizer.withDefaults() means “no custom settings, use the defaults”.
		
		http.httpBasic(Customizer.withDefaults());
		
		//This enables HTTP Basic Authentication.
		//Here, you don't need a separate login page.

        //Instead, the client sends the username and password directly with each HTTP request using an HTTP header.
		//Ex: in postman in Authorization select basic auth and enter username and password there
		
		//If credentials are correct → Request is allowed.
		//If credentials are incorrect → Request is rejected with 401 Unauthorized.
		
		http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		//SessionCreationPolicy   (enum: ALWAYS, IF_REQUIRED, NEVER, STATELESS)
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider daoProvider=new DaoAuthenticationProvider();
		daoProvider.setPasswordEncoder(passwordEncoder());
		daoProvider.setUserDetailsService(userDetailsService);   //It loads the user from the database using
		                                                        //UserDetailsService and verifies the password using 
		                                                   //PasswordEncoder. If both pass, the user is authenticated.”
		return daoProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	
	
	
	
}

