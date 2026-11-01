
package com.student.studentmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.student.studentmanagement.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).formLogin(form -> form.disable()).httpBasic(basic -> basic.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(
						exceptions -> exceptions.authenticationEntryPoint((request, response, authException) -> response
								.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))

				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/register", "/auth/login", "/error")
						.permitAll()

						// Only ADMIN can add, update, or delete students
						.requestMatchers(org.springframework.http.HttpMethod.POST, "/students", "/students/**")
						.hasRole("ADMIN")

						.requestMatchers(org.springframework.http.HttpMethod.PUT, "/students", "/students/**")
						.hasRole("ADMIN")

						.requestMatchers(org.springframework.http.HttpMethod.PATCH, "/students", "/students/**")
						.hasRole("ADMIN")

						.requestMatchers(org.springframework.http.HttpMethod.DELETE, "/students", "/students/**")
						.hasRole("ADMIN")
						// Only ADMIN can create departments and courses
						.requestMatchers(org.springframework.http.HttpMethod.POST, "/departments", "/departments/**",
								"/courses", "/courses/**")
						.hasRole("ADMIN")

						// Only ADMIN can update departments and courses
						.requestMatchers(org.springframework.http.HttpMethod.PUT, "/departments", "/departments/**",
								"/courses", "/courses/**")
						.hasRole("ADMIN")

						// Only ADMIN can partially update departments and courses
						.requestMatchers(org.springframework.http.HttpMethod.PATCH, "/departments", "/departments/**",
								"/courses", "/courses/**")
						.hasRole("ADMIN")

						// Only ADMIN can delete departments and courses
						.requestMatchers(org.springframework.http.HttpMethod.DELETE, "/departments", "/departments/**",
								"/courses", "/courses/**")
						.hasRole("ADMIN")
						// Both USER and ADMIN must be authenticated to access other endpoints
						.anyRequest().authenticated())

				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
