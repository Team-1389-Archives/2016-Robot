package com.team1389.y2016.robot.control;

import org.strongback.command.Command;

import com.team1389.base.wpiWrappers.TalonSRXSpeedHardware;

public class WaitUntilSpeedControllerWithinRange extends Command{
	
	TalonSRXSpeedHardware controller;
	double min, max;

	public WaitUntilSpeedControllerWithinRange(TalonSRXSpeedHardware controller, double min, double max) {
		this.max = max;
		this.min = min;
		this.controller = controller;
	}

	@Override
	public boolean execute() {
		double speed = controller.getSpeed();
		//System.out.println("in WaitUntilSpeedControllerWithinRange: " + speed);
		return Math.abs(speed) < Math.abs(max) && Math.abs(speed) > Math.abs(min);
	}

}
