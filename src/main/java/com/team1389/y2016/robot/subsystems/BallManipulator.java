package com.team1389.y2016.robot.subsystems;

import org.strongback.components.Motor;

import com.team1389.base.Subsystem;

public class BallManipulator extends Subsystem{
	Motor intake, flywheels;

	public BallManipulator(Motor intake, Motor flywheels) {
		super(intake, flywheels);
		this.intake = intake;
		this.flywheels = flywheels;
	}
}
