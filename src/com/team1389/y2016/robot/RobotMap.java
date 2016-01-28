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
	public static int leftMotorA_CAN = 1;
	public static int leftMotorB_CAN = 2;
	public static int leftMotorC_CAN = 3;
	public static int rightMotorA_CAN = 4;
	public static int rightMotorB_CAN = 5;
	public static int rightMotorC_CAN = 6;
	//Arm
	public static int turntableMotor_CAN = 7;
	public static int elevatorMotorA_CAN = 8;
	public static int elevatorMotorB_CAN = 9;
	//Ball Manipulator
	public static int intakeMotor_CAN = 10;
	public static int flywheelMotorA_CAN = 11;
	public static int flywheelMotorB_CAN = 12;
	
	//Inputs:
	//Ball Manipulator
	public static int ballHolderIR_DIO = 0;
	
	//Misc
	public static int driveJoystickPort = 0;
	public static int manipJoystickPort = 1;
	
	//Dimensions
	public static double referenceTurntableAngle = -17;//the angle that the turntable uses as a reference to zero itself
	public static double referenceArmElevationAngle = 0;//the angle that the arm is completely down
	
	public static double armElevationTicksPerDegree = 1;
	public static double turnTableTicksPerDegree = 1;
}
