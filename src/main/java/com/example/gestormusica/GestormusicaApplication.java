package com.example.gestormusica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestormusicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestormusicaApplication.class, args);
		CreateDB.main(args);
		
	}

}
