package com.app.epidemicsimulation.repository;

import com.app.epidemicsimulation.model.SimulationRecord;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RecordRepository extends ReactiveMongoRepository<SimulationRecord, String>
{
    Mono<SimulationRecord> findByOwnerId(String id);
    Mono<Void> deleteByOwnerId(String id);
}
