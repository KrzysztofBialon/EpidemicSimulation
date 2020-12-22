package com.app.epidemicsimulation.controller;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationSetUp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("")
public class mainController
{
   /* @PostMapping("/simulation")
    public Flux<SimulationDay> setSimulation(@RequestBody SimulationSetUp simulationSetUp)
    {

    }*/
}
