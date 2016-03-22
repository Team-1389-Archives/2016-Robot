package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.Switch;
import org.strongback.components.ui.InputDevice;

import edu.wpi.first.wpilibj.CANTalon;

public class IntakeControlWithIRCommand extends Command{
	final int INTAKE_IN_BUTTON = 1;
	final int INTAKE_OUT_BUTTON = 3;
	final int INTAKE_SHOOT_BUTTON = 6;
	final double inSpeed = 1.0;

	CANTalon motor;
	InputDevice joystick;
	Switch sensor;
	
	public IntakeControlWithIRCommand(CANTalon motor, InputDevice joystick, Switch sensor) {
		this.motor = motor;
		this.joystick = joystick;
		this.sensor = sensor;
	}

	@Override
	public boolean execute() {
		boolean goIn = joystick.getButton(INTAKE_IN_BUTTON).isTriggered();
		boolean goOut = joystick.getButton(INTAKE_OUT_BUTTON).isTriggered();
		boolean goInToShoot = joystick.getButton(INTAKE_SHOOT_BUTTON).isTriggered();
		
		double speed;
		
		if (goInToShoot){
			speed = inSpeed;
		} else if (goOut){
			speed = -inSpeed;
		} else if (goIn && !sensor.isTriggered()){
			speed = inSpeed;
		} else {
			speed = 0.0;
		}

		motor.set(speed);
		
		return false;
	}

}
