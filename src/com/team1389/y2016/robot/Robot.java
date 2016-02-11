package com.team1389.y2016.robot;

import com.team1389.base.RobotCode;
import com.team1389.base.TeleopBase;
import com.team1389.base.TestBase;
import com.team1389.base.auton.AutonomousBase;

/**
 * This class defines where the teleop and auton bases are.
 * The code in this file wony usually have to be changed.
 */
public class Robot implements RobotCode{
	TeleopMain teleopBase;
	AutonomousMain autonomousBase;
	TestMain testMain;
	RobotLayout layout;
	
	public Robot(RobotLayout layout) {
		teleopBase = new TeleopMain(layout);
		autonomousBase = new AutonomousMain(layout);
		testMain = new TestMain(layout);
		this.layout = layout;
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

	@Override
	public TestBase getTestBase() {
		return testMain;
	}
}
