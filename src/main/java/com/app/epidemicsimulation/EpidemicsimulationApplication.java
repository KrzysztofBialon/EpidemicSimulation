package com.app.epidemicsimulation;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationSetUp;
import com.app.epidemicsimulation.util.Simulation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EpidemicSimulationApplication
{

	public static void main(String[] args) {
		SpringApplication.run(EpidemicSimulationApplication.class, args);
		List<SimulationDay> list = new ArrayList<>();
		SimulationSetUp setUp = new SimulationSetUp("test", 100, 10, 2, 0.5, 3, 2, 50);
		SimulationDay firstDay = new SimulationDay(setUp.getI(), setUp.getP()- setUp.getI(), 0, 0);
		list.add(firstDay);
		System.out.println("afdgfs" + list.get(0).toString());
		Simulation simulation = new Simulation(setUp);

		for(int i = 0; i< setUp.getTs(); i++)
		{
			list.add(simulation.calculate(setUp.getTm(), setUp.getTi(), list));
		}
	}

}
