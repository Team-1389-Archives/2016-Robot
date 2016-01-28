package com.team1389.y2016.robot;

import org.strongback.hardware.Hardware;

import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;

import edu.wpi.first.wpilibj.CANTalon;

public class IOHardware extends IOLayout{
	public IOHardware() {
		//Outputs:

		//driveTrain
		leftDriveA = Hardware.Motors.talonSRX(new CANTalon(RobotMap.leftMotorA_CAN));
		leftDriveB = Hardware.Motors.talonSRX(new CANTalon(RobotMap.leftMotorB_CAN));
		leftDriveC = Hardware.Motors.talonSRX(new CANTalon(RobotMap.leftMotorC_CAN));
		rightDriveA = Hardware.Motors.talonSRX(new CANTalon(RobotMap.rightMotorA_CAN));
		rightDriveB = Hardware.Motors.talonSRX(new CANTalon(RobotMap.rightMotorB_CAN));
		rightDriveC = Hardware.Motors.talonSRX(new CANTalon(RobotMap.rightMotorC_CAN));
		
		//arm
		turntableMotor = new TalonSRXPositionHardware(new CANTalon(RobotMap.turntableMotor_CAN), RobotMap.turnTableTicksPerDegree);
		armElevationMotorA = new TalonSRXPositionHardware(new CANTalon(RobotMap.elevatorMotorA_CAN), RobotMap.armElevationTicksPerDegree);
		armElevationMotorB = new TalonSRXPositionHardware(new CANTalon(RobotMap.elevatorMotorB_CAN), RobotMap.armElevationTicksPerDegree);
		
		//ball manipulator
		intakeMotor = Hardware.Motors.talonSRX(new CANTalon(RobotMap.intakeMotor_CAN));
		flywheelMotorA = Hardware.Motors.talonSRX(new CANTalon(RobotMap.flywheelMotorA_CAN));
		flywheelMotorB = Hardware.Motors.talonSRX(new CANTalon(RobotMap.flywheelMotorB_CAN));
		
		//Inputs
		ballHolderIR = Hardware.Switches.normallyClosed(RobotMap.ballHolderIR_DIO);
		
		//Human Inputs
		controllerDriver = Hardware.HumanInterfaceDevices.driverStationJoystick(RobotMap.driveJoystickPort);
		controllerManip = Hardware.HumanInterfaceDevices.driverStationJoystick(RobotMap.manipJoystickPort);
	}
}
