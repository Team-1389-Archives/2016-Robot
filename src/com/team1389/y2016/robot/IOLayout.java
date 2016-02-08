package com.team1389.y2016.robot;

import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.wpiWrappers.FollowerMotor;
import com.team1389.base.wpiWrappers.PositionController;

public abstract class IOLayout{
	//driveTrain
	public Motor leftDriveA;
	public Motor leftDriveB;
	public Motor leftDriveC;
	public Motor rightDriveA;
	public Motor rightDriveB;
	public Motor rightDriveC;
	
	//arm
	public PositionController turntableMotor;
	public PositionController armElevationMotorA;
	public FollowerMotor armElevationMotorB;
	
	//Ball Manipulator
	public Motor intakeMotor;
	public Motor flywheelMotorA;
	public FollowerMotor flywheelMotorB;
	
	//Inputs
	public Switch ballHolderIR;
	
	//Human Controlls
	public InputDevice controllerDriver;
	public InputDevice controllerManip;
}
