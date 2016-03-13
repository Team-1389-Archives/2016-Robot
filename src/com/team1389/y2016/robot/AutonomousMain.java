package com.team1389.y2016.robot;

import java.util.ArrayList;
import java.util.List;

import org.strongback.command.Command;
import org.strongback.mock.MockVoltageSensor;

import com.team1389.base.auton.AutonMode;
import com.team1389.base.auton.AutonomousBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.control.SetASetpointCommand;
import com.team1389.y2016.robot.control.WaitUntilControllerWithinRangeCommand;

/**
 * This class defines which autonomous modes are available to be run. The first
 * in the list returned will be the default.
 */
public class AutonomousMain extends AutonomousBase {

	RobotLayout layout;

	public AutonomousMain(RobotLayout io) {
		this.layout = io;
		construct();
	}

	@Override
	public List<AutonMode> provideAutonModes() {
		ArrayList<AutonMode> modes = new ArrayList<AutonMode>();

		Command moveArmDown = CommandsUtil.combineSimultaneous(
				new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0), layout.subsystems.elevation);

		Command moveArmTo60 = CommandsUtil.combineSimultaneous(
				new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.15), layout.subsystems.elevation);
		// Command moveArmDown = new
		// SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0);
		// Command moveArmDown = Command.create(() -> {return true;});

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

		modes.add(new AutonMode() {

			@Override
			public String getName() {
				return "arm down drive forward";
			}

			@Override
			public Command getCommand() {
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -.03, .03),
						layout.subsystems.drive.driveDistanceCommand(7));
				return CommandsUtil.combineSimultaneous(moveArmDown, waitThenDrive);
			}
		});

		modes.add(new AutonMode() {

			@Override
			public String getName() {
				return "arm up drive forward";
			}

			@Override
			public Command getCommand() {
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, .12, .18),
						layout.subsystems.drive.driveDistanceCommand(9));
				return CommandsUtil.combineSimultaneous(moveArmTo60, waitThenDrive);
			}
		});
		
		modes.add(new AutonMode() {

			@Override
			public String getName() {
				return "arm down drive back";
			}

			@Override
			public Command getCommand() {
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -0.03, 0.03),
						layout.subsystems.drive.driveDistanceCommand(-7));
				return CommandsUtil.combineSimultaneous(moveArmDown, waitThenDrive);
			}
		});
		
		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "turn test";
			}
			
			@Override
			public Command getCommand() {
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -0.03, 0.03),
						layout.subsystems.drive.turnAmount(.5));
				return CommandsUtil.combineSimultaneous(moveArmDown, waitThenDrive);
			}
		});

		// add modes to mode list here

		return modes;
	}

	@Override
	protected void setup() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
		// uncomment for new setup
		// layout.subsystems.armSetpointProvider.setSetpoint(layout.io.armElevationMotor.getPosition());
		layout.subsystems.initAll();
	}
}
