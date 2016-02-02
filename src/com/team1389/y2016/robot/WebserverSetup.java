package com.team1389.y2016.robot;


import com.team1389.base.RobotCode;
import com.team1389.base.webserver.WebServer;

public class WebserverSetup {
	public static WebServer initiateWebserver(RobotCode code, String baseLoc, String projLoc){
		WebServer server = new WebServer(code, baseLoc, projLoc);
		return server;
	}
}
