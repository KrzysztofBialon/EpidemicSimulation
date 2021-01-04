package com.app.epidemicsimulation.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "DayRecords")
@Getter
@RequiredArgsConstructor
public class SimulationRecord
{
    @Id
    private final ObjectId ownerId;
    private final List<SimulationDay> records;
}
