package com.app.epidemicsimulation.service;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationRecord;
import com.app.epidemicsimulation.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationRecordService
{
    private RecordRepository recordRepository;

    @Autowired
    public SimulationRecordService(RecordRepository recordRepository)
    {
        this.recordRepository = recordRepository;
    }

    public void save(SimulationRecord simulationRecord)
    {
        recordRepository.insert(simulationRecord).subscribe();
    }
}
