package com.app.epidemicsimulation.util;
import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationSetUp;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

public class Simulation
{
    private SimulationSetUp setUp;
    @Getter
    private List<SimulationDay> list;

    public Simulation(SimulationSetUp setUp)
    {
        this.setUp = setUp;
        list = new ArrayList<>();
        list.add(new SimulationDay(setUp.getI(), setUp.getP()- setUp.getI(), 0, 0));
    }
    //calculate details for each day and save it into list
    public List<SimulationDay> calculate()
    {
       for(int i = 0; i< setUp.getTs(); i++)
       {
           list.add(SimulationDayCalc.calculate(list, setUp));
       }
       return list;
    }
}
