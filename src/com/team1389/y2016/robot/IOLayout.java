package com.team1389.y2016.robot;

import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.ui.Gamepad;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.wpiWrappers.PositionController;

public abstract class IOLayout{
	//driveTrain
	TalonSRX leftDriveA;
	TalonSRX leftDriveB;
	TalonSRX leftDriveC;
	TalonSRX rightDriveA;
	TalonSRX rightDriveB;
	TalonSRX rightDriveC;
	
	//arm
	PositionController turntableMotor;
	PositionController armElevationMotorA;
	PositionController armElevationMotorB;
	
	//Ball Manipulator
	TalonSRX intakeMotor;
	TalonSRX flywheelMotorA;
	TalonSRX flywheelMotorB;
	
	//Inputs
	Switch ballHolderIR;
	
	//Human Controlls
	InputDevice controllerDriver;
	InputDevice controllerManip;
}
