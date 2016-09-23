package com.team1389.y2016.robot.control;

import org.strongback.command.Command;

import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		SmartDashboard.putString("Status", "Waiting for controller... "+pos+" from "+max+"-"+min);
		return pos < max && pos > min;
	}

}
