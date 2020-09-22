package com.siit.bankingapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomLoginSuccessHandler successHandler;


    @Autowired
    private DataSource dataSource;

    @Value("${myapp.queries.users-query}")
    private String usersQuery;

    @Value("${myapp.queries.roles-query}")
    private String rolesQuery;


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
       authenticationManagerBuilder.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
                                   .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/recover-password/**").permitAll()
            .antMatchers("/update/password").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/index/**").hasAnyAuthority("SUPER_USER", "ADMIN_USER", "SITE_USER")
            .antMatchers("/employee/**").hasAnyAuthority("SUPER_USER", "ADMIN_USER")
            .antMatchers("/user/**").hasAuthority("SITE_USER")
            .anyRequest().authenticated()
            .and()
            .csrf().disable().formLogin()
            .loginPage("/login")
            .failureUrl("/login?error=true")
            .successHandler(successHandler)
            .usernameParameter("email")
            .passwordParameter("password")
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/").and()
            .exceptionHandling()
            .accessDeniedPage("/access-denied");
    }

}

