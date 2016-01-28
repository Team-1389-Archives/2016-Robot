package com.team1389.y2016.robot;

import com.team1389.base.RobotCode;
import com.team1389.base.TeleopBase;
import com.team1389.base.auton.AutonomousBase;

/**
 * This class defines where the teleop and auton bases are.
 * The code in this file wony usually have to be changed.
 */
public class Robot implements RobotCode{
	TeleopMain teleopBase;
	AutonomousMain autonomousBase;
	RobotLayout io;
	
	public Robot(RobotLayout io) {
		teleopBase = new TeleopMain(io);
		autonomousBase = new AutonomousMain(io);
		this.io = io;
	}

	public TeleopBase getTeleopBase() {
		return teleopBase;
	}

	public AutonomousBase getAutonomousBase() {
		return autonomousBase;
	}

	@Override
	public void setup() {
		System.out.println("Robot is initialized");
	}
}
