package com.crawler.web.crawler.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/start");

        http.authorizeRequests().antMatchers("/login*").permitAll();

        //http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        //http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

//        http
//                .cors().and()
//                .csrf().disable().authorizeRequests()
//                .antMatchers("/start").hasRole("manager")
//          ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user1").
                password(passwordEncoder().encode("user1pass")).roles("USER")
                .and()
                .withUser("user2")
                .password(passwordEncoder().encode("user2pass")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("adminpass")).roles("ADMIN")
                ;
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
