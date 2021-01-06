package com.app.epidemicsimulation.service;

import com.app.epidemicsimulation.model.SimulationRecord;
import com.app.epidemicsimulation.model.SimulationSetUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GeneralService
{
    private final SimulationSetUpService setUpService;
    private final SimulationRecordService recordService;

    @Autowired
    public GeneralService(SimulationSetUpService setUpService, SimulationRecordService recordService)
    {
        this.setUpService = setUpService;
        this.recordService = recordService;
    }

    @Transactional
    public void saveBoth(SimulationSetUp setUp, SimulationRecord record)
    {
        setUpService.save(setUp);
        recordService.save(record);
    }
    @Transactional
    public void deleteBoth(String id)
    {
        setUpService.deleteById(id);
        recordService.deleteByOwnerId(id);
    }
}
