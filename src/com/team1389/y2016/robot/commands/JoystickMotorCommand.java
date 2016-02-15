package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;

public class JoystickMotorCommand extends Command{
	Motor intake;
	ContinuousRange joyAxis;
	double speedMod;
	
	public JoystickMotorCommand(Motor intake, ContinuousRange joyAxis, double speedMod) {
		this.joyAxis = joyAxis;
		this.intake = intake;
		this.speedMod = speedMod;
	}

	@Override
	public boolean execute() {
		intake.setSpeed(joyAxis.read() * speedMod);
		return false;
	}

}
