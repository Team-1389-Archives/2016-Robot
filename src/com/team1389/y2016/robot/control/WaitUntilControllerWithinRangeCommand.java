package com.team1389.y2016.robot.control;

import org.strongback.command.Command;

import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;

public class WaitUntilControllerWithinRangeCommand extends Command{
	
	TalonSRXPositionHardware controller;
	double min, max;

	public WaitUntilControllerWithinRangeCommand(TalonSRXPositionHardware controller, double min, double max) {
		this.max = max;
		this.min = min;
		this.controller = controller;
	}

	@Override
	public boolean execute() {
		double pos = controller.getPosition();
		return pos < max && pos > min;
	}

}
