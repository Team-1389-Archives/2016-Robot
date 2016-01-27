package com.team1389.y2016.robot;

import com.team1389.base.IO;
import com.team1389.base.RobotCode;
import com.team1389.base.Simulator;
import com.team1389.base.Team1389RobotBase;
import com.team1389.base.TeleopBase;
import com.team1389.base.auton.AutonomousBase;

/**
 * This class defines where the teleop and auton bases are.
 * The code in this file wony usually have to be changed.
 */
public class Robot implements RobotCode<IOLayout>{
	TeleopMain teleopBase;
	AutonomousMain autonomousBase;
	IOLayout io;
	
	public Robot(IOLayout io) {
		teleopBase = new TeleopMain();
		autonomousBase = new AutonomousMain(io);
		this.io = io;
	}

	public TeleopBase<IOLayout> getTeleopBase() {
		return teleopBase;
	}

	public AutonomousBase<IOLayout> getAutonomousBase() {
		return autonomousBase;
	}

	@Override
	public void setup() {
		System.out.println("Robot is initialized");
	}

	@Override
	public IOLayout getIO() {
		return io;
	}
}
