package com.smartech.i2019.adaptiveinterviews.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/login", "/logout").permitAll().
                anyRequest().authenticated();
        httpSecurity.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/employees/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/interviews/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/users/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/departments/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "/departments/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/employees/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "/employees/**").access("hasRole('ROLE_ADMIN')");

        httpSecurity.authorizeRequests().and().formLogin().and().
                logout().logoutUrl("/logout").deleteCookies("JSESSIONID").logoutSuccessUrl("/login");
    }
}