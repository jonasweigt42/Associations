package com.think.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().requestMatchers();
	}

	@Override
	public void configure(WebSecurity web)
	{
		web.ignoring().antMatchers(

				// Vaadin Flow static resources
				"/VAADIN/**",

				// the standard favicon URI
				"/favicon.ico",

				// the robots exclusion standard
				"/robots.txt",

				// web application manifest
				"/manifest.webmanifest", "/sw.js", "/offline-page.html",

				// icons and images
				"/icons/**", "/images/**",

				// development mode static resources
				"/frontend/**",

				// development mode webjars
				"/webjars/**",

				// development mode H2 console
				"/h2-console/**",

				// production mode static resources
				"/frontend-es5/**", "/frontend-es6/**"

		);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{

	}
}
