package com.app.epidemicsimulation.repository;

import com.app.epidemicsimulation.model.SimulationSetUp;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends ReactiveMongoRepository<SimulationSetUp, String>
{
}
