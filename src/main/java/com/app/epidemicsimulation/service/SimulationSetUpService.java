package com.app.epidemicsimulation.service;

import com.app.epidemicsimulation.model.SimulationSetUp;
import com.app.epidemicsimulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SimulationSetUpService
{
    private final SimulationRepository repository;

    @Autowired
    public SimulationSetUpService(SimulationRepository repository)
    {
        this.repository = repository;
    }

    public Flux<SimulationSetUp> getAll()
    {
        return repository.findAll();
    }

    public void save(SimulationSetUp setUp)
    {
        repository.insert(setUp).subscribe();
    }
}
