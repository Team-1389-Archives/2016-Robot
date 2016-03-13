package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConstantSpeedSetpointProvider;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;

import edu.wpi.first.wpilibj.CANTalon;

public class FlywheelControlRPM extends Command{

	ConstantSpeedSetpointProvider motor;
	InputDevice joy;
	
	public FlywheelControlRPM(ConstantSpeedSetpointProvider motor, InputDevice joy) {
		this.motor = motor;
		this.joy = joy;
	}
	
	@Override
	public boolean execute() {
		if (joy.getButton(1).isTriggered()){
			motor.setSpeed(-1.0);
		} else {
			motor.setSpeed(joy.getAxis(1).read());
		}
		return false;
	}

}
