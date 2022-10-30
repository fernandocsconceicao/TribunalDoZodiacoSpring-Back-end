package com.lemomcrab.TribunaldoZodiacoBackend.security;

import com.lemomcrab.TribunaldoZodiacoBackend.security.filter.CustomAuthenticationFilter;
import com.lemomcrab.TribunaldoZodiacoBackend.security.filter.CustomAuthorizationFilter;
import com.lemomcrab.TribunaldoZodiacoBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter
                (super.authenticationManager());

        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        httpSecurity.sessionManagement().sessionCreationPolicy(STATELESS);
        httpSecurity.authorizeRequests().antMatchers("/signup/**", "/api/login/**", "/swagger-ui.html")
                .permitAll();
        httpSecurity.authorizeRequests().antMatchers("/token/refresh/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/user/role/addtouser", "/user/role/save")
                .hasAnyAuthority("ADMIN");
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.csrf().disable();
        httpSecurity.addFilter(customAuthenticationFilter);

    }

    @Bean
    AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
}
