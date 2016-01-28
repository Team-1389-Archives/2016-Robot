package com.team1389.y2016.robot;

import org.strongback.components.Motor;

import com.team1389.base.wpiWrappers.PositionController;
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
		
		PositionController elevationMotors =
				PositionController.compose(io.armElevationMotorA, io.armElevationMotorB);
		arm = new Arm(elevationMotors, io.turntableMotor);
		
		Motor flywheels = Motor.compose(io.flywheelMotorA, io.flywheelMotorB);
		ballManipulator = new BallManipulator(io.intakeMotor, flywheels);
	}
}
