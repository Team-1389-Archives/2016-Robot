package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;

import com.team1389.y2016.robot.subsystems.Arm;

//This class commands the turret to turn to a specified angle.
public class TurnCommand extends Command {

	Arm turntable;
	double angle;

	TurnCommand(Arm turntable, double angle) {
		super(turntable.getRequirements());
		this.angle = angle;
		this.turntable = turntable;

	}

	@Override
	public void initialize() {
		turntable.setTurnAngle(angle);

	}

	@Override
	public boolean execute() {

		double present = turntable.getTurntableAngle();

		if (Math.abs(present - angle) <= 0.5) {
			return true;
		}

		return false;
	}

}
