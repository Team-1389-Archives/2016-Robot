package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;

import com.team1389.base.TeleopBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.SetpointProvider;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
import com.team1389.y2016.robot.commands.MonitorCommand;
import com.team1389.y2016.robot.commands.MonitorStrongbackTalonCommand;
import com.team1389.y2016.robot.control.ArmSetpointProvider;
import com.team1389.y2016.robot.control.FlywheelControl;
import com.team1389.y2016.robot.control.FlywheelControlRPM;
import com.team1389.y2016.robot.control.IntakeControlCommand;
import com.team1389.y2016.robot.control.LowGoalElevationControl;
import com.team1389.y2016.robot.control.TurntableControl;

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	ConfigurablePid pidC;
	DoubleConstant target;
	
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
		
		//this was breaking things
//		layout.io.armElevationMotor.setCurrentPositionAs(0);
//		Command drive = layout.subsystems.drive.teleopControl(layout.io.controllerDriver, 2.5);

		Command drive = new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver, 1.0);

//		Command intake = new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerManip.getAxis(3), 1.0);
		Command intake = new IntakeControlCommand(layout.io.intakeMotor, layout.io.controllerManip.getDPad(0));

//		Command flywheel = new JoystickMotorCommand(new TalonMotorWrapper(layout.io.flywheelMotorA),
//				layout.io.controllerManip.getAxis(1), 1.0);
		
//		Command flywheel = new FlywheelControl(layout.io.flywheelMotorA, layout.io.controllerManip);
		Command flywheel = CommandsUtil.combineSimultaneous(
				new FlywheelControlRPM(layout.subsystems.flywheelSetpointProvider, layout.io.controllerManip),
				layout.subsystems.flywheelFollowCommand
		);
		
//		SetpointProvider xAxis = new JoystickSetpointControlAriStyleWithReset(layout.io.controllerDriver.getAxis(3),
//				 layout.io.controllerDriver.getButton(1), -.3, .3, 0.003, 0);
//		
//		
//		Command yaw = new PositionControllerRampCommand(layout.io.turntableMotor, xAxis,
//				new PIDConstants(1, 0, 0, 0, 0), .3, -.3, .12);
				
		Command monitorFlywheel = new MonitorCommand(layout.io.flywheelMotorA, "flywheel speed");
		
		return flywheel;
		
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
