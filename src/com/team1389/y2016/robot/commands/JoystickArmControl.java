package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.ui.InputDevice;

public class JoystickArmControl extends Command{
	Motor talon;
	InputDevice joystick;
	
	public JoystickArmControl(Motor motor, InputDevice joystick) {
		talon = motor;
		this.joystick = joystick;
	}

	@Override
	public boolean execute() {
		double motorPower = joystick.getAxis(1).read();
		talon.setSpeed(motorPower);
		return false;
	}

}
