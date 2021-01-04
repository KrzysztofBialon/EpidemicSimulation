package com.app.epidemicsimulation.service;

import com.app.epidemicsimulation.model.SimulationSetUp;
import com.app.epidemicsimulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        repository.insert(setUp).subscribe();
    }
    public Flux<SimulationSetUp> findAll()
    {
        return repository.findAll();
    }
    public Flux<SimulationSetUp> findByName(String name)
    {
        return repository.findAllByN(name);
    }
    public Mono<SimulationSetUp> findByReferenceId(String id)
    {
        return repository.findById(id);
    }
    public Flux<SimulationSetUp> sort(String sort, double from, double to)
    {
        return sortLogic(sort, from, to);
    }

    private Flux<SimulationSetUp> sortLogic(String sort, double from, double to)
    {
        Sort sortQuery = Sort.by(
                Sort.Order.by(sort),
                Sort.Order.asc(String.valueOf(from)),
                Sort.Order.desc(String.valueOf(to)));
        Sort testSort = (Sort) Sort.by(sort).and(Sort.Order.asc(String.valueOf(from)));
        return repository.findAll(testSort);
    }
}
