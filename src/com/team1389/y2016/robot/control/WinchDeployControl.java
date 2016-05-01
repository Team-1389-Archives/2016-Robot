package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.Switch;

import edu.wpi.first.wpilibj.Servo;

public class WinchDeployControl extends Command{

	Switch button;
	Servo servo;
	
	boolean hasBeenActivated;
	
	public WinchDeployControl(Switch button, Servo servo) {
		this.button = button;
		this.servo = servo;
		hasBeenActivated = false;
	}
	
	@Override
	public boolean execute() {
		if(hasBeenActivated){
			servo.set(1.0);
		} else {
			servo.set(0.0);
			if (button.isTriggered()){
				hasBeenActivated = true;
			}
		}
		return false;
	}

}
