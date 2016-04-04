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
	public static int leftMotorA_CAN = 4; public static boolean leftMotorA_isInverted = true;
	public static int leftMotorB_CAN = 8; public static boolean leftMotorB_isInverted = true;
	public static int leftMotorC_CAN = 7; public static boolean leftMotorC_isInverted = true;
	public static int rightMotorA_CAN = 2; public static boolean rightMotorA_isInverted = false;
	public static int rightMotorB_CAN = 1; public static boolean rightMotorB_isInverted = false;
	public static int rightMotorC_CAN = 3; public static boolean rightMotorC_isInverted = false;
	//Arm
	public static int turntableMotor_CAN = 9; public static boolean turntableMotor_isInverted = false;
	public static int elevatorMotorA_CAN = 5; public static boolean elevatorMotorA_isInverted = true;
	public static int elevatorMotorB_CAN = 6; public static boolean elevatorMotorB_isInverted = true;
	//Ball Manipulator
	public static int intakeMotor_CAN = 11; public static boolean intakeMotor_isInverted = false;
	//DONT COMMIT THIS
	public static int flywheelMotorA_CAN = 10; public static boolean flywheelMotorA_isInverted = false;
	public static int flywheelMotorB_CAN = 12; public static boolean flywheelMotorB_isInverted = true;
	
	//Inputs:
	public static double encoderTicksPerRotation = 4096;

	public static boolean leftEncoderInverted = true;
	public static boolean rightEncoderInverted = false;
	public static boolean elevatorEncoderInverted = true;
	public static boolean turntableEncoderInverted = false;
	public static boolean flywheelEncoderInverted = false;
	
	//Ball Manipulator
	public static int ballHolderIR1_DIO = 0; public static boolean ballHolderIR1_isInverted = false;
	public static int ballHolderIR2_DIO = 1; public static boolean ballHolderIR2_isInverted = false;
	
	//Misc
	public static int driveJoystickPort = 0;
	public static int manipJoystickPort = 1;
	public static double referenceArmElevationAngle = 0;//the angle that the arm is completely down
	
	public static double wheelTicksPerRotation = encoderTicksPerRotation * .75;
	public static double rightEncoderSpeedMod = encoderTicksPerRotation * .75;
	public static double armElevationTicksPerRotation = encoderTicksPerRotation * 2;
	public static double turnTableTicksPerRotation= 23567;
	
	//Driving
	public static double teleopDriveSpeed = 1;
	public static double wheelRotationsPerTurn = (22.5 / 7.5) * 1.25 * 0.95;
	public static double maxAutonVelocity = 2.1;
	public static double maxAutonAcceleration = 1.5;
	public static ConfigurablePid driveForwardPid = new ConfigurablePid("driveForwardPID", new PIDConstants(.2, 0, 0, 0, 0));
	public static ConfigurablePid driveTurnPid = new ConfigurablePid("driveTurnPID", new PIDConstants(2, 0, 0, 0, 0));
	
	//Arm
	
	public static DoubleConstant highGoalPoint = new DoubleConstant("high goal setpoint", 0.12);
	public static DoubleConstant flySpeed = new DoubleConstant("flywheel speed", -27000.0);
}

