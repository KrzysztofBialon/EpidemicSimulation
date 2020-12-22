package com.app.epidemicsimulation.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimulationDay
{
    private final int pi; //number of infected
    private final int pv; //number of susceptible
    private final int pm; //number of death
    private final int pr; //number of recovered & resistant
}
