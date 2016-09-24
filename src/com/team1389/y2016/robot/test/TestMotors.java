package com.team1389.y2016.robot.test;

import org.strongback.command.Command;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestMotors extends Command{

	CANTalon talon;
	public TestMotors(CANTalon talon){
		this.talon = talon;
	}
	
	@Override
	public boolean execute() {
		SmartDashboard.putNumber("Encoder data",talon.getPosition());
		return false;
	}

}
