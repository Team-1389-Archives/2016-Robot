package com.team1389.y2016.robot;

import java.util.ArrayList;
import java.util.List;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.hardware.Hardware;

import com.team1389.base.auton.AutonMode;
import com.team1389.base.auton.AutonomousBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.SetASetpointCommand;
import com.team1389.base.util.control.SpeedControlSetCommandSetCommand;
import com.team1389.base.util.control.SpeedControllerSetCommand;
import com.team1389.base.util.control.WaitTimeCommand;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;
import com.team1389.base.wpiWrappers.TalonSRXSpeedHardware;
import com.team1389.y2016.robot.control.SetMotorCommand;
import com.team1389.y2016.robot.control.WaitUntilControllerWithinRangeCommand;
import com.team1389.y2016.robot.control.WaitUntilSpeedControllerWithinRange;

/**
 * This class defines which autonomous modes are available to be run. The first
 * in the list returned will be the default.
 */
public class AutonomousMain extends AutonomousBase {

	RobotLayout layout;

	DoubleConstant autonForwardFirst;
	DoubleConstant autonTurn;
	DoubleConstant autonForwardSecond;
	
	public static final double rotationsPerInch = 1.0 / 22.5;
	public static final double rotationsPerDegree = 1.0 / 360.0;
	// Should we move these to RobotMap?

	public AutonomousMain(RobotLayout io) {
		this.layout = io;

		final double rotationsPerInch = 1.0 / 22.5;
		final double rotationsPerDegree = 1.0 / 360.0;

		autonForwardFirst = new DoubleConstant("auton forward first", -9.2);
		autonTurn = new DoubleConstant("auton turn", .159);
		autonForwardSecond = new DoubleConstant("auton straight second", -3.5);
		construct();
	}

	@Override
	public List<AutonMode> provideAutonModes() {
		ArrayList<AutonMode> modes = new ArrayList<AutonMode>();

		Command moveArmDown = CommandsUtil.combineSimultaneous(
				new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0), layout.subsystems.elevation);

		Command moveArmTo60 = CommandsUtil.combineSimultaneous(
				new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.15), layout.subsystems.elevation);
		
		Command waitUntilDownIsh = new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -0.3, .3);
		
		Command armWaitThenDown = CommandsUtil.combineSequential(
				new WaitTimeCommand(4),
				new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0));
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
				return "ball denial";
			}

			@Override
			public Command getCommand() {
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, .12, .18),
						layout.subsystems.drive.driveDistanceCommand(7),
						new SetMotorCommand(layout.io.intakeMotor, -1.0),
						new WaitTimeCommand(2),
						new SetMotorCommand(layout.io.intakeMotor, 0.0),
						layout.subsystems.drive.turnAmount(.5),
						layout.subsystems.drive.driveDistanceCommand(5.5)
						);
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
//						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -0.03, 0.03),
						CommandsUtil.combineSimultaneous(
								armWaitThenDown,
								CommandsUtil.combineSequential(
										layout.subsystems.drive.driveDistanceCommand(-7))));
				return CommandsUtil.combineSimultaneous(layout.subsystems.elevation, waitThenDrive);
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
		
		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "low bar back turn forward high goal";
			}
			
			@Override
			public Command getCommand() {
				SpeedControllerSetCommand flywheelSpeedControl =
						new SpeedControllerSetCommand(layout.subsystems.flywheelSpeedController, 0.0);
				Command waitThenDrive = CommandsUtil.combineSequential(
						new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0),
								CommandsUtil.combineSequential(
										CommandsUtil.combineSimultaneous(
												layout.subsystems.drive.driveDistanceCommand(autonForwardFirst.get()),
												CommandsUtil.combineSequential(
														new WaitTimeCommand(layout.subsystems.drive.timeForDistance(autonForwardFirst.get()) / 2.0 - 0.5),
														new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.25))),
										layout.subsystems.drive.turnAmount(autonTurn.get()),
										Command.create(() -> {System.out.println("arm set to zero");}),
										new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0),
										layout.subsystems.drive.driveDistanceCommand(autonForwardSecond.get()),
										new SetASetpointCommand(layout.subsystems.armSetpointProvider, RobotMap.highGoalPoint.get()),
										waitTillSetpoint(layout.io.armElevationMotor, RobotMap.highGoalPoint.get()),
										new SpeedControlSetCommandSetCommand(flywheelSpeedControl, RobotMap.flySpeed.get()),
										waitTillSpeedSetpoint(layout.subsystems.flywheelSpeedController, RobotMap.flySpeed.get()),
										Command.create(() -> {System.out.println("on to intake");}),
										new SetMotorCommand(layout.io.intakeMotor, 1.0),
										new WaitTimeCommand(2),
										new SetMotorCommand(layout.io.intakeMotor, 0.0),
										new SpeedControlSetCommandSetCommand(flywheelSpeedControl, 0.0)
						));
				return CommandsUtil.combineSimultaneous(layout.subsystems.elevation, flywheelSpeedControl, waitThenDrive);
			}
		});

		// add modes to mode list here

		return modes;
	}

	private Command waitTillSetpoint(TalonSRXPositionHardware controller, double point) {
		return new WaitUntilControllerWithinRangeCommand(controller, point - 0.06, point + 0.06);
	}

	private Command waitTillSpeedSetpoint(TalonSRXSpeedHardware controller, double point) {
		double absPoint = Math.abs(point);
		return new WaitUntilSpeedControllerWithinRange(controller, absPoint - (absPoint * .10),
				absPoint + (absPoint * .10));
	}

	@Override
	protected void setup() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
		// uncomment for new setup
		// layout.subsystems.armSetpointProvider.setSetpoint(layout.io.armElevationMotor.getPosition());
		layout.subsystems.initAll();
	}
}
