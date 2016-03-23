package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.InputDevice;

public class IntakeControlCommand extends Command{
	
	Motor motor;
	InputDevice joystick;
	ContinuousRange axis;
	Switch button, ir;
	
	public IntakeControlCommand(Motor motor, ContinuousRange axis, Switch button, Switch ir) {
		this.motor = motor;
		this.axis = axis;
		this.button = button;
		this.ir = ir;
	}

	@Override
	public boolean execute() {
		double speed;
		
		boolean canGoIn = !ir.isTriggered() || button.isTriggered();
		speed = axis.read();
		
		double finalSpeed;
		
		if (canGoIn){
			finalSpeed = speed;
		} else {
			finalSpeed = -1.0;
		}
		
		motor.setSpeed(finalSpeed);
		return false;
	}

}
