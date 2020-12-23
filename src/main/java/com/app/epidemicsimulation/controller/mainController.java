package com.app.epidemicsimulation.controller;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationRecord;
import com.app.epidemicsimulation.model.SimulationSetUp;
import com.app.epidemicsimulation.service.SimulationRecordService;
import com.app.epidemicsimulation.service.SimulationSetUpService;
import com.app.epidemicsimulation.util.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class mainController
{
   /* @PostMapping("/simulation")
    public Flux<SimulationDay> setSimulation(@RequestBody SimulationSetUp simulationSetUp)
    {

    }*/
    private SimulationSetUpService setUpService;
    private SimulationRecordService recordService;

    @Autowired
    public mainController(SimulationSetUpService setUpService,
                          SimulationRecordService recordService)
    {
        this.setUpService = setUpService;
        this.recordService = recordService;
    }

    @GetMapping("/")
    public void test()
    {
        List<SimulationDay> list = new ArrayList<>();
        SimulationSetUp setUp = new SimulationSetUp("test", 100, 10, 2, 0.5, 2, 1, 50);

        SimulationDay firstDay = new SimulationDay(setUp.getI(), setUp.getP()- setUp.getI(), 0, 0);
        list.add(firstDay);
        Simulation simulation = new Simulation(setUp);

        for(int i = 0; i< setUp.getTs(); i++)
        {
            list.add(simulation.calculate(setUp.getTm(), setUp.getTi(), list));
        }

        setUpService.save(setUp);
        recordService.save(new SimulationRecord(setUp.getSimulationRecordReference(), list));

    }
}
