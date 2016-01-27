package com.team1389.y2016.robot;

import com.team1389.base.Mode;
import com.team1389.base.Simulator;

public class SimulationMain {
	public static void main(String[] args){
		Simulator.simulate(new Robot(new IOSimulated()), Mode.TELEOP);
	}
}
