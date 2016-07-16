package com.team1389.y2016.robot;

import java.util.ArrayList;
import java.util.List;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.hardware.Hardware;

import com.team1389.base.auton.AutonMode;
import com.team1389.base.auton.AutonomousBase;
import com.team1389.base.control.IMUAnglePIDSource;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.SetASetpointCommand;
import com.team1389.base.util.control.SetableSetpointProvider;
import com.team1389.base.util.control.SpeedControlSetCommandSetCommand;
import com.team1389.base.util.control.SpeedControllerSetCommand;
import com.team1389.base.util.control.TalonDriveControl;
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
	DoubleConstant autonArcMod;
	DoubleConstant chevalDisance;
	
	public static final double rotationsPerInch = 1.0 / 22.5;
	public static final double rotationsPerDegree = 1.0 / 360.0;
	// Should we move these to RobotMap?

	public AutonomousMain(RobotLayout io) {
		this.layout = io;

		final double rotationsPerInch = 1.0 / 22.5;
		final double rotationsPerDegree = 1.0 / 360.0;

		autonForwardFirst = new DoubleConstant("auton forward first", -8.66);
		autonTurn = new DoubleConstant("auton turn", 15d);
		autonForwardSecond = new DoubleConstant("auton straight second", -3.85);
		autonArcMod = new DoubleConstant("auton arc mod", 1.0);
		chevalDisance = new DoubleConstant("cheval", 1.95);
		construct();
//		setSelectedAuton("ball denial");
//		setSelectedAuton("arm down drive forward");
//		setSelectedAuton("arm down drive forward");
		setSelectedAuton("vision shot");
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
		modes.add(new AutonMode(){

			@Override
			public Command getCommand() {
				return CommandsUtil.combineSimultaneous(
					layout.subsystems.vision.lightsOn(),
					Command.create(()->{
						layout.subsystems.vision.getCorrectionAngle();
						System.out.println("checking...");
						return false;
					}));
			}

			@Override
			public String getName() {
				return "vision shot";
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
				return null;
			//	return layout.subsystems.drive.turnWithPidSource(new IMUAnglePIDSource(layout.io.imu), autonTurn.get(),RobotMap.imuTurnPid.get());
			}
			
		});

		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "testArc";
			}
			
			@Override
			public Command getCommand() {
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -0.03, 0.03),
						layout.subsystems.drive.driveArcCommand(3, 5));
				return CommandsUtil.combineSimultaneous(moveArmDown, waitThenDrive);
			}
		});
		
		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				//TODO broken
				return "arm down, low bar, low goal,back to neutral";
			}
			
			@Override
			public Command getCommand() {
				final double speedMod = 1.7;
				final double accMod = 1.4;
				Command waitThenDrive = CommandsUtil.combineSequential(
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -0.03, 0.03),
						//CommandsUtil.combineSequential(
//								CommandsUtil.combineSimultaneous(
//										layout.subsystems.drive.driveDistanceCommand(-autonForwardFirst.get(),speedMod,accMod),
//										CommandsUtil.combineSequential(
//												new WaitTimeCommand(layout.subsystems.drive.timeForDistance(autonForwardFirst.get()/ 2,speedMod,accMod) ),
//												new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.25))),
						layout.subsystems.drive.driveDistanceCommand(-autonForwardFirst.get(),speedMod,accMod),
						//layout.subsystems.drive.turnWithPidSource(layout.io.imu, autonTurn.get(), RobotMap.imuTurnPid.get()),
						layout.subsystems.drive.driveDistanceCommand(-autonForwardSecond.get(), speedMod, accMod),
						layout.subsystems.drive.driveDistanceCommand(1.5, 0.4, 1.0),
//						new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0),
//						Command.create(() -> {System.out.println("set it");return true;}),
//					//	new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -.1, .01),
//						Command.create(() -> {System.out.println("finished driving and commanded arm to move down");return true;}),
//						new WaitTimeCommand(1.15),
						new SetMotorCommand(layout.io.intakeMotor, -1.0),
						new WaitTimeCommand(1),
						new SetMotorCommand(layout.io.intakeMotor, 0),
						layout.subsystems.drive.driveDistanceCommand(autonForwardSecond.get()-1.8, speedMod, accMod),
						layout.subsystems.drive.turnAmount(-autonTurn.get()),
						layout.subsystems.drive.driveDistanceCommand(autonForwardFirst.get(), speedMod, accMod)
				/*)*/);
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
												layout.subsystems.drive.driveArcCommand(autonForwardFirst.get() * autonArcMod.get(), autonForwardFirst.get()),
												CommandsUtil.combineSequential(
														new WaitTimeCommand(layout.subsystems.drive.timeForDistance(autonForwardFirst.get()) / 2.0 - 0.2),
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
												layout.subsystems.drive.driveArcCommand(autonForwardFirst.get() * autonArcMod.get(), autonForwardFirst.get()),
												CommandsUtil.combineSequential(
														new WaitTimeCommand(layout.subsystems.drive.timeForDistance(autonForwardFirst.get()) / 2.0 - 0.2),
														new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.25))),
										layout.subsystems.drive.turnAmount(autonTurn.get()),
										Command.create(() -> {System.out.println("arm set to zero");}),
										makeShootCommand(flywheelSpeedControl)
						));
				return CommandsUtil.combineSimultaneous(layout.subsystems.elevation, flywheelSpeedControl, waitThenDrive);
			}
		});
		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "high goal motion but don't shoot";
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
										layout.subsystems.drive.driveDistanceCommand(autonForwardSecond.get()),
										layout.subsystems.drive.turnAmount(.5),
										new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0),
										layout.subsystems.drive.driveDistanceCommand(2),
										new SetMotorCommand(layout.io.intakeMotor, -1.0)
						));
				return CommandsUtil.combineSimultaneous(layout.subsystems.elevation, flywheelSpeedControl, waitThenDrive);
			}
		});

		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "high goal rock wall center";
			}
			
			@Override
			public Command getCommand() {
				SpeedControllerSetCommand flywheelSpeedControl =
						new SpeedControllerSetCommand(layout.subsystems.flywheelSpeedController, 0.0);
				Command waitThenDrive = CommandsUtil.combineSequential(
						layout.subsystems.drive.driveDistanceCommand(4),
						layout.subsystems.drive.turnAmount(-.25),
						layout.subsystems.drive.driveDistanceCommand(.5),
						layout.subsystems.drive.turnAmount(.25),
						shootHighFromCenter(flywheelSpeedControl, layout.subsystems.drive, layout.subsystems.armSetpointProvider)
						);
				return CommandsUtil.combineSimultaneous(layout.subsystems.elevation, flywheelSpeedControl, waitThenDrive);
			}
		});
		
		modes.add(new AutonMode() {
			
			@Override
			public String getName() {
				return "cheval de friasdf";
			}
			
			@Override
			public Command getCommand() {

				Command waitThenDrive = CommandsUtil.combineSequential(
						new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.2),
//						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, .15, .25),
						layout.subsystems.drive.driveDistanceCommand(chevalDisance.get()),
						new SetASetpointCommand(layout.subsystems.armSetpointProvider, 0.0),
						new WaitUntilControllerWithinRangeCommand(layout.io.armElevationMotor, -.3, .3),
						layout.subsystems.drive.driveDistanceCommand(3));
				return CommandsUtil.combineSimultaneous(layout.subsystems.elevation, waitThenDrive);
			}
		});


		// add modes to mode list here

		return modes;
	}
	
	private Command shootHighFromCenter(SpeedControllerSetCommand flywheel, TalonDriveControl drive, SetableSetpointProvider armSetpoinpt){
		return CommandsUtil.combineSequential(
				drive.driveDistanceCommand(6),
				new SetASetpointCommand(layout.subsystems.armSetpointProvider, RobotMap.highGoalPoint.get()),
				waitTillSetpoint(layout.io.armElevationMotor, RobotMap.highGoalPoint.get()),
				new SpeedControlSetCommandSetCommand(flywheel, RobotMap.flySpeed.get()),
				waitTillSpeedSetpoint(layout.subsystems.flywheelSpeedController, RobotMap.flySpeed.get()),
				Command.create(() -> {System.out.println("on to intake");}),
				new SetMotorCommand(layout.io.intakeMotor, 1.0),
				new WaitTimeCommand(2),
				new SetMotorCommand(layout.io.intakeMotor, 0.0),
				new SpeedControlSetCommandSetCommand(flywheel, 0.0)
				);
	}

	private Command waitTillSetpoint(TalonSRXPositionHardware controller, double point) {
		return new WaitUntilControllerWithinRangeCommand(controller, point - 0.08, point + 0.06);
	}

	private Command waitTillSpeedSetpoint(TalonSRXSpeedHardware controller, double point) {
		double absPoint = Math.abs(point);
		return new WaitUntilSpeedControllerWithinRange(controller, absPoint - (absPoint * .10),
				absPoint + (absPoint * .10));
	}
	
	private Command makeShootCommand(SpeedControllerSetCommand flywheelSpeedControl){
		Command shoot = CommandsUtil.combineSequential(
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
		);
		return shoot;
	}

	@Override
	protected void setup() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
		// uncomment for new setup
		// layout.subsystems.armSetpointProvider.setSetpoint(layout.io.armElevationMotor.getPosition());
		layout.subsystems.initAll();
	}
}
