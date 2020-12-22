package com.app.epidemicsimulation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SimulationSetUp
{
    private final String n; //simulation name
    private final int p; //population size
    private final int i; //initial infected
    private final double r; //virus reproduction rate indicator
    private final double m; //mortality rate indicator
    private final int ti; //number of days to recovery
    private final int tm; //number of days to death
    private final int ts; //simulation duration in days

}
