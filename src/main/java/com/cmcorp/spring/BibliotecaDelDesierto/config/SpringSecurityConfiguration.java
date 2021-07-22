/*
 * Copyright (c) 2021 CMCORP
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * An intermediate form of license used by the X Consortium for X11 used the following wording:[16]
 *
 */
package com.cmcorp.spring.BibliotecaDelDesierto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cmcorp.spring.BibliotecaDelDesierto.service.UserService;

/**
 * Security configuration
 * 
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	/*
	 * Method that returns a password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	@Autowired
	private UserService userServicio;
	
    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl = new AuthenticationSuccessHandlerImpl();
   
    /**
     * method that ensures that the user service uses the password encoder
     * @return authentication
     */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userServicio);
		auth.setPasswordEncoder(passwordEncoder());;
		return auth;
	}
	
	/**
	 * Configure the roles of the views and the session of the user after authenticated
	 */
    @Override  
    protected void configure(HttpSecurity http) throws Exception {  
        http  
        .authorizeRequests()          	
            .antMatchers("/myaccount").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
            .antMatchers("/newbook").hasAuthority("ROLE_ADMIN")
            .antMatchers("/checkout").hasAuthority("ROLE_USER")
            .antMatchers("/book/{id}/summary").hasAuthority("ROLE_USER")
            .antMatchers("/book/{id}/edit").hasAuthority("ROLE_ADMIN")
            .antMatchers("/cart").hasAuthority("ROLE_USER")
            .antMatchers("/js/**", "/css/**","/resources/**").permitAll().anyRequest().permitAll()
        .and()  
        .formLogin()
        	.loginPage("/login")
        	.usernameParameter("username")
        	//.defaultSuccessUrl("/myaccount")
        	.permitAll().successHandler(authenticationSuccessHandlerImpl)
        	.failureForwardUrl("/login_error")
	      	.permitAll()
        .and()
        .exceptionHandling()
        	.accessDeniedPage("/access_denied")
        .and()
        .logout()       
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")       
        	.permitAll()
        .and()
				.headers().frameOptions().sameOrigin();
		http.csrf().disable();  //Habilitar para el login desde app
    }  
    
    /**
     * Set the authentication provider
     */
    @Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
    	auth.authenticationProvider(authenticationProvider());
    }  
		


}
