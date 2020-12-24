package com.app.epidemicsimulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class EpidemicSimulationApplication
{
	public static void main(String[] args) {
		SpringApplication.run(EpidemicSimulationApplication.class, args);
	}
}
