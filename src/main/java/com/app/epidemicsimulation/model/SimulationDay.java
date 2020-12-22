package com.app.epidemicsimulation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SimulationDay
{
    private final int pi; //number of infected
    private final int pv; //number of susceptible
    private final int pm; //number of death
    private final int pr; //number of recovered & resistant

    @Override
    public String toString()
    {
        return "SimulationDay{" +
                "pi=" + pi +
                ", pv=" + pv +
                ", pm=" + pm +
                ", pr=" + pr +
                '}';
    }
}
