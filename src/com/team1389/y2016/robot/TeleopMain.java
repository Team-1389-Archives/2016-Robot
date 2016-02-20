package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;

import com.team1389.base.TeleopBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.wpiWrappers.TalonMotorWrapper;
import com.team1389.y2016.robot.commands.ButtonMotorCommand;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
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
//		return new SpeedPIDTestCommand(pidC.get(), layout.io.leftDriveA, layout.io.controllerDriver, targetSpeed.get());
//		return new TestMotionProfileCommand();
//		return new PIDTestCommand(pidC.get(), layout.io.leftDriveA, layout.io.controllerDriver, target.get(), false, RobotMap.leftEncoderTicksPerRotation);
//		return CommandsUtil.combineSimultaneous(new JoystickMotorCommand(motor, layout.io.controllerDriver.getAxis(1), .5),
//				new JoystickMotorCommand(new TalonMotorWrapper(layout.io.flywheelMotorA), layout.io.controllerDriver.getAxis(2), 1.0));
//		return new PIDTestCommand(pidC.get(), layout.io.simpleElevationA , layout.io.controllerDriver, target.get(), false, RobotMap.armElevationTicksPerRotation, false);
//		return new MonitorCommand(layout.io.simpleElevationA, "elev");
		
		SetpointProvider yAxis = new JoystickSetpointControlAriStyle(layout.io.controllerDriver.getAxis(1), 0, .2, 0.001, 0);
		SetpointProvider xAxis = new JoystickSetpointControlAriStyle(layout.io.controllerDriver.getAxis(3), 0, .2, 0.001, 0);
		
		Command elevation = new PositionControllerRampCommand(layout.io.armElevationMotor, 
				yAxis, new PIDConstants(1, 0, 0, 0, 0), .2, 0, .1);
		
//		Command yaw = new PositionControllerRampCommand(layout.io.turntableMotor, xAxis,
//				pidC.get(), .5, -.5, .1);
		
		Command intake = new ButtonMotorCommand(layout.io.intakeMotor, layout.io.controllerManip.getButton(0), RobotMap.intakeMotor_isInverted);
		Command flyWheel = new ButtonMotorCommand(new TalonMotorWrapper(layout.io.flywheelMotorA), layout.io.controllerManip.getButton(1), RobotMap.flywheelMotorA_isInverted);
		Command drive = new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver, 0.5);
		
		return CommandsUtil.combineSimultaneous(elevation,intake, drive, flyWheel);
//		return new MonitorCommand(layout.io.simpleTurntable, "turntable");
		


	}
	
	private SetpointProvider joystickSetpointProvider(JoystickSetpointControlAriStyle joystickSetpointControlAriStyle,
			double max, int min) {
		// TODO Auto-generated method stub
		return null;
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
