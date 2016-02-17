package com.team1389.y2016.robot;

import org.strongback.command.Command;

import com.team1389.base.TestBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.y2016.robot.commands.SpeedPIDTestCommand;

public class TestMain extends TestBase{
	//DANGER: Talons don't work properly in test mode
	
	RobotLayout layout;
	
	ConfigurablePid constants;
	
	public TestMain(RobotLayout layout) {
		this.layout = layout;
		
	}

	@Override
	public Command provideCommand() {
		return null;
	}

	@Override
	public void setupTest() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
	}

}
