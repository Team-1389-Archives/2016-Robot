package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.Switch;

import edu.wpi.first.wpilibj.Servo;

public class WinchServoControl extends Command{

	Servo servo;
	Switch button;
	boolean isTriggered;
	
	public WinchServoControl(Servo servo, Switch button) {
		this.servo = servo;
		this.button = button;
	}
	
	@Override
	public void initialize() {
		isTriggered = false;
	}
	
	@Override
	public boolean execute() {
		if (button.isTriggered()){
			isTriggered = true;
		}
		servo.set(isTriggered ? 0.0 : 1.0);
		return false;
	}

}
