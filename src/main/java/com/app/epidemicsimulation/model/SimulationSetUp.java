package com.app.epidemicsimulation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "SimulationSetUps")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationSetUp
{
    @Id
    private ObjectId id;
    @JsonProperty(value = "n")
    private String n; //simulation name
    @JsonProperty(value = "p")
    private int p; //population size
    @JsonProperty(value = "i")
    private int i; //initial infected
    @JsonProperty(value = "r")
    private double r; //virus reproduction rate indicator
    @JsonProperty(value = "m")
    private double m; //mortality rate indicator
    @JsonProperty(value = "ti")
    private int ti; //number of days to recovery
    @JsonProperty(value = "tm")
    private int tm; //number of days to death
    @JsonProperty(value = "ts")
    private int ts; //simulation duration in days

    public SimulationSetUp(String n, int p, int i, double r, double m, int ti, int tm, int ts)
    {
        this.id = new ObjectId();
        this.n = n;
        this.p = p;
        this.i = i;
        this.r = r;
        this.m = m;
        this.ti = ti;
        this.tm = tm;
        this.ts = ts;
    }
}
