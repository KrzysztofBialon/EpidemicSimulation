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

        SimulationRecord simulationRecord = new SimulationRecord(setUp.getSimulationRecordReference(), list);

        recordService.save(simulationRecord);

        return recordService.getSimulation(simulationRecord.getId());
    }

    @GetMapping(value = "/search/record/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationRecord> getAllSimulationRecord()
    {
        return recordService.getAllSimulation();
    }
    @GetMapping(value = "/search/record", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationDay> getSimulationRecord(@RequestParam(value="id") String id)//TODO czy zostawic id jako reference czy osobno id do warstwy dostepu a osobno do refa
    {
        return recordService.getSimulation(id);
    }//TODO czy ustawic id przed save i jedno dl aobu kolekcji czy w trakcie
    @GetMapping(value = "/search/set_up", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationSetUp> getSimulationSetUpByName(@RequestParam(value="name") String name)
    {
        return setUpService.findByName(name);
    }//TODO endpoint do sortowania po parametrach
    @GetMapping("search/set_up/{ref_id}")
    public Mono<SimulationSetUp> getSimulationSetUpByReferenceId(@PathVariable String ref_id)
    {
        return setUpService.findByReferenceId(ref_id);
    }

}
