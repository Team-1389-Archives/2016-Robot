package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.control.SetableSetpointProvider;

public class ArmSetpointProvider extends Command{
	
	InputDevice joystick;
	SetableSetpointProvider setpoint;
	
	public ArmSetpointProvider(InputDevice joystick, SetableSetpointProvider setpoint) {
		this.joystick = joystick;
		this.setpoint = setpoint;
	}

	private double getSetpoint() {

		if (joystick.getButton(2).isTriggered()){
			return 0.15;
		} else {
			return 0.0;
		}
	}

	@Override
	public boolean execute() {
		setpoint.setSetpoint(getSetpoint());
		return false;
	}

}
