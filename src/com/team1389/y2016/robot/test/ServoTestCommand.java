package com.team1389.y2016.robot.test;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;

import edu.wpi.first.wpilibj.Servo;

public class ServoTestCommand extends Command{

	Servo servo;
	ContinuousRange axis;
	
	public ServoTestCommand(Servo servo, ContinuousRange axis) {
		this.servo = servo;
		this.axis = axis;
	}
	@Override
	public boolean execute() {
		servo.set(axis.read());
		return false;
	}

}
