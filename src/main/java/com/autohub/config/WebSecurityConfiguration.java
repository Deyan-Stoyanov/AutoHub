package com.autohub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers("/styles/**", "/scripts/**", "/images/**", "/content/**", "C:\\Users\\Lenovo\\autohub\\images/**", "/about", "/contacts", "mailto:**").permitAll()
                .antMatchers("/login", "/register", "/").anonymous()
                .antMatchers("/articles/create/**", "/articles/delete/**", "/articles/edit/**", "/marketplace/pending", "/marketplace/cars/approve", "/marketplace/cars/decline", "/marketplace/parts/approve", "/marketplace/parts/decline").hasAnyRole("ADMIN", "ROOT")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();

    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
                new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
