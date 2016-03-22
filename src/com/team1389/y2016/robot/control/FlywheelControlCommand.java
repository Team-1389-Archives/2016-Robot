package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.control.SpeedControllerSetCommand;

public class FlywheelControlCommand extends Command{
	final int SPIN_UP_BUTTON = 5;
	SpeedControllerSetCommand speedControlCommand;
	InputDevice joystick;
	double speed;

	public FlywheelControlCommand(InputDevice joystick, SpeedControllerSetCommand speedControlCommand, double speed) {
		this.speedControlCommand = speedControlCommand;
		this.joystick = joystick;
		this.speed = speed;
	}
	
	@Override
	public void initialize() {
	}

	@Override
	public boolean execute() {
		boolean spinning = joystick.getButton(SPIN_UP_BUTTON).isTriggered();
		
		if (spinning){
			speedControlCommand.setSpeed(speed);
		} else {
			speedControlCommand.setSpeed(0);
		}
		return false;
	}
	
	@Override
	public void end() {
		speedControlCommand.setSpeed(0);
	}
	
	@Override
	public void interrupted() {
		end();
	}

}
