package com.team1389.y2016.robot;

import org.strongback.hardware.Hardware;

import com.kauailabs.navx.frc.AHRS;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;
import com.team1389.y2016.robot.control.CombinedSolenoid;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;

public class IOHardware extends IOLayout{
	public IOHardware() {
		//Outputs:

		//driveTrain
		leftDriveA = createCANTalon(RobotMap.leftMotorA_CAN, RobotMap.leftMotorA_isInverted,
				TalonControlMode.PercentVbus, RobotMap.leftEncoderInverted);
		leftDriveA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		leftDriveA.reverseOutput(false);//NOTE THIS IS HERE
		
		leftDriveB = new CANTalon(RobotMap.leftMotorB_CAN);
		leftDriveC = new CANTalon(RobotMap.leftMotorC_CAN);
		
		leftDriveController = new TalonSRXPositionHardware(leftDriveA, RobotMap.wheelTicksPerRotation);
		
		
		rightDriveA = createCANTalon(RobotMap.rightMotorA_CAN, RobotMap.rightMotorA_isInverted,
				TalonControlMode.PercentVbus, RobotMap.rightEncoderInverted);
		rightDriveA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightDriveA.reverseOutput(true);

		rightDriveB = new CANTalon(RobotMap.rightMotorB_CAN);
		rightDriveC = new CANTalon(RobotMap.rightMotorC_CAN);
		
		rightDriveController = new TalonSRXPositionHardware(rightDriveA, RobotMap.wheelTicksPerRotation);

		ringLight=new CombinedSolenoid(RobotMap.ringLightA_Sol,RobotMap.ringLightB_Sol);
		//arm
		
		simpleElevationA = createCANTalon(RobotMap.elevatorMotorA_CAN, RobotMap.elevatorMotorA_isInverted,
				TalonControlMode.PercentVbus, RobotMap.elevatorEncoderInverted);
		simpleElevationB = new CANTalon(RobotMap.elevatorMotorB_CAN);
		
		armElevationMotor = new TalonSRXPositionHardware(simpleElevationA, RobotMap.armElevationTicksPerRotation);
		//System.out.println("ELEVATOR CONFIGURED AND READY");
		
		//uncomment for turntable
		simpleTurntable = createCANTalon(RobotMap.turntableMotor_CAN, RobotMap.turntableMotor_isInverted,					TalonControlMode.PercentVbus, RobotMap.turntableEncoderInverted);
	turntableMotor = new TalonSRXPositionHardware(simpleTurntable, RobotMap.turnTableTicksPerRotation);
		
		
		//ball manipulator
		intakeMotor = Hardware.Motors.victorSP(RobotMap.intakeMotor_CAN);
			if(RobotMap.intakeMotor_isInverted) {intakeMotor = intakeMotor.invert();}
			
		flywheelMotorA = createCANTalon(RobotMap.flywheelMotorA_CAN, RobotMap.flywheelMotorA_isInverted,
				TalonControlMode.PercentVbus, RobotMap.flywheelEncoderInverted);
		
		flywheelMotorB = createCANTalon(RobotMap.flywheelMotorB_CAN, RobotMap.flywheelMotorB_isInverted,
				TalonControlMode.Follower, false);
		
		flywheelFancy = new TalonSRXPositionHardware(flywheelMotorA, 4096 * (18.0 / 42.0));
		
		//Climber
		winchRelease = new Servo(RobotMap.winchRelease_PWM);
		climberTalon = createCANTalon(RobotMap.secondArmTalon_CAN,false,TalonControlMode.Position,false);		
		climber = new TalonSRXPositionHardware(climberTalon, RobotMap.encoderTicksPerRotation);
				
		configFollowerTalonsToWorkAroundDumbGlitch();
		
		//Inputs
		ballHolderIR1 = Hardware.Switches.normallyClosed(RobotMap.ballHolderIR1_DIO);
		ballHolderIR2 = Hardware.Switches.normallyClosed(RobotMap.ballHolderIR2_DIO);
		imu = new AHRS(SPI.Port.kMXP);
		gyro=new AnalogGyro(0);
		gyro.initGyro();
		gyro.calibrate();
		//Human Inputs
		controllerDriver = Hardware.HumanInterfaceDevices.driverStationJoystick(RobotMap.driveJoystickPort);
		controllerManip = Hardware.HumanInterfaceDevices.driverStationJoystick(RobotMap.manipJoystickPort);
		controllerFake = Hardware.HumanInterfaceDevices.driverStationJoystick(2);
	}
	
	private static CANTalon createCANTalon(int port, boolean reverse, TalonControlMode mode, boolean sensorReversed){
		CANTalon talon = new CANTalon(port);
		talon.setInverted(reverse);
		talon.reverseOutput(reverse);
		talon.changeControlMode(mode);
		talon.reverseSensor(sensorReversed);
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
		configFollowerTalon(leftDriveB, RobotMap.leftMotorB_isInverted, leftDriveA);
		configFollowerTalon(leftDriveC, RobotMap.leftMotorC_isInverted, leftDriveA);
		
		configFollowerTalon(rightDriveB, RobotMap.rightMotorB_isInverted, rightDriveA);
		configFollowerTalon(rightDriveC, RobotMap.rightMotorC_isInverted, rightDriveA);
		
		configFollowerTalon(simpleElevationB, RobotMap.elevatorMotorB_isInverted, simpleElevationA);
		
		configFollowerTalon(flywheelMotorB, RobotMap.flywheelMotorB_isInverted, flywheelMotorA);
	}
}
