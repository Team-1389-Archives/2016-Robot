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
		
		leftDriveB = new CANTalon(RobotMap.leftMotorB_CAN);
		leftDriveC = new CANTalon(RobotMap.leftMotorC_CAN);
		
		
		rightDriveA = createCANTalon(RobotMap.rightMotorA_CAN, RobotMap.rightMotorA_isInverted,
				TalonControlMode.PercentVbus, RobotMap.rightEncoderInverted);
		rightDriveB = new CANTalon(RobotMap.rightMotorB_CAN);
		rightDriveC = new CANTalon(RobotMap.rightMotorC_CAN);

		
		//arm
		turntableMotor = new TalonSRXPositionHardware(new CANTalon(RobotMap.turntableMotor_CAN), RobotMap.turnTableTicksPerDegree,
				RobotMap.turntableMotor_isInverted);
//		TalonSRXPositionHardware armElevatorMotorATalon = new TalonSRXPositionHardware(new CANTalon(RobotMap.elevatorMotorA_CAN),
//				RobotMap.armElevationTicksPerDegree, RobotMap.elevatorMotorA_isInverted);
//		armElevationMotorA  = armElevatorMotorATalon;
//		armElevationMotorB = new TalonSRXPositionFollower(new CANTalon(RobotMap.elevatorMotorB_CAN), RobotMap.elevatorMotorA_CAN,
//				RobotMap.elevatorMotorB_isInverted);
		
		simpleElevationA = createCANTalon(RobotMap.elevatorMotorA_CAN, RobotMap.elevatorMotorA_isInverted,
				TalonControlMode.PercentVbus, RobotMap.elevatorEncoderInverted);
		simpleElevationB = new CANTalon(RobotMap.elevatorMotorB_CAN);
		
		CANTalon simpleTurn = new CANTalon(RobotMap.turntableMotor_CAN);
		simpleTurntable = simpleTurn;
		
		
		configFollowerTalonsToWorkAroundDumbGlitch();
		
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
	
	private static void configFollowerTalon(CANTalon talon, boolean reverse, CANTalon toFollow){
		talon.changeControlMode(TalonControlMode.Follower);
		talon.setInverted(reverse);
		talon.set(toFollow.getDeviceID());
	}
	
	/**
	 * when test mode is enabled, follower talons stop following.
	 */
	@Override
	public void configFollowerTalonsToWorkAroundDumbGlitch() {
		System.out.println("configging talons");
		configFollowerTalon(leftDriveB, RobotMap.leftMotorB_isInverted, leftDriveA);
		configFollowerTalon(leftDriveC, RobotMap.leftMotorC_isInverted, leftDriveA);
		
		configFollowerTalon(rightDriveB, RobotMap.rightMotorB_isInverted, rightDriveA);
		configFollowerTalon(rightDriveC, RobotMap.rightMotorC_isInverted, rightDriveA);
		
		configFollowerTalon(simpleElevationB, RobotMap.elevatorMotorB_isInverted, simpleElevationA);
	}
}
