package com.team1389.y2016.robot;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;

import com.team1389.y2016.robot.test.PositionControllerRampCommand.SetpointProvider;

import edu.wpi.first.wpilibj.buttons.Button;

public class JoystickSetpointControlAriStyleWithReset implements SetpointProvider {
	double position;
	double min, max, speed, start;
	ContinuousRange joystickAxis;
	Switch button;
	
	public JoystickSetpointControlAriStyleWithReset(ContinuousRange joystickAxis, Switch button, double min, double max, double speed, double start) {
		this.min = min;
		this.max = max;
		this.speed = speed;
		this.start = start;
		this.joystickAxis = joystickAxis;
		this.button = button;
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
		
		if (button.isTriggered()){
			position = start;
		}
		
		return position;
	}

}
