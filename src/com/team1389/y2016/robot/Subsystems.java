package com.team1389.y2016.robot;

import org.strongback.command.Command;

import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.util.control.ConstantSpeedSetpointProvider;
import com.team1389.base.util.control.PositionControllerControlCommand;
import com.team1389.base.util.control.PositionControllerRampCommand;
import com.team1389.base.util.control.SetableSetpointProvider;
import com.team1389.base.util.control.TalonDriveControl;
import com.team1389.y2016.robot.subsystems.Drivetrain;

public class Subsystems {
	TalonDriveControl drive;
	Drivetrain drivetrain;
//	ArmControl arm;
	SetableSetpointProvider armSetpointProvider;
	Command elevation;
	IOLayout io;
	
	//flywheel
	Command flywheelFollowCommand;
	ConstantSpeedSetpointProvider flywheelSetpointProvider;
	ConfigurablePid flywheelPid;
	
	public Subsystems(IOLayout io) {
		flywheelPid = new ConfigurablePid("flywheel pid", new PIDConstants(.5, 0, 0, 0, 0));
		
		this.io = io;
		drive = new TalonDriveControl(io.leftDriveController, io.rightDriveController, RobotMap.maxAutonVelocity,
				RobotMap.maxAutonAcceleration, RobotMap.wheelRotationsPerTurn, RobotMap.drivePid.get());
		drivetrain = new Drivetrain(io.leftDriveA, io.rightDriveA);

		armSetpointProvider= new SetableSetpointProvider(false);
		
		
//		armSetpointProvider = new LowGoalElevationControl(io.controllerManip.getAxis(1));

		elevation = new PositionControllerRampCommand(io.armElevationMotor, 
				armSetpointProvider, new PIDConstants(.6, 0, 0, 0, 0), .26, 0, .2);//TODO: extract these numbers to RobotMap

		flywheelSetpointProvider = new ConstantSpeedSetpointProvider(0.0);
		flywheelFollowCommand = new PositionControllerControlCommand(flywheelSetpointProvider, io.flywheelFancy);
		flywheelFollowCommand = new PositionControllerRampCommand(io.flywheelFancy, flywheelSetpointProvider, flywheelPid.get());
		
		
		//calibrate arm
		io.armElevationMotor.setCurrentPositionAs(.25);
		
	}
	
	public void initAll(){
		io.armElevationMotor.setCurrentPositionAs(io.armElevationMotor.getPosition());
		io.armElevationMotor.disable();
		io.turntableMotor.setCurrentPositionAs(io.turntableMotor.getPosition());
		
		io.flywheelFancy.setPID(flywheelPid.get());
	}
}
