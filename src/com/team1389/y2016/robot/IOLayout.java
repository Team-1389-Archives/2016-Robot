package com.team1389.y2016.robot;

import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.ui.Gamepad;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.wpiWrappers.PositionController;

public abstract class IOLayout{
	//driveTrain
	public TalonSRX leftDriveA;
	public TalonSRX leftDriveB;
	public TalonSRX leftDriveC;
	public TalonSRX rightDriveA;
	public TalonSRX rightDriveB;
	public TalonSRX rightDriveC;
	
	//arm
	public PositionController turntableMotor;
	public PositionController armElevationMotorA;
	public PositionController armElevationMotorB;
	
	//Ball Manipulator
	public TalonSRX intakeMotor;
	public TalonSRX flywheelMotorA;
	public TalonSRX flywheelMotorB;
	
	//Inputs
	public Switch ballHolderIR;
	
	//Human Controlls
	public InputDevice controllerDriver;
	public InputDevice controllerManip;
}
