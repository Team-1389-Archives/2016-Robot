package com.team1389.y2016.robot.control;

import org.strongback.command.Command;

import edu.wpi.first.wpilibj.Solenoid;

public class SolenoidControlCommand extends Command{
	boolean on;
	Solenoid solenoid;
	public SolenoidControlCommand(boolean on,Solenoid solenoid){
		this.on=on;
		this.solenoid=solenoid;
	}
	@Override
	public boolean execute() {
		solenoid.set(on);
		return true;
	}
	
}
