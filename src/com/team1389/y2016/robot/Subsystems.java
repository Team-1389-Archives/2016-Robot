package com.team1389.y2016.robot;

import com.team1389.base.util.control.TalonDriveControl;
import com.team1389.y2016.robot.control.ArmControl;
import com.team1389.y2016.robot.subsystems.Drivetrain;

public class Subsystems {
	TalonDriveControl drive;
	Drivetrain drivetrain;
	ArmControl arm;
	
	public Subsystems(IOLayout io) {
		drive = new TalonDriveControl(io.leftDriveController, io.rightDriveController, RobotMap.maxAutonVelocity,
				RobotMap.maxAutonAcceleration, RobotMap.wheelRotationsPerTurn, RobotMap.drivePid.get());
		drivetrain = new Drivetrain(io.leftDriveA, io.rightDriveA);
		arm = new ArmControl(io.armElevationMotor, io.turntableMotor, RobotMap.armPid.get());
	}
}
