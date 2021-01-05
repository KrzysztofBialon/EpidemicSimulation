package com.app.epidemicsimulation.controller;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationRecord;
import com.app.epidemicsimulation.model.SimulationSetUp;
import com.app.epidemicsimulation.service.SimulationRecordService;
import com.app.epidemicsimulation.service.SimulationSetUpService;
import com.app.epidemicsimulation.util.Simulation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/simulation")
public class mainController
{
    private final SimulationSetUpService setUpService;
    private final SimulationRecordService recordService;

    @Autowired
    public mainController(SimulationSetUpService setUpService, SimulationRecordService recordService)
    {
        this.setUpService = setUpService;
        this.recordService = recordService;
    }
    //Generates simulation on given conditions from JSON object passed in body
    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationDay> createSimulation(@Valid @RequestBody SimulationSetUp setUpBody)
    {
        SimulationSetUp setUp = setUpBody;
        setUp.setId(new ObjectId().toHexString()); //generate id for simulation set up
        Simulation simulation = new Simulation(setUp);
        simulation.calculate(); //run simulation
        //create SimulationRecord object with reference to its owner conditionsand attach all details of simulation as list.
        SimulationRecord simulationRecord = new SimulationRecord(setUp.getId(), simulation.getList());

        setUpService.saveBoth(setUp, simulationRecord, recordService);
        //return all record days to user
        return recordService.getSimulation(simulationRecord.getOwnerId());
    }

    @GetMapping(value = "/search/set_ups/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationSetUp> getAllSimulationSetUps()
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
        return setUpService.findById(id);
    }

    @GetMapping(value = "/search/record/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationRecord> getAllSimulationRecords()
    {
        return recordService.getAllSimulation();
    }

    @GetMapping(value = "/search/record/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SimulationDay> getSimulationRecordById(@PathVariable(value="id") String id)
    {
        return recordService.getSimulation(id);
    }
    //Modifies simulation at given id. Takes full valid body, generates new records, and replaces both in db.
    //204 NO_CONTENT on success
    @PutMapping(value = "/simulationSetUps/{id}")
    public Mono<ResponseEntity> modifySimulation(
            @Valid @RequestBody SimulationSetUp setUpBody, @PathVariable(value = "id") String id)
    {
        SimulationSetUp oldSetUp = setUpService.findById(id).block();
        SimulationSetUp setUp = setUpBody;
        setUp.setId(id);
        //if only name not match, update db, and return.
        if(setUp.equals(oldSetUp) && !setUpBody.getN().equals(oldSetUp.getN()))
        {
            setUpService.save(setUp);
            return Mono.just(ResponseEntity.noContent().build());
        }

        Simulation simulation = new Simulation(setUp);
        simulation.calculate();
        recordService.getByOwnerId(id).block();
        SimulationRecord simulationRecord = recordService.getByOwnerId(id).block();
        simulationRecord.setRecords(simulation.getList());

        setUpService.saveBoth(setUp, simulationRecord, recordService);

        return Mono.just(ResponseEntity.noContent().build());
    }

    @DeleteMapping(value = "/simulationSetUps/{id}")
    public Mono<ResponseEntity> deleteSimulation(@PathVariable(value = "id") String id)
    {
        return recordService.deleteByOwnerId(id)
                .then(setUpService.deleteById(id))
                .then(Mono.just(ResponseEntity.noContent().build()));

    }
}
