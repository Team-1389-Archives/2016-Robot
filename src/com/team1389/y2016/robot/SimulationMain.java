package com.team1389.y2016.robot;

import com.team1389.base.Mode;
import com.team1389.base.Simulator;

public class SimulationMain {
	public static void main(String[] args){
		IOLayout io = new IOSimulated();
		RobotLayout layout = new RobotLayout(io, new Subsystems(io));
		Simulator.simulate(new Robot(layout), Mode.TELEOP);
	}
}
