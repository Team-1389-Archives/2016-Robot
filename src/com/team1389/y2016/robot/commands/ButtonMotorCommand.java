package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.Switch;

public class ButtonMotorCommand extends Command{
	Motor motor;
	Switch button;
	boolean invert;
	
	public ButtonMotorCommand(Motor motor, Switch button, boolean invert) {
		this.motor = motor;
		this.button = button;
	}

	@Override
	public boolean execute() {
		double speed = 0.0;
		if (button.isTriggered()){
			speed = invert? 1.0: -1.0;
		}
		motor.setSpeed(speed);
		return false;
	}

}
