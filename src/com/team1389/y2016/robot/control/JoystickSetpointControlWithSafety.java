package com.team1389.y2016.robot.control;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;

import com.team1389.base.util.control.SetpointProvider;

public class JoystickSetpointControlWithSafety implements SetpointProvider {
	double position;
	double min, max, speed, start;
	ContinuousRange joystickAxis;
	Switch safetyButton;
	
	public JoystickSetpointControlWithSafety(ContinuousRange joystickAxis, Switch button, double min, double max, double speed, double start) {
		this.min = min;
		this.max = max;
		this.speed = speed;
		this.start = start;
		this.joystickAxis = joystickAxis;
		this.safetyButton = button;
		this.position = start;
	}
	
	@Override
	public double getSetpoint() {
		double joy = joystickAxis.read();
		
		if (!safetyButton.isTriggered() || Math.abs(joy) <= 0.1){
			joy = 0.0;
		}
		
		position += joy * speed;
		if (position > max) {
			position = max;
		} else if (position < min) {
			position = min;
		}	
		
		return position;
	}

}
