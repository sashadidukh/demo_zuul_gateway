package com.example.zuul.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class BookApplication {


	@RequestMapping(value = "/available")
	public String available() {
		System.out.println("bla bla");
		return "Spring in Action";
	}


	@RequestMapping(value = "/checked-out")
	public String checkedOut() {
		return "Spring Boot in Action";
	}


	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}
}
