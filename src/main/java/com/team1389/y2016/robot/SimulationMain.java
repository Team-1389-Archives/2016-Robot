package com.team1389.y2016.robot;


import com.team1389.base.BaseConstants;
import com.team1389.base.Mode;
import com.team1389.base.RobotCode;
import com.team1389.base.Simulator;
import com.team1389.base.webserver.WebServer;

public class SimulationMain {
	private static final String baseResoucesLocation = BaseConstants.resourceBaseFolder + "webapp/";
	private static final String projectResourcesLocation = "Resources/projectSpecific";
	public static void main(String[] args){
		RobotCode code = makeRobotCode();
		WebServer server = WebserverSetup.initiateWebserver(code, baseResoucesLocation, projectResourcesLocation);
		Simulator.simulate(makeRobotCode(), Mode.TELEOP, server);
	}
	
	public static RobotCode makeRobotCode(){
		IOLayout io = new IOSimulated();
		RobotLayout layout = new RobotLayout(io, new Subsystems(io));
		return new Robot(layout);
		
	}
}
