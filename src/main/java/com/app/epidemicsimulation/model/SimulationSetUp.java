package com.app.epidemicsimulation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "SimulationSetUps")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationSetUp
{
    @Id
    private String id;
    @JsonProperty(value = "n", required = true)
    @NotNull
    @NotBlank
    private String n; //simulation name
    @JsonProperty(value = "p", required = true)
    @NotNull
    @Min(1)
    private Integer p; //population size
    @JsonProperty(value = "i", required = true)
    @NotNull
    @Min(1)
    private Integer i; //initial infected
    @JsonProperty(value = "r", required = true)
    @NotNull
    @Min(0)
    private Double r; //virus reproduction rate indicator
    @JsonProperty(value = "m", required = true)
    @NotNull
    @Min(0)
    private Double m; //mortality rate indicator
    @JsonProperty(value = "ti", required = true)
    @NotNull
    @Min(1)
    private Integer ti; //number of days to recovery
    @JsonProperty(value = "tm", required = true)
    @NotNull
    @Min(1)
    private Integer tm; //number of days to death
    @JsonProperty(value = "ts", required = true)
    @NotNull
    @Min(1)
    private Integer ts; //simulation duration in days

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof  SimulationSetUp))
            return false;

        SimulationSetUp setUp = (SimulationSetUp) obj;
        //name check after this, to omit unnecessary ode and calculations if only name to change
        return (this.p.equals(setUp.p) &&
                this.i.equals(setUp.i) &&
                this.r.equals(setUp.r) &&
                this.m.equals(setUp.m) &&
                this.ti.equals(setUp.ti) &&
                this.tm.equals(setUp.tm) &&
                this.ts.equals(setUp.ts));
    }
}
