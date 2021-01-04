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
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/simulation")
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
    //Generates simulation on given conditions from JSON object passed in body
    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationDay> createSimulation(@RequestBody JSONObject setUpBody) throws JsonProcessingException
    {
        List<SimulationDay> list = new ArrayList<>();
        SimulationSetUp setUp = objectMapper.readValue(setUpBody.toJSONString(), SimulationSetUp.class);
        SimulationDay firstDay = new SimulationDay(setUp.getI(), setUp.getP()- setUp.getI(), 0, 0);
        Simulation simulation = new Simulation(setUp);

        list.add(firstDay);

        for(int i = 0; i< setUp.getTs(); i++)
        {
            list.add(simulation.calculate(setUp.getTm(), setUp.getTi(), list));
        }

        setUpService.save(setUp);

        SimulationRecord simulationRecord = new SimulationRecord(setUp.getId(), list);

        recordService.save(simulationRecord);

        return recordService.getSimulation(simulationRecord.getOwnerId().toHexString());
    }

    @GetMapping(value = "/search/set_ups/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationSetUp> getSimulationSetUpByName()
    {
        return setUpService.findAll();
    }
    @GetMapping(value = "/search/set_ups/{name}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationSetUp> getSimulationSetUpByName(@PathVariable(value="name") String name)
    {
        return setUpService.findByName(name);
    }
    @GetMapping("search/set_ups/{id}")
    public Mono<SimulationSetUp> getSimulationSetUpById(@PathVariable(value="id") String id)
    {
        return setUpService.findByReferenceId(id);
    }

    @GetMapping("/search/set_ups/sort/{sort}")
    public Flux<SimulationSetUp> getSimulationSetUpBy(@PathVariable(value = "sort") String sort,
                                                      @RequestParam(value = "from") double from,
                                                      @RequestParam(value = "to") double to)
    {
        return setUpService.sort(sort, from, to);
    }
    @GetMapping(value = "/search/record/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationRecord> getAllSimulationRecord()
    {
        return recordService.getAllSimulation();
    }

    @GetMapping(value = "/search/record/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationDay> getSimulationRecord(@PathVariable(value="id") String id)
    {
        return recordService.getSimulation(id);
    }

}
