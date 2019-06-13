package com.tvs.iot.config;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.tvs.iot.*")
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String SALT = "salt"; // Salt should be protected carefully
    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/about/**",
            "/contact/**",
            "/error/**/*",
            "/api/**",
            "/console/**",
            "/signup",
            "/actuator/**",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**"

    };
    @Autowired
    private Environment env;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(PUBLIC_MATCHERS)
            .permitAll().anyRequest().authenticated();

        http
            .csrf().disable().cors().disable()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
            .and()
            .rememberMe();

    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("9999").password("9900").roles("USER"); //This is in-memory authentication
        log.debug("jhkjhj");
      //  auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());


    }


}
