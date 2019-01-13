package com.cairone.example.webapp.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cairone.example.webapp.oauth2.CustomOAuth2UserService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
	        .disable()
	    .formLogin()
	        .disable()
	    .httpBasic()
	        .disable()
		.antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/login/**", "/login-error/**", "/webjars/**", "/error/**")
            .permitAll()
        .anyRequest()
        	.authenticated()
        .and()
        .oauth2Login()
		        .authorizationEndpoint()
		        .baseUri("/oauth2/authorize")
		        .and()
		    .redirectionEndpoint()
		        .baseUri("/oauth2/callback/*")
		        .and()
		    .userInfoEndpoint()
		        .userService(customOAuth2UserService);	
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
