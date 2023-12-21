package com.example.lab6;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(

				title = "Task Manager API",
				description = "This is project for Spring labs"

		),
		servers = {

				@Server(url = "http://localhost:8080", description = "test server"),
				@Server(url = "http://example.com", description = "production server")
		}

)
@SpringBootApplication
public class Lab6Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab6Application.class, args);
	}

}
