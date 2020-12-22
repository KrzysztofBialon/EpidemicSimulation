package com.app.epidemicsimulation.util;

import com.app.epidemicsimulation.model.SimulationDay;

import java.util.List;

public interface ISimulationCalculations
{
    SimulationDay calculate(/*SimulationDay simulationDay,
                            SimulationDay tmSimulationDay,
                            SimulationDay tiSimulationDay,*/
            int tm,int ti,
                            List<SimulationDay> list);
}
