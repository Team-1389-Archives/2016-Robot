package com.team1389.y2016.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Outputs:
	//Drivetrain
	public static int leftMotorA_CAN = 7; public static boolean leftMotorA_isInverted = false;
	public static int leftMotorB_CAN = 8; public static boolean leftMotorB_isInverted = false;
	public static int leftMotorC_CAN = 4; public static boolean leftMotorC_isInverted = false;
	public static int rightMotorA_CAN = 2; public static boolean rightMotorA_isInverted = false;
	public static int rightMotorB_CAN = 1; public static boolean rightMotorB_isInverted = false;
	public static int rightMotorC_CAN = 3; public static boolean rightMotorC_isInverted = false;
	//Arm
	public static int turntableMotor_CAN = 9; public static boolean turntableMotor_isInverted = false;
	public static int elevatorMotorA_CAN = 5; public static boolean elevatorMotorA_isInverted = true;
	public static int elevatorMotorB_CAN = 6; public static boolean elevatorMotorB_isInverted = true;
	//Ball Manipulator
	public static int intakeMotor_CAN = 11; public static boolean intakeMotor_isInverted = false;
	public static int flywheelMotorA_CAN = 10; public static boolean flywheelMotorA_isInverted = false;
	
	//Inputs:
	public static double encoderTicksPerRotation = 4096;

	public static boolean leftEncoderInverted = true;
	public static boolean rightEncoderInverted = true;
	public static boolean elevatorEncoderInverted = true;
	public static boolean turntableEncoderInverted = false;
	
	//Ball Manipulator
	public static int ballHolderIR_DIO = 0; public static boolean ballHolderIR_isInverted = false;
	
	//Misc
	public static int driveJoystickPort = 0;
	public static int manipJoystickPort = 1;
	public static double referenceArmElevationAngle = 0;//the angle that the arm is completely down
	
	public static double leftEncoderTicksPerRotation = encoderTicksPerRotation * .75;
	public static double rightEncoderSpeedMod = encoderTicksPerRotation * .75;
	public static double armElevationTicksPerRotation = encoderTicksPerRotation * 2;
	public static double turnTableTicksPerRotation= 23567;
	
}

