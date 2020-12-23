package com.app.epidemicsimulation.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "DayRecords")
@RequiredArgsConstructor
public class SimulationRecord
{
    @Id
    private final String id;
    private final List<SimulationDay> records;
}
