package com.team1389.y2016.robot.control;

import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.control.PositionControllerRampCommand.SetpointProvider;

public class VerticalArmControl implements SetpointProvider{
	InputDevice joystick;
	
	public VerticalArmControl(InputDevice joystick) {
		this.joystick = joystick;
	}

	@Override
	public double getSetpoint() {
		return 0;
	}

}
