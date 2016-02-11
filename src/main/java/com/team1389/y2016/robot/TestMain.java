package com.team1389.y2016.robot;

import org.strongback.command.Command;

import com.team1389.base.TestBase;
import com.team1389.y2016.robot.test.TestEncoders;

public class TestMain extends TestBase{
	
	RobotLayout layout;
	
	public TestMain(RobotLayout layout) {
		this.layout = layout;
	}

	@Override
	public Command provideCommand() {
		System.out.println("this rasens");
		return new TestEncoders(layout.io.turntableMotor);
	}

	@Override
	public void setupTest() {
	}

}
