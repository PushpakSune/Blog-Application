package com.pss.project;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiProjectApplication {

	/* Model Mapper is used to map entity to dto and vice versa
	 We have used this in Comment Service class but not in Post Service class
	 */

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiProjectApplication.class, args);
		System.out.println("************** Application Started ******************* ");
	}
}
