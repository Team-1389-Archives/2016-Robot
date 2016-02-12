package com.team1389.y2016.robot;

import org.strongback.components.TalonSRX;
import org.strongback.hardware.Hardware;
import org.strongback.mock.Mock;

import com.team1389.base.wpiWrappers.TalonSRXPositionFollower;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;
import com.team1389.base.wpiWrappers.TalonSRXSpeedFollower;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class IOHardware extends IOLayout{
	public IOHardware() {
		//Outputs:

		//driveTrain
		CANTalon leftATalon = new CANTalon(RobotMap.leftMotorA_CAN);
		leftATalon.setInverted(RobotMap.leftMotorA_isInverted);
		leftDriveA = Hardware.Motors.talonSRX(leftATalon);
		leftDriveB = createFollwerTalon(RobotMap.leftMotorB_CAN, leftDriveA, RobotMap.leftMotorB_isInverted);
		leftDriveC = createFollwerTalon(RobotMap.leftMotorC_CAN, leftDriveA, RobotMap.leftMotorC_isInverted);
		
		
		CANTalon rightATalon = new CANTalon(RobotMap.rightMotorA_CAN);
		rightATalon.setInverted(RobotMap.rightMotorA_isInverted);
		rightDriveA = Hardware.Motors.talonSRX(rightATalon);
		rightDriveB = createFollwerTalon(RobotMap.rightMotorB_CAN, rightDriveA, RobotMap.rightMotorB_isInverted);
		rightDriveC = createFollwerTalon(RobotMap.rightMotorC_CAN, rightDriveA, RobotMap.rightMotorC_isInverted);


		
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
	
	private static TalonSRX createFollwerTalon(int port, TalonSRX toFollow, boolean invert){
		CANTalon talon = new CANTalon(port);
		talon.setInverted(invert);
        talon.changeControlMode(TalonControlMode.Follower);
        talon.set(toFollow.getDeviceID());
//        talon.reverseOutput(invert);
        return Hardware.Motors.talonSRX(talon);

	}
}
