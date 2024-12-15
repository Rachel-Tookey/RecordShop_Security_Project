package com.example.group.project;

import com.example.group.project.model.repository.UserRepository;
import com.example.group.project.service.impl.UserDetailsServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetailsService;

@Slf4j
@SpringBootApplication
public class GroupProjectApplication {

	@Value("${spring.application.message}")
	private String message;

	@PostConstruct
	public void init() {
		log.info(message);
	}


	public static void main(String[] args) {
		SpringApplication.run(GroupProjectApplication.class, args);
	}

}
