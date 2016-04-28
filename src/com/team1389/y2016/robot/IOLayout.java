package com.team1389.y2016.robot;

import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.wpiWrappers.SmartCANTalon;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;

public abstract class IOLayout{
	//driveTrain
	public CANTalon leftDriveA;
	public CANTalon leftDriveB;
	public CANTalon leftDriveC;
	public CANTalon rightDriveA;
	public CANTalon rightDriveB;
	public CANTalon rightDriveC;
	
	public TalonSRXPositionHardware leftDriveController;
	public TalonSRXPositionHardware rightDriveController;
	
	//arm
		//uncomment for turntable
//	public TalonSRXPositionHardware turntableMotor;
	public TalonSRXPositionHardware armElevationMotor;
	
	public CANTalon simpleElevationA;
	public CANTalon simpleElevationB;
		//uncomment for turntable
//	public CANTalon simpleTurntable;
	
	//Ball Manipulator
	public Motor intakeMotor;
	public CANTalon flywheelMotorA;
	public CANTalon flywheelMotorB;
	
	public TalonSRXPositionHardware flywheelFancy;
	
	//Inputs
	public Switch ballHolderIR1;
	public Switch ballHolderIR2;
	
	//Human Controlls
	public InputDevice controllerDriver;
	public InputDevice controllerManip;
	public InputDevice controllerFake;
	
	//Climbing Mechanism
	Servo winchRelease;
	SmartCANTalon secondArm;
	
	public abstract void configFollowerTalonsToWorkAroundDumbGlitch();
}
