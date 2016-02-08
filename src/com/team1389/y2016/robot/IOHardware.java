package com.team1389.y2016.robot;

import org.strongback.hardware.Hardware;

import com.team1389.base.wpiWrappers.TalonSRXPositionFollower;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;
import com.team1389.base.wpiWrappers.TalonSRXSpeedFollower;

import edu.wpi.first.wpilibj.CANTalon;

public class IOHardware extends IOLayout{
	public IOHardware() {
		//Outputs:

		//driveTrain
		leftDriveA = Hardware.Motors.talonSRX(new CANTalon(RobotMap.leftMotorA_CAN));
			if(RobotMap.leftMotorA_isInverted) {leftDriveA = leftDriveA.invert();}
		leftDriveB = Hardware.Motors.talonSRX(new CANTalon(RobotMap.leftMotorB_CAN));
			if(RobotMap.leftMotorB_isInverted) {leftDriveB = leftDriveB.invert();}
		leftDriveC = Hardware.Motors.talonSRX(new CANTalon(RobotMap.leftMotorC_CAN));
			if(RobotMap.leftMotorC_isInverted) {leftDriveC = leftDriveC.invert();}
		rightDriveA = Hardware.Motors.talonSRX(new CANTalon(RobotMap.rightMotorA_CAN));
			if(RobotMap.rightMotorA_isInverted) {rightDriveA = rightDriveA.invert();}
		rightDriveB = Hardware.Motors.talonSRX(new CANTalon(RobotMap.rightMotorB_CAN));
			if(RobotMap.rightMotorB_isInverted) {rightDriveB = rightDriveB.invert();}
		rightDriveC = Hardware.Motors.talonSRX(new CANTalon(RobotMap.rightMotorC_CAN));
			if(RobotMap.rightMotorC_isInverted) {rightDriveC = rightDriveC.invert();}
		
		//arm
		turntableMotor = new TalonSRXPositionHardware(new CANTalon(RobotMap.turntableMotor_CAN), RobotMap.turnTableTicksPerDegree,
				RobotMap.turntableMotor_isInverted);
		TalonSRXPositionHardware armElevatorMotorATalon = new TalonSRXPositionHardware(new CANTalon(RobotMap.elevatorMotorA_CAN),
				RobotMap.armElevationTicksPerDegree, RobotMap.elevatorMotorA_isInverted);
		armElevationMotorA  = armElevatorMotorATalon;
		armElevationMotorB = new TalonSRXPositionFollower(new CANTalon(RobotMap.elevatorMotorB_CAN), RobotMap.elevatorMotorA_CAN,
				RobotMap.elevatorMotorB_isInverted);
		
		//ball manipulator
		intakeMotor = Hardware.Motors.talonSRX(new CANTalon(RobotMap.intakeMotor_CAN));
			if(RobotMap.intakeMotor_isInverted) {intakeMotor = intakeMotor.invert();}
		flywheelMotorA = Hardware.Motors.talonSRX(new CANTalon(RobotMap.flywheelMotorA_CAN));
			if(RobotMap.flywheelMotorA_isInverted) {flywheelMotorA = flywheelMotorA.invert();}
		flywheelMotorB = new TalonSRXSpeedFollower(new CANTalon(RobotMap.flywheelMotorB_CAN),
				RobotMap.flywheelMotorA_CAN, RobotMap.flywheelMotorB_isInverted);
		
		//Inputs
		ballHolderIR = Hardware.Switches.normallyClosed(RobotMap.ballHolderIR_DIO);
		
		//Human Inputs
		controllerDriver = Hardware.HumanInterfaceDevices.driverStationJoystick(RobotMap.driveJoystickPort);
		controllerManip = Hardware.HumanInterfaceDevices.driverStationJoystick(RobotMap.manipJoystickPort);
	}
}
