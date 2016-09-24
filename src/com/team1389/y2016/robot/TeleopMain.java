package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;

import com.team1389.base.TeleopBase;
//import com.team1389.base.control.AxisWithSafety;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.util.control.PositionControllerRampCommand;
import com.team1389.base.util.control.SetpointProvider;
import com.team1389.base.util.control.SpeedControllerSetCommand;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
import com.team1389.y2016.robot.commands.JoystickMotorCommand;
import com.team1389.y2016.robot.commands.JoystickPositionControl;
import com.team1389.y2016.robot.control.ArmSetpointProvider;
import com.team1389.y2016.robot.control.FlywheelControl;
import com.team1389.y2016.robot.control.FlywheelControlCommand;
import com.team1389.y2016.robot.control.IntakeControlCommand;
import com.team1389.y2016.robot.control.JoystickSetpointControlWithSafety;
import com.team1389.y2016.robot.control.LowGoalElevationControl;
import com.team1389.y2016.robot.control.TurntableControl;
import com.team1389.y2016.robot.control.WinchDeployControl;
import com.team1389.y2016.robot.test.MonitorDoubleCommand;

import edu.wpi.first.wpilibj.CANTalon;

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	ConfigurablePid pidC;
	
	public TeleopMain(RobotLayout layout) {
		this.layout = layout;
//		pidC = new ConfigurablePid("pid config", new PIDConstants(0, 0, 0, 0, 0));
//		target = new DoubleConstant("maxChange", 0.1);
	}

	@Override
	public void setupTeleop() {
		layout.io.gyro.reset();
		layout.io.imu.reset();
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
//		layout.subsystems.armSetpointProvider.setSetpoint(layout.io.armElevationMotor.getPosition());
		layout.subsystems.initAll();
	}

	@Override
	public  Command provideCommand() {
		
		SetpointProvider yAxis = new LowGoalElevationControl(layout.io.controllerManip.getAxis(1));

//		Command elevation = new PositionControllerRampCommand(layout.io.armElevationMotor, 
//				yAxis, new PIDConstants(.6, 0, 0, 0, 0), .24, 0, .12);
		
		//uncomment

		//uncomment for turntable

		Command elevationPidDo = layout.subsystems.elevation;
		Command elevationControl = new ArmSetpointProvider(layout.io.controllerManip, layout.subsystems.armSetpointProvider,layout.subsystems.turntable,layout.io.armElevationMotor);
		Command elevation = CommandsUtil.combineSimultaneous(elevationControl, elevationPidDo);
		
		Command turntable=layout.subsystems.turntable.teleopTurntable(layout.io.controllerManip);
		
		Command drive = new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver, 1.0);
		Command rightEncoder = new MonitorDoubleCommand<CANTalon>(layout.io.rightDriveA, "Right Encoder:", (CANTalon t) -> t.getPosition());
		Command leftEncoder = new MonitorDoubleCommand<CANTalon>(layout.io.rightDriveA, "Right Encoder:", (CANTalon t) -> t.getPosition());
		Command driveAndMeasure = CommandsUtil.combineSimultaneous(leftEncoder, rightEncoder, drive);
		
		Command climbPidDo=layout.subsystems.climber;
		Command climbControl = new JoystickPositionControl(layout.subsystems.climberSetpointProvider,layout.io.controllerManip.getAxis(5));
		Command climber = CommandsUtil.combineSimultaneous(climbPidDo,climbControl);
		
		Switch ballHolderIr = Switch.or(layout.io.ballHolderIR1, layout.io.ballHolderIR2);
		Command intake = new IntakeControlCommand(layout.io.intakeMotor, layout.io.controllerManip.getAxis(1),
				layout.io.controllerManip.getButton(9), ballHolderIr);
		
		Command flywheelBasic = new FlywheelControl(layout.io.flywheelMotorA, layout.io.controllerManip);
		
		Command flywheelSpeed = new SpeedControllerSetCommand(layout.subsystems.flywheelSpeedController, 0.0);
		Command flywheel = new FlywheelControlCommand(layout.io.controllerManip, (SpeedControllerSetCommand) flywheelSpeed);
		Command flywheelMonitor = new MonitorDoubleCommand<CANTalon>(layout.io.flywheelMotorA, "FlyWheel Encoder:", (CANTalon t) -> t.getSpeed());
		Command flywheelAll = CommandsUtil.combineSimultaneous(flywheel, flywheelSpeed, flywheelMonitor);
		
		//Command testIntake = new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerDriver.getAxis(0), 1.0);

		SetpointProvider xAxis = new JoystickSetpointControlWithSafety(layout.io.controllerManip.getAxis(4),
				 layout.io.controllerManip.getButton(10), -.3, .3, 0.003, 0);
		
		//uncomment for turntable
		Command yaw = new PositionControllerRampCommand(layout.io.turntableMotor, xAxis,
				new PIDConstants(1, 0, 0, 0, 0), .01, -.01, .02);
		Command vision=layout.subsystems.vision.autoAim(layout,layout.io.controllerManip);
		Command winchDeploy = new WinchDeployControl(
				Switch.and(layout.io.controllerDriver.getButton(8),layout.io.controllerManip.getButton(8)),
				layout.io.winchRelease);
		
		return CommandsUtil.combineSimultaneous(driveAndMeasure, intake, elevation, flywheelAll,turntable,vision);
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
