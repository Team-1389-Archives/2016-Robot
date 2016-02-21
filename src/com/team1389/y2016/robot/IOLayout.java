package com.team1389.y2016.robot;

import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;

import edu.wpi.first.wpilibj.CANTalon;

public abstract class IOLayout{
	//driveTrain
	public CANTalon leftDriveA;
	public CANTalon leftDriveB;
	public CANTalon leftDriveC;
	public CANTalon rightDriveA;
	public CANTalon rightDriveB;
	public CANTalon rightDriveC;
	
	//arm
	public TalonSRXPositionHardware turntableMotor;
	public TalonSRXPositionHardware armElevationMotor;
	
	public CANTalon simpleElevationA;
	public CANTalon simpleElevationB;
	public CANTalon simpleTurntable;
	
	//Ball Manipulator
	public Motor intakeMotor;
	public CANTalon flywheelMotorA;
	
	//Inputs
	public Switch ballHolderIR;
	
	//Human Controlls
	public InputDevice controllerDriver;
	public InputDevice controllerManip;
	public InputDevice controllerFake;
	
	public abstract void configFollowerTalonsToWorkAroundDumbGlitch();
}
