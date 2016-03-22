package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;

import com.team1389.base.TeleopBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.util.control.PositionControllerRampCommand;
import com.team1389.base.util.control.SetpointProvider;
import com.team1389.base.util.control.SpeedControllerSetCommand;
import com.team1389.base.util.testing.TalonMonitorCommand;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
import com.team1389.y2016.robot.commands.JoystickMotorCommand;
import com.team1389.y2016.robot.control.ArmSetpointProvider;
import com.team1389.y2016.robot.control.FlywheelControl;
import com.team1389.y2016.robot.control.IntakeControlCommand;
import com.team1389.y2016.robot.control.LowGoalElevationControl;
import com.team1389.y2016.robot.control.TurntableControl;

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	ConfigurablePid pidC;
	DoubleConstant flySpeed = new DoubleConstant("flywheel speed", 10.0);
	
	public TeleopMain(RobotLayout layout) {
		this.layout = layout;
		System.out.println("layout in teleop:" + layout);
//		pidC = new ConfigurablePid("pid config", new PIDConstants(0, 0, 0, 0, 0));
//		target = new DoubleConstant("maxChange", 0.1);
	}

	@Override
	public void setupTeleop() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
//		layout.subsystems.armSetpointProvider.setSetpoint(layout.io.armElevationMotor.getPosition());
		layout.subsystems.initAll();
	}

	@Override
	public  Command provideCommand() {
		
		layout.io.leftDriveController.setPID(RobotMap.drivePid.get());
		layout.io.rightDriveController.setPID(RobotMap.drivePid.get());
		
		SetpointProvider yAxis = new LowGoalElevationControl(layout.io.controllerManip.getAxis(1));

//		Command elevation = new PositionControllerRampCommand(layout.io.armElevationMotor, 
//				yAxis, new PIDConstants(.6, 0, 0, 0, 0), .24, 0, .12);
		
		//uncomment
		Command elevationPidDo = layout.subsystems.elevation;
		Command elevationControl = new ArmSetpointProvider(layout.io.controllerManip, layout.subsystems.armSetpointProvider);
		Command elevation = CommandsUtil.combineSimultaneous(elevationControl, elevationPidDo);
		Command turntable = new TurntableControl(layout.io.controllerManip, layout.io.simpleTurntable);

		Command drive = new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver, 1.0);

		Command intake = new IntakeControlCommand(layout.io.intakeMotor, layout.io.controllerManip.getDPad(0));
		
		Command flywheelBasic = new FlywheelControl(layout.io.flywheelMotorA, layout.io.controllerManip);
//		Command flywheel = CommandsUtil.combineSimultaneous(
//				new FlywheelControlRPS(layout.subsystems.flywheelSetpointProvider, layout.io.controllerManip),
//				layout.subsystems.flywheelFollowCommand
//		);
		
		Command flywheel = new SpeedControllerSetCommand(layout.subsystems.flywheelSpeedController, flySpeed.get());
		
//		SetpointProvider xAxis = new JoystickSetpointControlAriStyleWithReset(layout.io.controllerDriver.getAxis(3),
//				 layout.io.controllerDriver.getButton(1), -.3, .3, 0.003, 0);
//		
//		
//		Command yaw = new PositionControllerRampCommand(layout.io.turntableMotor, xAxis,
//				new PIDConstants(1, 0, 0, 0, 0), .3, -.3, .12);
				
		Command monitorFlywheel = new TalonMonitorCommand(layout.io.flywheelMotorA, "flywheel speed");
		
		Command testIntake = new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerDriver.getAxis(0), 1.0);
//		return CommandsUtil.combineSimultaneous(testIntake, flywheelBasic, monitorFlywheel);
		
//		return CommandsUtil.combineSimultaneous(flywheel, monitorFlywheel, testIntake);
		
		Command monitorTurntable = new TalonMonitorCommand(layout.io.simpleTurntable, "turntable");

		SetpointProvider xAxis = new JoystickSetpointControlAriStyleWithReset(layout.io.controllerManip.getAxis(0),
				 layout.io.controllerManip.getButton(1), -.3, .3, 0.003, 0);
		
//		xAxis = new SetpointProvider() {
//			
//			@Override
//			public double getSetpoint() {
//				return 0;
//			}
//		};

		Command yaw = new PositionControllerRampCommand(layout.io.turntableMotor, xAxis,
				new PIDConstants(1, 0, 0, 0, 0), .3, -.3, .12);
		
		return CommandsUtil.combineSimultaneous(drive, yaw, monitorTurntable, elevation, intake);
		
//		return CommandsUtil.combineSimultaneous(drive, elevation, intake, flywheel, turntable, monitorFlywheel);
		
//		return monitorFlywheel;
		
		/*
		SetpointProvider xAxis = new JoystickSetpointControlAriStyleWithReset(layout.io.controllerDriver.getAxis(3),
				 layout.io.controllerDriver.getButton(1), -.3, .3, 0.003, 0);
//		SetpointProvider yAxis = new LowGoalElevationControl(layout.io.controllerDriver.getAxis(1));
		SetpointProvider yAxis = new JoystickSetpointControlAriStyleWithReset(layout.io.controllerDriver.getAxis(1),
				layout.io.controllerDriver.getButton(7), 0.0, 0.24, 0.004, 0);
		
		Command elevation = new PositionControllerRampCommand(layout.io.armElevationMotor, 
				yAxis, new PIDConstants(.6, 0, 0, 0, 0), .24, 0, .12);
		
		Command yaw = new PositionControllerRampCommand(layout.io.turntableMotor, xAxis,
				new PIDConstants(1, 0, 0, 0, 0), .3, -.3, .12);
		
		Command drive = new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerManip, 1.0);
		
		Command flywheel = new ButtonMotorCommand(new TalonMotorWrapper(layout.io.flywheelMotorA), layout.io.controllerDriver.getButton(2), false);
		
		return CommandsUtil.combineSimultaneous(elevation, drive, intake, flywheel);
		*/
	}
	
	private SetpointProvider joystickSetpointProvider(ContinuousRange joystickAxis, double max, double min){
		return new SetpointProvider(){

			@Override
			public double getSetpoint() {
				double joy = joystickAxis.read();
				if (joy > 0){
					joy *= max;
				} else {
					joy *= min;
				}
				return joy;
			}
			
		};
	}
}
