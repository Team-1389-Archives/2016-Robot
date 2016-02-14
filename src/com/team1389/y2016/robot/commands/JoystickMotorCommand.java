package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;

public class JoystickMotorCommand extends Command{
	Motor intake;
	ContinuousRange joyAxis;
	
	public JoystickMotorCommand(Motor intake, ContinuousRange joyAxis) {
		this.joyAxis = joyAxis;
		this.intake = intake;
	}

	@Override
	public boolean execute() {
		intake.setSpeed(joyAxis.read());
		return false;
	}

}
