package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;

import com.team1389.base.util.Timer;
import com.team1389.base.util.control.MotionProfile;
import com.team1389.base.wpiWrappers.PositionController;

public class PositionControllerFollowMotionProfileCommand extends Command{
	
	PositionController controller;
	MotionProfile profile;
	Timer timer;
	
	public PositionControllerFollowMotionProfileCommand(PositionController controller, MotionProfile profile) {
		this.controller = controller;
		this.profile = profile;
		timer = new Timer();
	}
	
	@Override
	public void initialize() {
		controller.setCurrentPositionAs(0);
		timer.zero();
	}

	@Override
	public boolean execute() {
		double setpoint = profile.getPosition(timer.get());
		return false;
	}

}
