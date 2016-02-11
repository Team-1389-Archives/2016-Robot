package com.team1389.y2016.robot;

import com.team1389.y2016.robot.subsystems.Arm;
import com.team1389.y2016.robot.subsystems.BallManipulator;
import com.team1389.y2016.robot.subsystems.Drivetrain;

public class Subsystems {
	public final Drivetrain drivetrain;
	public final Arm arm;
	public final BallManipulator ballManipulator;
	
	public Subsystems(IOLayout io) {
		drivetrain = new Drivetrain(io.leftDriveA, io.rightDriveA);
		
		arm = new Arm(io.armElevationMotorA, io.turntableMotor);//armElevationMotorB will automatically follow armElevationMotorA
		
		ballManipulator = new BallManipulator(io.intakeMotor, io.flywheelMotorA);//flywheelMotorB automatically follower flywheelMotorA
	}
}
