package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.Motor;

public class SetMotorCommand extends Command{
	
	final Motor motor;
	double speed;
	
	public SetMotorCommand(Motor motor, double speed) {
		this.motor = motor;
		this.speed = speed;
	}
	
	@Override
	public void initialize() {
		motor.setSpeed(speed);
	}

	@Override
	public boolean execute() {
		return true;
	}

}
