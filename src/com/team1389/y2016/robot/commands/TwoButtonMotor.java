package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.Switch;

public class TwoButtonMotor extends Command {
	Switch one, two;
	Motor motor;
	double speedMod;
	
	public TwoButtonMotor(Switch one, Switch two, double speedMod, Motor motor) {
		this.one = one;
		this.two = two;
		this.motor = motor;
		this.speedMod = speedMod;
	}

	@Override
	public boolean execute() {
		double speed = 0.0;
		if (one.isTriggered()){
			speed = speedMod;
		} else if (two.isTriggered()){
			speed = -speedMod;
		}
		return false;
	}

}
