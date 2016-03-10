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
import com.team1389.base.util.control.TalonDriveControl;
import com.team1389.base.util.testing.JoystickTalonCommand;
import com.team1389.base.util.testing.TalonMonitorCommand;
import com.team1389.base.wpiWrappers.TalonMotorWrapper;
import com.team1389.y2016.robot.commands.ButtonMotorCommand;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
import com.team1389.y2016.robot.commands.JoystickMotorCommand;
import com.team1389.y2016.robot.control.LowGoalElevationControl;

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	ConfigurablePid pidC;
	DoubleConstant target;
	
	public TeleopMain(RobotLayout layout) {
		this.layout = layout;
//		pidC = new ConfigurablePid("pid config", new PIDConstants(0, 0, 0, 0, 0));
//		target = new DoubleConstant("maxChange", 0.1);
	}

	@Override
	public void setupTeleop() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
	}

	@Override
	public  Command provideCommand() {
		
		layout.io.leftDriveController.setPID(RobotMap.drivePid.get());
		layout.io.rightDriveController.setPID(RobotMap.drivePid.get());
		
		SetpointProvider yAxis = new LowGoalElevationControl(layout.io.controllerManip.getAxis(1));

		Command elevation = new PositionControllerRampCommand(layout.io.armElevationMotor, 
				yAxis, new PIDConstants(.6, 0, 0, 0, 0), .24, 0, .12);
		
		layout.io.armElevationMotor.setCurrentPositionAs(0);
		Command drive = layout.subsystems.drive.teleopControl(layout.io.controllerDriver, 2);

		Command intake = new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerManip.getAxis(3), 1.0);

		Command flywheel = new JoystickMotorCommand(new TalonMotorWrapper(layout.io.flywheelMotorA),
				layout.io.controllerManip.getAxis(0), 1.0);
		
		return CommandsUtil.combineSimultaneous(drive, elevation, intake, flywheel);
		
//		return new JoystickTalonCommand(layout.io.simpleElevationA,layout.io.controllerManip.getAxis(1), 0.2);
//		return layout.subsystems.arm.getTeleopControlCommand(layout.io.controllerManip);
		
//		return layout.subsystems.drive.teleopControl(layout.io.controllerDriver, RobotMap.teleopDriveSpeed);
		
//		return new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver, 1.0);
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
		
//		InputDevice fake = layout.io.controllerFake;
		Command intake = new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerDriver.getAxis(0), 1.0);
//		Command flywheel = new JoystickMotorCommandAsButton(new TalonMotorWrapper(layout.io.flywheelMotorA), layout.io.controllerDriver.getAxis(2), 1.0);
		Command flywheel = new ButtonMotorCommand(new TalonMotorWrapper(layout.io.flywheelMotorA), layout.io.controllerDriver.getButton(2), false);
		
		return CommandsUtil.combineSimultaneous(elevation, drive, intake, flywheel);
		*/

		//monitors
//		Command monitorTurntable = new TalonMonitorCommand(layout.io.simpleTurntable, "turn");
//		Command monitorElevation = new TalonMonitorCommand(layout.io.simpleElevationA, "elev");
//		Command monitorLeft = new TalonMonitorCommand(layout.io.leftDriveA, "left");
//		Command monitorRight = new TalonMonitorCommand(layout.io.rightDriveA, "right");
//	
//		return CommandsUtil.combineSimultaneous(monitorLeft, monitorRight);

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
