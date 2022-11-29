package com.cryptshare.planningpoker;

import com.cryptshare.planningpoker.data.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
class SecurityConfig {

	// https://docs.spring.io/spring-security/reference/servlet/integrations/mvc.html#mvc-enablewebmvcsecurity
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// TODO
		http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()).httpBasic(withDefaults()).csrf().disable();
		return http.build();
	}

	@Bean
	UserDetailsManager userDetailsService(UserRepository userRepository, DataSource dataSource) {
		final JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
		userDetailsManager.setEnableGroups(false);

		userDetailsManager.createUser(User.withDefaultPasswordEncoder().username("John Doe").password("changeme").roles("USER").build());
		userDetailsManager.createUser(User.withDefaultPasswordEncoder().username("Alice").password("changeme").roles("USER").build());

		return userDetailsManager;
	}

}
