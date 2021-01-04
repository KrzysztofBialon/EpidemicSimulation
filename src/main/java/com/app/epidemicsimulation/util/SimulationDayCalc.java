package com.app.epidemicsimulation.util;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationSetUp;

import java.util.List;

import static java.lang.StrictMath.abs;

public class SimulationDayCalc
{
    public static SimulationDay calculate(List<SimulationDay> list, SimulationSetUp setUp)
    {
        int infected = 0;
        int vulnerable = 0;
        int dead = 0;
        int resistant = 0;
        int prevInfected = list.get(list.size()-1).getPi();
        int prevDead = list.get((list.size()-1)).getPm();
        int prevResistant = list.get((list.size()-1)).getPr();
        int newInfected= (int) (prevInfected * setUp.getR());
        int infectedTmMinusOneDaysAgo = list.size() - setUp.getTm()-2 < 0 ? 0 : list.get(list.size() - setUp.getTm()-2).getPi();
        int infectedTmDaysAgo = list.size()-1 - setUp.getTm() < 0 ? 0 : list.get(list.size()-1 - setUp.getTm()).getPi();
        int infectedTiMinusOneDaysAgo = list.size() - setUp.getTi()-2 < 0 ? 0 : list.get(list.size() - setUp.getTi()-2).getPi();
        int infectedTiDaysAgo = list.size()-1 - setUp.getTi() < 0 ? 0 : list.get(list.size()-1 - setUp.getTi()).getPi();
        int diffTm = infectedTmDaysAgo - infectedTmMinusOneDaysAgo;
        if(diffTm < 0)
            diffTm *= 2;
        int diffTi = infectedTiDaysAgo - infectedTiMinusOneDaysAgo;
        if(diffTi < 0)
            diffTi *= 2;
        int differentialTm = abs(diffTm);//diffTm <= 0 ? 0 : diffTm;
        int differentialTi = abs(diffTi);//diffTi <= 0 ? 0 : diffTi;
        int totalDead = (int) (differentialTm * setUp.getM()) + prevDead;
        int totalResistant = ((int) (differentialTi * setUp.getM()) + prevResistant);
        if(prevDead + prevResistant >= setUp.getP())
        {
            dead = prevDead;
            resistant = prevResistant;
        } else
        {
            if(totalDead + totalResistant >= setUp.getP())
                totalResistant -= (totalDead + totalResistant) % setUp.getP();
            if(setUp.getTi() < setUp.getTm())
            {
                resistant += (prevResistant + differentialTi);
                if(resistant >= setUp.getP())
                    resistant = setUp.getP();
                vulnerable -= newInfected;
                if(vulnerable <= 0)
                    vulnerable = 0;
                infected = newInfected + prevInfected - differentialTi;
                if(infected >= setUp.getP())
                    infected = setUp.getP() - resistant;
            } else
            {
                infected = newInfected + prevInfected >= setUp.getP() ? setUp.getP() : prevInfected + newInfected;
                vulnerable = vulnerable - newInfected <= 0 ? 0 : vulnerable - newInfected;
                dead = totalDead >= setUp.getP() ? setUp.getP() : totalDead;
                resistant = totalResistant >= setUp.getP() ? setUp.getP() : totalResistant;
                infected = infected - (dead + resistant) <= 0 ? 0 : infected - (dead + resistant);
            }
        }

        return new SimulationDay(infected, vulnerable, dead, resistant);
    }
}
