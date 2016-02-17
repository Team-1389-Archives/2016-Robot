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
	public static int rightMotorA_CAN = 2; public static boolean rightMotorA_isInverted = true;
	public static int rightMotorB_CAN = 1; public static boolean rightMotorB_isInverted = true;
	public static int rightMotorC_CAN = 3; public static boolean rightMotorC_isInverted = true;
	//Arm
	public static int turntableMotor_CAN = 9; public static boolean turntableMotor_isInverted = false;
	public static int elevatorMotorA_CAN = 5; public static boolean elevatorMotorA_isInverted = false;
	public static int elevatorMotorB_CAN = 6; public static boolean elevatorMotorB_isInverted = true;
	//Ball Manipulator
	public static int intakeMotor_CAN = 11; public static boolean intakeMotor_isInverted = false;
	public static int flywheelMotorA_CAN = 10; public static boolean flywheelMotorA_isInverted = false;
	
	//Inputs:
	//Ball Manipulator
	public static boolean leftEncoderInverted = true;
	public static double leftEncoderSpeedMod = -100;
	public static boolean rightEncoderInverted = true;
	public static double rightEncoderSpeedMod = 160;
	public static int ballHolderIR_DIO = 0; public static boolean ballHolderIR_isInverted = false;
	
	//Misc
	public static int driveJoystickPort = 0; public static boolean driveJoystickPortisInverted = false;
	public static int manipJoystickPort = 1; public static boolean manipJoystickPortisInverted = false;
	
	//Dimensions
	public static double referenceTurntableAngle = -17;//the angle that the turntable uses as a reference to zero itself
	public static double referenceArmElevationAngle = 0;//the angle that the arm is completely down
	
	public static double armElevationTicksPerDegree = 1;
	public static double turnTableTicksPerDegree = 1;
	
	public static double encoderTicksPerRotation = 4096;
}

