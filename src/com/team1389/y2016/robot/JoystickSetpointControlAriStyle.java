package com.team1389.y2016.robot;

import org.strongback.components.ui.ContinuousRange;

import com.team1389.base.util.control.PositionControllerRampCommand.SetpointProvider;

public class JoystickSetpointControlAriStyle implements SetpointProvider {
	double position;
	double min, max, speed, start;
	ContinuousRange joystickAxis;
	
	public JoystickSetpointControlAriStyle(ContinuousRange joystickAxis, double min, double max, double speed, double start) {
		this.min = min;
		this.max = max;
		this.speed = speed;
		this.start = start;
		this.joystickAxis = joystickAxis;
		this.position = start;
	}
	
	@Override
	public double getSetpoint() {
		position += joystickAxis.read() * speed;
		if (position > max) {
			position = max;
		} else if (position < min) {
			position = min;
		}
		return position;
	}

}
