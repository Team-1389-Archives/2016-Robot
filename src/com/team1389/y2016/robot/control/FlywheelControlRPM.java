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
	double speedMod;
	
	public FlywheelControlRPM(ConstantSpeedSetpointProvider motor, InputDevice joy) {
		this.motor = motor;
		this.joy = joy;
		speedMod = 1;
	}
	
	@Override
	public boolean execute() {
		double speed;
		if (joy.getButton(1).isTriggered()){
			speed = -1.0;
		} else {
			speed = joy.getAxis(1).read();
		}
		double moddedSpeed = speed * speedMod;
		motor.setSpeed(moddedSpeed);
		return false;
	}

}
