package com.nancy.booking.show.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Provides RBAC (Role Based Access) Support when tested with Rest API
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";

    private static final String BUYER = "BUYER";

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/findUser").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/setupShow").hasRole(ADMIN)
                .antMatchers("/getShowDetails").hasRole(ADMIN)
                .antMatchers("/findAllShows").hasRole(ADMIN)
                .antMatchers("/bookTicket").hasRole(BUYER)
                .antMatchers("/cancelTicket").hasRole(BUYER)
                .anyRequest().authenticated();
        httpSecurity.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication()
                .withUser("james").password("james").roles(BUYER)
                .and().withUser("jack").password("jack").roles(BUYER)
                .and().withUser("robert").password("robert").roles(BUYER)
                .and().withUser("michael").password("michael").roles(BUYER)
                .and().withUser("andrew").password("andrew").roles(BUYER)

                .and().withUser("mark").password("mark").roles(ADMIN)
                .and().withUser("sam").password("sam").roles(ADMIN)
                .and().withUser("vinay").password("vinay").roles(ADMIN)
                .and().withUser("mano").password("mano").roles(ADMIN)
                .and().withUser("cathy").password("cathy").roles(ADMIN);
    }

}
