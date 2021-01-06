package com.app.epidemicsimulation.service;

import com.app.epidemicsimulation.model.SimulationSetUp;
import com.app.epidemicsimulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SimulationSetUpService
{
    private final SimulationRepository repository;
    @Autowired
    public SimulationSetUpService(SimulationRepository repository)
    {
        this.repository = repository;
    }

    public void save(SimulationSetUp setUp)
    {
        repository.save(setUp).subscribe();
    }
    public Flux<SimulationSetUp> findAll()
    {
        return repository.findAll();
    }
    public Flux<SimulationSetUp> findByName(String name)
    {
        return repository.findAllByN(name);
    }
    public Mono<SimulationSetUp> findById(String id)
    {
        return repository.findById(id);
    }
    public Mono<Void> deleteById(String id)
    {
       return repository.deleteById(id);
    }
}
