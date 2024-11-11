package com.example.LunchMatch;

import com.example.LunchMatch.auth.Role;
import com.example.LunchMatch.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class LunchMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunchMatchApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository repo) {
		return args -> {
			if (repo.findByName("USER").isEmpty()) {
				repo.save(
						Role.builder()
								.name("USER").build()
				);
			}
		};
	}

}
