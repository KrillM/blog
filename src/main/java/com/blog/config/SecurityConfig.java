package com.blog.config;

import com.blog.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    WriterService writerService;

    @Override
    protected void configure(HttpSecurity hs) throws Exception{
        hs.formLogin()
                .loginPage("/writer/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/writer/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/writer/logout"))
                .logoutSuccessUrl("/");
        hs.authorizeRequests()
                .mvcMatchers("/","/writer/**","/read/**","/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        hs.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(writerService).passwordEncoder(passwordEncoder());
    }
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**", "/js/**","/img/**");
    }
}
