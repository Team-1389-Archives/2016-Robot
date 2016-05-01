package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.Timer;

public class IntakeControlCommand extends Command{
	static final double delay= .15;
	Timer timer;
	boolean triggered;
	Motor motor;
	InputDevice joystick;
	ContinuousRange axis;
	Switch button, ir;
	
	public IntakeControlCommand(Motor motor, ContinuousRange axis, Switch button, Switch ir) {
		timer=new Timer();
		this.motor = motor;
		this.axis = axis;
		this.button = button;
		this.ir = ir;
	}

	@Override
	public boolean execute() {
		double speed;
		if(!ir.isTriggered())timer.zero();
		boolean canGoIn = timer.get()<delay|| button.isTriggered();
		speed = axis.read();
		double finalSpeed;
		
		if (canGoIn){
			finalSpeed = speed;
		} else {
			finalSpeed = -.5;
		}
		
		motor.setSpeed(finalSpeed);
		return false;
	}

}
