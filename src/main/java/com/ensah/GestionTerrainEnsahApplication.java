package com.ensah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;


@SpringBootApplication
public class GestionTerrainEnsahApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionTerrainEnsahApplication.class, args);
	}




}
