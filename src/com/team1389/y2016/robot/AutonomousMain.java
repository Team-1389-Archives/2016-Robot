package com.team1389.y2016.robot;

import java.util.ArrayList;
import java.util.List;

import org.strongback.command.Command;

import com.team1389.base.auton.AutonMode;
import com.team1389.base.auton.AutonomousBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.SetASetpointCommand;
import com.team1389.y2016.robot.control.WaitUntilControllerWithinRangeCommand;

/**
 * This class defines which autonomous modes are available to be run. The first
 * in the list returned will be the default.
 */
public class AutonomousMain extends AutonomousBase {

	RobotLayout layout;

	DoubleConstant autonForwardFirst;
	DoubleConstant autonTurn;
	DoubleConstant autonForwardSecond;

	public AutonomousMain(RobotLayout io) {
		this.layout = io;
		
		final double rotationsPerInch = 1.0 / 22.5;
		final double rotationsPerDegree = 1.0 / 360.0;
		
		autonForwardFirst = new DoubleConstant("auton forward first", 228.0 * rotationsPerInch);
		autonTurn = new DoubleConstant("auton turn", 60.0 * rotationsPerDegree);
		autonForwardSecond = new DoubleConstant("auton straight second", 122.0 * rotationsPerInch);
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
						layout.subsystems.drive.turnAmount(autonTurn.get()));
				return CommandsUtil.combineSimultaneous(moveArmDown, waitThenDrive);
			}
		});
		
		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "arm down, low bar, low goal";
			}
			
			@Override
			public Command getCommand() {
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -0.03, 0.03),
						layout.subsystems.drive.driveDistanceCommand(autonForwardFirst.get()),
						layout.subsystems.drive.turnAmount(autonTurn.get()),
						layout.subsystems.drive.driveDistanceCommand(autonForwardSecond.get()));
				return CommandsUtil.combineSimultaneous(moveArmDown, waitThenDrive);
			}
		});
		
		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "test forward 4";
			}
			
			@Override
			public Command getCommand() {
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -0.03, 0.03),
						layout.subsystems.drive.driveDistanceCommand(4));
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
