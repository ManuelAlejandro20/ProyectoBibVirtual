package com.cmcorp.spring.BibliotecaDelDesierto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cmcorp.spring.BibliotecaDelDesierto.service.UserService;

@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	@Autowired
	private UserService userServicio;
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userServicio);
		auth.setPasswordEncoder(passwordEncoder());;
		return auth;
	}
	    
    @Override  
    protected void configure(HttpSecurity http) throws Exception {  
        http  
        .sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        .and()
        .authorizeRequests()          	
            .antMatchers("/myaccount").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
            .antMatchers("/resources/**").permitAll().anyRequest().permitAll()
        .and()  
        .formLogin()
        	.loginPage("/login")
        	.usernameParameter("username")
        	.defaultSuccessUrl("/myaccount")
        	.failureForwardUrl("/login_error")
	      	.permitAll()
        .and()
        .logout()       
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")       
        	.permitAll();        
    }  
	
    @Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
    	auth.authenticationProvider(authenticationProvider());
    }  
		


}