package com.app.epidemicsimulation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@Document(collection = "SimulationSetUps")
public class SimulationSetUp
{
    @Id
    private String id;
    private final String n; //simulation name
    private final int p; //population size
    private final int i; //initial infected
    private final double r; //virus reproduction rate indicator
    private final double m; //mortality rate indicator
    private final int ti; //number of days to recovery
    private final int tm; //number of days to death
    private final int ts; //simulation duration in days
    private String SimulationRecordReference = new ObjectId().toHexString();
}
