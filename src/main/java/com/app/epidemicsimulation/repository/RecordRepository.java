package com.app.epidemicsimulation.repository;

import com.app.epidemicsimulation.model.SimulationRecord;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends ReactiveMongoRepository<SimulationRecord, String>
{
}
