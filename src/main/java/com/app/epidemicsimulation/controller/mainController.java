package com.app.epidemicsimulation.controller;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationRecord;
import com.app.epidemicsimulation.model.SimulationSetUp;
import com.app.epidemicsimulation.service.SimulationRecordService;
import com.app.epidemicsimulation.service.SimulationSetUpService;
import com.app.epidemicsimulation.util.Simulation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class mainController
{
    private final SimulationSetUpService setUpService;
    private final SimulationRecordService recordService;
    private final ObjectMapper objectMapper;

    @Autowired
    public mainController(SimulationSetUpService setUpService,
                          SimulationRecordService recordService,
                          ObjectMapper objectMapper)
    {
        this.setUpService = setUpService;
        this.recordService = recordService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/simulation", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationDay> createSimulation(@RequestBody JSONObject setUpBody) throws JsonProcessingException
    {
        List<SimulationDay> list = new ArrayList<>();
        SimulationSetUp setUp = objectMapper.readValue(setUpBody.toJSONString(), SimulationSetUp.class);
        SimulationDay firstDay = new SimulationDay(setUp.getI(), setUp.getP()- setUp.getI(), 0, 0);
        list.add(firstDay);
        Simulation simulation = new Simulation(setUp);

        for(int i = 0; i< setUp.getTs(); i++)
        {
            list.add(simulation.calculate(setUp.getTm(), setUp.getTi(), list));
        }
        setUpService.save(setUp);

        SimulationRecord simulationRecord = new SimulationRecord(setUp.getSimulationRecordReference(), list);
        recordService.save(simulationRecord);

        return recordService.getSimulation(simulationRecord.getId());
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
