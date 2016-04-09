package com.team1389.y2016.robot;

import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Outputs:
	//Drivetrain
	public static final int leftMotorA_CAN = 4; public static final boolean leftMotorA_isInverted = true;
	public static final int leftMotorB_CAN = 8; public static final boolean leftMotorB_isInverted = true;
	public static final int leftMotorC_CAN = 7; public static final boolean leftMotorC_isInverted = true;
	public static final int rightMotorA_CAN = 2; public static final boolean rightMotorA_isInverted = false;
	public static final int rightMotorB_CAN = 1; public static final boolean rightMotorB_isInverted = false;
	public static final int rightMotorC_CAN = 3; public static final boolean rightMotorC_isInverted = false;
	//Arm
	public static final int turntableMotor_CAN = 9; public static final boolean turntableMotor_isInverted = false;
	public static final int elevatorMotorA_CAN = 12; public static final boolean elevatorMotorA_isInverted = true;
	public static final int elevatorMotorB_CAN = 6; public static final boolean elevatorMotorB_isInverted = true;
	//Ball Manipulator
	public static final int intakeMotor_CAN = 11; public static final boolean intakeMotor_isInverted = false;
	//DONT COMMIT THIS
	public static final int flywheelMotorA_CAN = 5; public static final boolean flywheelMotorA_isInverted = false;
	public static final int flywheelMotorB_CAN = 10; public static final boolean flywheelMotorB_isInverted = true;
	
	//Inputs:
	public static final double encoderTicksPerRotation = 4096;

	public static final boolean leftEncoderInverted = true;
	public static final boolean rightEncoderInverted = false;
	public static final boolean elevatorEncoderInverted = true;
	public static final boolean turntableEncoderInverted = false;
	public static final boolean flywheelEncoderInverted = false;
	
	//Ball Manipulator
	public static final int ballHolderIR1_DIO = 0; public static final boolean ballHolderIR1_isInverted = false;
	public static final int ballHolderIR2_DIO = 1; public static final boolean ballHolderIR2_isInverted = false;
	
	//Misc
	public static final int driveJoystickPort = 0;
	public static final int manipJoystickPort = 1;
	public static final double referenceArmElevationAngle = 0;//the angle that the arm is completely down
	
	public static final double wheelTicksPerRotation = encoderTicksPerRotation * .75;
	public static final double rightEncoderSpeedMod = encoderTicksPerRotation * .75;
	public static final double armElevationTicksPerRotation = encoderTicksPerRotation * 2;
	public static final double turnTableTicksPerRotation= 23567;
	
	//Driving
	public static final double teleopDriveSpeed = 1;
	public static final double wheelRotationsPerTurn = (22.5 / 7.5) * 1.25 * 0.95;
	public static final double maxAutonVelocity = 2.1;
	public static final double maxAutonAcceleration = 1.5;
	public static final ConfigurablePid driveForwardPid = new ConfigurablePid("driveForwardPID", new PIDConstants(.2, 0, 0, 0, 0));
	public static final ConfigurablePid driveTurnPid = new ConfigurablePid("driveTurnPID", new PIDConstants(2, 0, 0, 0, 0));
	
	//Arm
	
	public static DoubleConstant highGoalPoint = new DoubleConstant("high goal setpoint", 0.12);
	public static DoubleConstant flySpeed = new DoubleConstant("flywheel speed", -29000.0);
}

