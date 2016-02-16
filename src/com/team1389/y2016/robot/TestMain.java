package com.team1389.y2016.robot;

import org.strongback.command.Command;

import com.team1389.base.TestBase;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.y2016.robot.commands.SpeedPIDTestCommand;

public class TestMain extends TestBase{
	
	RobotLayout layout;
	ConfigurablePid constants;
	
	public TestMain(RobotLayout layout) {
		this.layout = layout;
		
		constants = new ConfigurablePid("pidVals", new PIDConstants(0, 0, 0, 0, 0));
	}

	@Override
	public Command provideCommand() {
		return new SpeedPIDTestCommand(constants.get(), layout.io.leftDriveA, layout.io.controllerDriver);
	}

	@Override
	public void setupTest() {
	}

}
