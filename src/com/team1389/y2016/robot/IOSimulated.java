package com.team1389.y2016.robot;

import org.strongback.mock.Mock;

import com.team1389.base.wpiWrappers.MockPositionController;

public class IOSimulated extends IOLayout{
	public IOSimulated() {

		//driveTrain
		leftDriveA = Mock.stoppedTalonSRX();
		leftDriveB = Mock.stoppedTalonSRX();
		leftDriveC = Mock.stoppedTalonSRX();
		rightDriveA = Mock.stoppedTalonSRX();
		rightDriveB = Mock.stoppedTalonSRX();
		rightDriveC = Mock.stoppedTalonSRX();
		
		//arm
		turntableMotor = new MockPositionController(0);
		armElevationMotorA = new MockPositionController(0);
		armElevationMotorB = new MockPositionController(0);
		
		//ball manipulator
		intakeMotor = Mock.stoppedTalonSRX();
		flywheelMotorA = Mock.stoppedTalonSRX();
		flywheelMotorB = Mock.stoppedTalonSRX();
		
		//Inputs
		ballHolderIR = Mock.notTriggeredSwitch();
		leftDriveA.setSpeed(5);
		
		//Human Inputs
		controllerDriver = null;
		controllerManip = null;
	}
}
