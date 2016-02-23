package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;

public class JoystickMotorCommandAsButton extends Command{
	Motor intake;
	ContinuousRange joyAxis;
	double speedMod;
	
	public JoystickMotorCommandAsButton(Motor motor, ContinuousRange joyAxis, double speedMod) {
		this.joyAxis = joyAxis;
		this.intake = motor;
		this.speedMod = speedMod;
	}

	@Override
	public boolean execute() {
		double read = joyAxis.read();
		double out;
		if (read > 0.8){
			out = speedMod;
		} else if (read < -0.8){
			out = -speedMod;
		} else {
			out = 0.0;
		}
		intake.setSpeed(out);
		return false;
	}

}
