package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;

import edu.wpi.first.wpilibj.CANTalon;

public class JoystickCANTalonCommand extends Command{
	CANTalon intake;
	ContinuousRange joyAxis;
	
	public JoystickCANTalonCommand(CANTalon intake, ContinuousRange joyAxis) {
		this.joyAxis = joyAxis;
		this.intake = intake;
	}

	@Override
	public boolean execute() {
		intake.set(joyAxis.read());
		return false;
	}
}
