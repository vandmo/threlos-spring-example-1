package se.vandmo.threlos.spring.example_1;

import static org.springframework.security.core.context.SecurityContextHolder.MODE_INHERITABLETHREADLOCAL;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @PostConstruct
  public void makeSecurityContextAvailableInSpawnedThreads() {
    SecurityContextHolder.setStrategyName(MODE_INHERITABLETHREADLOCAL);
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests(authorize -> authorize.anyRequest().authenticated())
        .httpBasic();
  }

  @Autowired
  public void configureTwoUsers(AuthenticationManagerBuilder authentication)
      throws Exception {
    authentication
        .inMemoryAuthentication()
        .withUser(User.builder().username("admin").password("{noop}verysecret").roles("Admin"))
        .withUser(User.builder().username("normaluser").password("{noop}secret").roles("User"));
  }

}