package com.team1389.y2016.robot;

import org.strongback.components.TalonSRX;
import org.strongback.hardware.Hardware;

import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class IOHardware extends IOLayout{
	public IOHardware() {
		//Outputs:

		//driveTrain
		leftDriveA = createCANTalon(RobotMap.leftMotorA_CAN, RobotMap.leftMotorA_isInverted,
				TalonControlMode.PercentVbus, RobotMap.leftEncoderInverted);
		leftDriveA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		leftDriveB = createCANFollower(RobotMap.leftMotorB_CAN, RobotMap.leftMotorB_isInverted, leftDriveA);
		leftDriveC = createCANFollower(RobotMap.leftMotorC_CAN, RobotMap.leftMotorC_isInverted, leftDriveA);
		
		
		rightDriveA = createCANTalon(RobotMap.rightMotorA_CAN, RobotMap.rightMotorA_isInverted,
				TalonControlMode.PercentVbus, RobotMap.rightEncoderInverted);
		rightDriveB = createCANFollower(RobotMap.rightMotorB_CAN, RobotMap.rightMotorB_isInverted, rightDriveA);
		rightDriveC = createCANFollower(RobotMap.rightMotorC_CAN, RobotMap.rightMotorC_isInverted, rightDriveA);
		
		//arm
		turntableMotor = new TalonSRXPositionHardware(new CANTalon(RobotMap.turntableMotor_CAN), RobotMap.turnTableTicksPerDegree,
				RobotMap.turntableMotor_isInverted);
//		TalonSRXPositionHardware armElevatorMotorATalon = new TalonSRXPositionHardware(new CANTalon(RobotMap.elevatorMotorA_CAN),
//				RobotMap.armElevationTicksPerDegree, RobotMap.elevatorMotorA_isInverted);
//		armElevationMotorA  = armElevatorMotorATalon;
//		armElevationMotorB = new TalonSRXPositionFollower(new CANTalon(RobotMap.elevatorMotorB_CAN), RobotMap.elevatorMotorA_CAN,
//				RobotMap.elevatorMotorB_isInverted);
		
		CANTalon simpleA = new CANTalon(RobotMap.elevatorMotorA_CAN);
		simpleA.setInverted(RobotMap.elevatorMotorA_isInverted);
		CANTalon simpleB = new CANTalon(RobotMap.elevatorMotorB_CAN);
		simpleB.setInverted(RobotMap.elevatorMotorB_isInverted);
		simpleElevationA = Hardware.Motors.talonSRX(simpleA);
		simpleElevationB = Hardware.Motors.talonSRX(simpleB);
		
		//ball manipulator
		intakeMotor = Hardware.Motors.talonSRX(new CANTalon(RobotMap.intakeMotor_CAN));
			if(RobotMap.intakeMotor_isInverted) {intakeMotor = intakeMotor.invert();}
			
		flywheelMotorA = createCANTalon(RobotMap.flywheelMotorA_CAN, RobotMap.flywheelMotorA_isInverted,
				TalonControlMode.PercentVbus, false);
//		flywheelMotorA = Hardware.Motors.talonSRX(flywheelTalon);
		
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
	
	private static CANTalon createCANTalon(int port, boolean reverse, TalonControlMode mode, boolean sensorReversed){
		CANTalon talon = new CANTalon(port);
		talon.setInverted(reverse);
		talon.changeControlMode(mode);
		talon.reverseSensor(sensorReversed);
		return talon;
	}
	
	private static CANTalon createCANFollower(int port, boolean reverse, CANTalon toFollow){
		CANTalon talon = new CANTalon(port);
		talon.changeControlMode(TalonControlMode.Follower);
		talon.setInverted(reverse);
		talon.set(toFollow.getDeviceID());
		return talon;
	}
}
