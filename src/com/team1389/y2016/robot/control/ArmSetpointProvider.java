package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.SetableSetpointProvider;
import com.team1389.y2016.robot.RobotMap;

public class ArmSetpointProvider extends Command{
	
	InputDevice joystick;
	SetableSetpointProvider setpoint;
	double point;

	public ArmSetpointProvider(InputDevice joystick, SetableSetpointProvider setpoint) {
		this.joystick = joystick;
		this.setpoint = setpoint;
		this.point = 0;
	}

	private double getSetpoint() {

		if (joystick.getButton(4).isTriggered()){
			point =  0.15;
		} else if (joystick.getButton(1).isTriggered()){
			point =  0.0;
		} else if (joystick.getButton(2).isTriggered()){
			point = 0.25;
		} else if (joystick.getButton(3).isTriggered()){
			point = 0.07;
		} else if (joystick.getButton(6).isTriggered()){
			point = RobotMap.highGoalPoint.get();
		}
		
		return point;
	}

	@Override
	public boolean execute() {
		setpoint.setSetpoint(getSetpoint());
		return false;
	}

}
