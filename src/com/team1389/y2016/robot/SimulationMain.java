package com.team1389.y2016.robot;

import com.team1389.base.Mode;
import com.team1389.base.RobotCode;
import com.team1389.base.Simulator;
import com.team1389.base.webserver.WebServer;

public class SimulationMain {
	public static void main(String[] args){
		RobotCode code = makeRobotCode();
		WebServer server = WebserverSetup.initiateWebserver(code);
		server.start();
		Simulator.simulate(makeRobotCode(), Mode.TELEOP);
	}
	
	public static RobotCode makeRobotCode(){
		IOLayout io = new IOSimulated();
		RobotLayout layout = new RobotLayout(io, new Subsystems(io));
		return new Robot(layout);
		
	}
}
