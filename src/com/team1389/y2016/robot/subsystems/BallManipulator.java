package com.team1389.y2016.robot.subsystems;

import org.strongback.components.Motor;

import com.team1389.base.Subsystem;

import edu.wpi.first.wpilibj.CANTalon;

public class BallManipulator extends Subsystem{
	Motor intake;
	CANTalon flywheels;

	public BallManipulator(Motor intake, CANTalon flywheels) {
		this.intake = intake;
		this.flywheels = flywheels;
	}
}
