package com.example.common;

import com.example.service.UserDetailsServiceImpl;

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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

  @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/img/**", "/fonts/**", "/error");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().invalidSessionUrl("/timeout");

		http
		.authorizeRequests()
			.antMatchers("/to-register", "/register", "/ajax/**", "/to-login", "/logout").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/to-login").permitAll() // ログイン画面を表示するurl
			.loginProcessingUrl("/login") // ここへリクエストされるとログイン処理を開始する
			.defaultSuccessUrl("/")
			.failureUrl("/to-login?error")
			.usernameParameter("mailAddress")
			.passwordParameter("password")
			.and()
		.logout()
			.logoutUrl("/logout").permitAll()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/to-login")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true).permitAll();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}