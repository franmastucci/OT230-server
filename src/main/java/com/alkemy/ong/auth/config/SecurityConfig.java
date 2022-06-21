package com.alkemy.ong.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alkemy.ong.auth.filter.JwtFilter;
import com.alkemy.ong.auth.service.impl.UserDetailsServiceImpl;


Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl myUserDetailsService;

	@Autowired
	JwtFilter jwtRequestFilter;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /*  
    Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/auth/register")
           .antMatchers("/users/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf();
        http.authorizeRequests()
           .antMatchers("/auth/register").permitAll()
           .antMatchers("/users/**").permitAll();
    }
     */
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	return super.authenticationManagerBean();
    }
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/auth/register").permitAll()
		.antMatchers(HttpMethod.POST, "/auth/login").permitAll()
		.antMatchers("/users/**").permitAll()
		.anyRequest().authenticated()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
		.authenticationEntryPoint(new Http403ForbiddenEntryPoint());;
		
	}

}
