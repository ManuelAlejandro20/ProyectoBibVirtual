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

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	@Autowired
	private UserService userServicio;
	
    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl = new AuthenticationSuccessHandlerImpl();
   
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
    
    @Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
    	auth.authenticationProvider(authenticationProvider());
    }  
		


}
