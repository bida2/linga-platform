package com.foreignwords.foreignwords.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.foreignwords.foreignwords.services.EmailService;



@Configuration
@EnableWebSecurity
public class HTTPAccessConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	 	@Autowired
	    private UserDetailsService userDetailsService;

	    @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public EmailService emailService() {
	    	return new EmailService();
	    }
	    
	    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	    }
	  
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	// In the future, /addStock will require login. For testing purposes now it is excluded from the list of pages 
	    	// that require login
	    	// A logged in user cannot access the /signup URL -> he must log out and then try to signup
	    	http.authorizeRequests().antMatchers("/","/login","/css/**","/js/**","/img/**", "/h2-console/**", "/forgot", "/changePassword", "/logout").permitAll()
	    	.and()
	    	.authorizeRequests().antMatchers("/wordUser").hasRole("USER")
	    	.and()
	    	.authorizeRequests().antMatchers("/word","/approve").hasRole("ADMIN")
	    	.and()
	    	.formLogin()
            .loginPage("/login")
            .failureUrl("/loginFailure")
            .permitAll()
            .and()
            .rememberMe()
            .alwaysRemember(true)
            .and()
        .logout()
        .logoutSuccessUrl("/")
            .permitAll();
	    	/* H2 Console only settings - disable otherwise */
	    	//http.csrf().disable();
	    	// http.headers().frameOptions().disable();
	    }

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	    }
	    
}