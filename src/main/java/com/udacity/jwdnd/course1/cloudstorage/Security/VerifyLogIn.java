package com.udacity.jwdnd.course1.cloudstorage.Security;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationServiceProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class VerifyLogIn extends WebSecurityConfigurerAdapter {

    private final AuthenticationServiceProvider authenticationServiceProvider;

    public VerifyLogIn(AuthenticationServiceProvider authenticationServiceProvider) {
        this.authenticationServiceProvider = authenticationServiceProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationServiceProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //added this part to be able to access H2-console in the broswer
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/signup","/css/**", "/js/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.formLogin()
                .defaultSuccessUrl("/file", true);

        http.logout()
                .logoutSuccessUrl("/login").permitAll();
    }


}

