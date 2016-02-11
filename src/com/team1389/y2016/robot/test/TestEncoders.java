package com.team1389.y2016.robot.test;

import org.strongback.command.Command;

import com.team1389.base.wpiWrappers.PositionController;

public class TestEncoders extends Command{
	PositionController turntable;
	
	public TestEncoders(PositionController talon) {
		this.turntable = talon;
	}

	@Override
	public boolean execute() {
		System.out.println(turntable.getPosition());
		return false;
	}

}
