package com.proyecto.polotic.MapacheGym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.proyecto.polotic.MapacheGym.repositorios")
@EntityScan(basePackages = "com.proyecto.polotic.MapacheGym.entidades")
public class MapacheGymApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapacheGymApplication.class, args);
	}

}
