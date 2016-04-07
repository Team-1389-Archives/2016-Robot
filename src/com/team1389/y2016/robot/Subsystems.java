package com.team1389.y2016.robot;

import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.util.control.PositionControllerRampCommand;
import com.team1389.base.util.control.SetableSetpointProvider;
import com.team1389.base.util.control.TalonDriveControl;
import com.team1389.base.wpiWrappers.TalonSRXSpeedHardware;
import com.team1389.y2016.robot.subsystems.Drivetrain;

public class Subsystems {
	TalonDriveControl drive;
	Drivetrain drivetrain;
//	ArmControl arm;
	final SetableSetpointProvider armSetpointProvider;
	PositionControllerRampCommand elevation;
	IOLayout io;
	ConfigurablePid armPid;
	
	//flywheel
	TalonSRXSpeedHardware flywheelSpeedController;
	ConfigurablePid flywheelPid;
	
	
	
	public Subsystems(IOLayout io) {
		flywheelPid = new ConfigurablePid("flywheel pid", new PIDConstants(.501, 0, 0, 0, 0));
		armPid = new ConfigurablePid("arm pid", new PIDConstants(.8, 0, 0, 0, 0));
		
		this.io = io;
		drive = new TalonDriveControl(io.leftDriveController, io.rightDriveController, RobotMap.maxAutonVelocity,
				RobotMap.maxAutonAcceleration, RobotMap.wheelRotationsPerTurn, RobotMap.driveForwardPid.get(), RobotMap.driveTurnPid.get());
		drivetrain = new Drivetrain(io.leftDriveA, io.rightDriveA);

		armSetpointProvider= new SetableSetpointProvider(false);
		
		
//		armSetpointProvider = new LowGoalElevationControl(io.controllerManip.getAxis(1));

		elevation = new PositionControllerRampCommand(io.armElevationMotor, 
				armSetpointProvider, armPid.get(), .26, 0, .2);//TODO: extract these numbers to RobotMap
		
		//flywheel
		flywheelSpeedController = new TalonSRXSpeedHardware(io.flywheelMotorA);

		
		//calibrate arm
		io.armElevationMotor.setCurrentPositionAs(.25);
		
	}
	
	public void initAll(){
		io.armElevationMotor.setCurrentPositionAs(io.armElevationMotor.getPosition());
		io.armElevationMotor.disable();
		//uncomment for turntable
//		io.turntableMotor.setCurrentPositionAs(io.turntableMotor.getPosition());
		
		System.out.println("setting flywheel pid in Subsystems");
		io.flywheelFancy.setPID(flywheelPid.get());
		
		elevation.setPID(armPid.get());
		
		drive.setPids(RobotMap.driveForwardPid.get(), RobotMap.driveTurnPid.get());
	}
}
