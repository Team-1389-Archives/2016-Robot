package com.team1389.y2016.robot;

import org.strongback.components.Motor;

import com.team1389.y2016.robot.subsystems.Arm;
import com.team1389.y2016.robot.subsystems.BallManipulator;
import com.team1389.y2016.robot.subsystems.Drivetrain;

public class Subsystems {
	public final Drivetrain drivetrain;
	public final Arm arm;
	public final BallManipulator ballManipulator;
	
	public Subsystems(IOLayout io) {
		Motor leftMotors = Motor.compose(io.leftDriveA, io.leftDriveB, io.leftDriveC);//NOTE: This wont work. Should set two of the Talons to follower mode.
		Motor rightMotors = Motor.compose(io.rightDriveA, io.rightDriveB, io.rightDriveC);
		drivetrain = new Drivetrain(leftMotors, rightMotors);
		
		arm = new Arm(io.armElevationMotorA, io.turntableMotor);
		
		ballManipulator = new BallManipulator(io.intakeMotor, io.flywheelMotorA);//flywheelMotorB automatically follower flywheelMotorA
	}
}
