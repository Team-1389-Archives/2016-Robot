package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;

import com.team1389.base.util.control.ConfigurablePid.PIDConstants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class TalonPIDToPositionCommand extends Command{
	
	PIDConstants pidConstants;
	
	public TalonPIDToPositionCommand(PIDConstants pidConstants, CANTalon talon) {
		talon.changeControlMode(TalonControlMode.Position);//this could lead to confusion later in code... oh well
		this.pidConstants = pidConstants;
	}

	@Override
	public boolean execute() {
		return false;
	}

}
