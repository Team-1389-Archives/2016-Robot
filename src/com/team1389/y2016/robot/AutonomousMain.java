package com.team1389.y2016.robot;

import java.util.ArrayList;
import java.util.List;

import com.team1389.base.auton.AutonMode;
import com.team1389.base.auton.AutonomousBase;

/**
 * This class defines which autonomous modes are available to be run. The first in the
 * list returned will be the default.
 */
public class AutonomousMain extends AutonomousBase{
	
	RobotLayout layout;

	public AutonomousMain(RobotLayout io) {
		this.layout = io;
	}

	@Override
	public List<AutonMode> provideAutonModes(){
		ArrayList<AutonMode> modes = new ArrayList<AutonMode>();
		
		//add modes to mode list here
		
		return modes;
	}

	@Override
	protected void setup() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
	}
}
