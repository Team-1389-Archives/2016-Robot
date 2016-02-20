package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.TalonSRX;

public class MonitorStrongbackTalonCommand extends Command{
	
	TalonSRX talon;
	String name;
	
	public MonitorStrongbackTalonCommand(TalonSRX talon, String name) {
		this.talon = talon;
		this.name = name;
	}

	@Override
	public boolean execute() {
		double speed = talon.getEncoderInput().getRate();
		System.out.println("Talon " + name + ": " + speed);
		return false;
	}

}
