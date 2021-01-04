package com.app.epidemicsimulation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

@Document(collection = "DayRecords")
@Getter
@Setter
public class SimulationRecord
{
    @Id
    private String id;
    @JsonProperty(value = "ownerId")
    private String ownerId;
    @JsonProperty(value = "records")
    @OneToMany(targetEntity = SimulationDay.class, cascade = CascadeType.ALL)
    private List<SimulationDay> records;

    public SimulationRecord(String ownerId, List<SimulationDay> records)
    {
        this.ownerId = ownerId;
        this.records = records;
    }
}
