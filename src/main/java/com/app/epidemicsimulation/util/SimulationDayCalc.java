package com.app.epidemicsimulation.util;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationSetUp;

import java.util.List;

import static java.lang.StrictMath.abs;

public class SimulationDayCalc
{
    public static final SimulationDay calculate(List<SimulationDay> list, SimulationSetUp setUp)
    {
        //Get previous day details from list
        SimulationDay prevDay = new SimulationDay(
                list.get(list.size()-1).getPi(), 0, list.get((list.size()-1)).getPm(), list.get((list.size()-1)).getPr());
        int newInfected= (int) (prevDay.getPi() * setUp.getR()); //new infected based on previous multiplied by R factor
        int infected = 0; //total infected for the day
        int vulnerable = 0; //total vulnerable to infection for the day
        int dead = 0; //total dead for the day
        int resistant = 0; //total resistant (left after pr days but not dead after pm days) for the day
        int infectedTmMinusOneDaysAgo = extractPrevData(setUp.getTm(), 2, list);//infected tm-1 days ago
        int infectedTiMinusOneDaysAgo = extractPrevData(setUp.getTi(), 2, list);//infected ti-1 days ago
        int infectedTmDaysAgo = extractPrevData(setUp.getTm(), 1, list);//infected tm days ago
        int infectedTiDaysAgo = extractPrevData(setUp.getTi(), 1, list);//infected tm- days ago
        int diffTm = infectedTmDaysAgo - infectedTmMinusOneDaysAgo;//number of infected tm days ago to calculate deaths
        int diffTi = infectedTiDaysAgo - infectedTiMinusOneDaysAgo;//number of infected tm days ago to calculate recovery
        // if last infected t days ago and t-1 grater, recalculate not to loose difference
        if(diffTm < 0)
            diffTm = abs(diffTm) * 2;

        if(diffTi < 0)
            diffTi = abs(diffTi) * 2;

        int totalDead = (int) (diffTm * setUp.getM()) + prevDay.getPm(); //total dead equals tm days ago infected * mortality indicator + total from prev day
        int totalResistant = ((int) (diffTi * setUp.getM()) + prevDay.getPr()); //total recovered equals not dead from ti days ago
        //if total dead + total recovered >= population set on prev and continue with simulation
        if(prevDay.getPm() + prevDay.getPr() >= setUp.getP())
        {
            dead = prevDay.getPm();
            resistant = prevDay.getPr();
        } else
        {
            if(totalDead + totalResistant >= setUp.getP()) //if tD + tR >= P approx. resistant by exceeding value
                totalResistant -= (totalDead + totalResistant) % setUp.getP();
            //calc for ti<tm. No deaths, calc for infection & recovery
            if(setUp.getTi() < setUp.getTm())
            {
                resistant += (prevDay.getPr() + diffTi);
                if(resistant >= setUp.getP())
                    resistant = setUp.getP();
                vulnerable -= newInfected;
                if(vulnerable <= 0)
                    vulnerable = 0;
                infected = newInfected + prevDay.getPi() - diffTi;
                if(infected >= setUp.getP())
                    infected = setUp.getP() - resistant;
            } else //ti>=tm
            {
                infected = newInfected + prevDay.getPi() >= setUp.getP() ? setUp.getP() : prevDay.getPi() + newInfected;//infected > P = P
                vulnerable = vulnerable - newInfected <= 0 ? 0 : vulnerable - newInfected; //0 if infected depleted, else vulnerable less about infected this day
                dead = totalDead >= setUp.getP() ? setUp.getP() : totalDead; //deaths exeeds P ? = P, else set on total deads
                resistant = totalResistant >= setUp.getP() ? setUp.getP() : totalResistant; //resistant > P = P, else set on total
                infected = infected - (dead + resistant) <= 0 ? 0 : infected - (dead + resistant);//if infected < 0 set 0 (inf depleeted), else difference of deaths+resistant
            }
        }
        return new SimulationDay(infected, vulnerable, dead, resistant);
    }
    //get previously infected given tm or ti days ago
    private static final int extractPrevData(int daysAgo, int shift, List<SimulationDay> list)
    {
        return list.size() - daysAgo-shift < 0 ? 0 : list.get(list.size() - daysAgo-shift).getPi();
    }
}
