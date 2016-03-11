package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.components.ui.InputDevice;

public class IntakeControlCommand extends Command{
	
	Motor motor;
	InputDevice joystick;
	DirectionalAxis pov;
	
	public IntakeControlCommand(Motor motor, DirectionalAxis pov) {
		this.motor = motor;
		this.pov = pov;
	}

	@Override
	public boolean execute() {
		double dir = pov.getDirection();
		if (dir == 0 || dir == 315 || dir == 45){
			motor.setSpeed(-1);
		} else if (dir == 225 || dir == 180 || dir == 135){
			motor.setSpeed(1);
		} else {
			motor.setSpeed(0);
		}
		return false;
	}

}
