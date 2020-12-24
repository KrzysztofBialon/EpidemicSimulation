package com.app.epidemicsimulation.service;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationRecord;
import com.app.epidemicsimulation.repository.RecordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SimulationRecordService
{
    private RecordRepository recordRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public SimulationRecordService(RecordRepository recordRepository,
                                   ObjectMapper objectMapper)
    {
        this.recordRepository = recordRepository;
        this.objectMapper = objectMapper;
    }

    public void save(SimulationRecord simulationRecord)
    {
        recordRepository.insert(simulationRecord).subscribe();
    }

    public Flux<SimulationRecord> getAllSimulation()
    {
        return recordRepository.findAll();
    }
    public Flux<SimulationDay> getSimulation(String id)
    {
        return
                recordRepository.
                        findById(id)
                        .map(x->
                                x.getRecords().stream())
                        .flatMapMany((Flux::fromStream));
    }
}
