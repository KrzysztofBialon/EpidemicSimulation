package com.app.epidemicsimulation.repository;

import com.app.epidemicsimulation.model.SimulationSetUp;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SimulationRepository extends ReactiveMongoRepository<SimulationSetUp, String>
{
    Flux<SimulationSetUp> findAllByN(String name);
    Mono<SimulationSetUp> findById(String id);
}
