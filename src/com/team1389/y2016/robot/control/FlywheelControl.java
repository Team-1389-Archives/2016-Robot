package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import edu.wpi.first.wpilibj.CANTalon;

public class FlywheelControl extends Command{

	CANTalon motor;
	InputDevice joy;
	
	public FlywheelControl(CANTalon motor, InputDevice joy) {
		this.motor = motor;
		this.joy = joy;
	}
	@Override
	public boolean execute() {
		if (joy.getButton(1).isTriggered()){
			motor.set(-1);
		} else {
			motor.set(joy.getAxis(1).read());
		}
		return false;
	}

}
