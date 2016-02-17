package com.team1389.y2016.robot;

import org.strongback.command.Command;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class MonitorCommand extends Command{
	
	CANTalon talon;
	String name;
	
	public MonitorCommand(CANTalon talon, String name) {
		this.talon = talon;
		this.name = name;
	}

	@Override
	public boolean execute() {
		double speed = talon.getEncVelocity();
		System.out.println("Talon " + name + ": " + speed + " mode:" + talon.getControlMode());
		return false;
	}

}
