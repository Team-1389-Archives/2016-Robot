package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.TeleopBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.wpiWrappers.TalonMotorWrapper;
import com.team1389.y2016.robot.commands.ButtonMotorCommand;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
import com.team1389.y2016.robot.commands.JoystickMotorCommand;
import com.team1389.y2016.robot.commands.MonitorCommand;
import com.team1389.y2016.robot.commands.TwoButtonMotor;
import com.team1389.y2016.robot.control.LowGoalElevationControl;
import com.team1389.y2016.robot.test.GraphVoltageCommand;
import com.team1389.y2016.robot.test.PositionControllerRampCommand;
import com.team1389.y2016.robot.test.PositionControllerRampCommand.SetpointProvider;

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	ConfigurablePid pidC;
	DoubleConstant target;
	
	public TeleopMain(RobotLayout layout) {
		this.layout = layout;
		pidC = new ConfigurablePid("pid config", new PIDConstants(0, 0, 0, 0, 0));
		target = new DoubleConstant("maxChange", 0.1);
	}

	@Override
	public void setupTeleop() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
	}

	@Override
	public  Command provideCommand() {
//		SetpointProvider yAxis = new JoystickSetpointControlAriStyle(layout.io.controllerDriver.getAxis(1), 0, .24, 0.003, 0);
		SetpointProvider xAxis = new JoystickSetpointControlAriStyleWithReset(layout.io.controllerDriver.getAxis(3),
				layout.io.controllerDriver.getButton(0), -.3, .3, 0.003, 0);
		SetpointProvider yAxis = new LowGoalElevationControl(layout.io.controllerDriver.getAxis(1));
		
		Command elevation = new PositionControllerRampCommand(layout.io.armElevationMotor, 
				yAxis, new PIDConstants(.7, 0, 0, 0, 0), .24, 0, .12);
		
		Command yaw = new PositionControllerRampCommand(layout.io.turntableMotor, xAxis,
				new PIDConstants(1, 0, 0, 0, 0), .3, -.3, .12);
		
		Command drive = new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerManip, 1.0);
		
		InputDevice fake = layout.io.controllerFake;
		Command intake = new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerDriver.getAxis(0), 1.0);
//		Command flywheel = new JoystickMotorCommand(new TalonMotorWrapper(layout.io.flywheelMotorA), layout.io.controllerDriver.getAxis(2), 1.0);
		
		
		return CommandsUtil.combineSimultaneous(elevation, yaw, drive, intake);
//		return new MonitorCommand(layout.io.simpleElevationA, "elev");
//		return new MonitorCommand(layout.io.simpleElevationA, "elev");


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
