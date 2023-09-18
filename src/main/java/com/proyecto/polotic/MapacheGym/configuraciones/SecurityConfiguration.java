package com.proyecto.polotic.MapacheGym.configuraciones;

import com.proyecto.polotic.MapacheGym.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private EmpleadoServicio empleadoServicio;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(empleadoServicio).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/", "/asistencias/nueva", "asistencias/crear", "/registro/nuevo", "/css/*",
								"/images/*",
								"/images/*/*", "/js/*")
						.permitAll()
						.requestMatchers("/clientes", "/clientes/*")
						.hasAnyRole("Instructor", "Administrador", "Usuario")
						.requestMatchers("/asistencias", "/asistencias/*").hasAnyRole("Usuario", "Administrador")
						.requestMatchers("/membresias", "/membresias/*").hasAnyRole("Administrador")
						.requestMatchers("/empleados", "/empleados/*").hasAnyRole("Administrador")
						.anyRequest().authenticated())
				.formLogin((form) -> form
						.loginPage("/login")
						.loginProcessingUrl("/logincheck")
						.usernameParameter("usuario")
						.passwordParameter("password")
						.permitAll())
				.logout((logout) -> logout.permitAll());

		return http.build();
	}

}