package com.team1389.y2016.robot;

import org.strongback.command.Command;

import com.team1389.base.TeleopBase;

//TODO: make this work the way AutonomousMain does, plus also a TestMain

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	
	public TeleopMain(RobotLayout layout) {
		this.layout = layout;
	}

	@Override
	public void setupTeleop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public  Command provideCommand() {
		return null;
	}

}
