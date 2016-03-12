package com.team1389.y2016.robot;

import java.util.ArrayList;
import java.util.List;

import org.strongback.command.Command;

import com.team1389.base.auton.AutonMode;
import com.team1389.base.auton.AutonomousBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.control.SetASetpointCommand;

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
		
		
//		Command moveArmDown = CommandsUtil.combineSimultaneous(
//				new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0),
//				layout.subsystems.elevation);
//		Command moveArmDown = new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0);
		Command moveArmDown = Command.create(() -> {return true;});
		
		System.out.println("layout:" + layout);
//		System.out.println("subsystems:" + layout.subsystems);
//		System.out.println("armSetpointProvider:" + layout.subsystems.armSetpointProvider);
		
		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "basic";
			}
			
			@Override
			public Command getCommand() {
				return moveArmDown;
			}
		});
		
		//add modes to mode list here
		
		return modes;
	}

	@Override
	protected void setup() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
		//uncomment for new setup
//		layout.subsystems.armSetpointProvider.setSetpoint(layout.io.armElevationMotor.getPosition());
		layout.subsystems.initArm();
	}
}
