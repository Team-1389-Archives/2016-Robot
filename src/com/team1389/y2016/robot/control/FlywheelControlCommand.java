package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.SpeedControllerSetCommand;
import com.team1389.y2016.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class FlywheelControlCommand extends Command{
	final int SPIN_UP_BUTTON = 5;
	SpeedControllerSetCommand speedControlCommand;
	InputDevice joystick;
	double speed;
	DoubleConstant flySpeed;
	Joystick rumbler;

	public FlywheelControlCommand(InputDevice joystick, SpeedControllerSetCommand speedControlCommand) {
		this.speedControlCommand = speedControlCommand;
		this.joystick = joystick;
		flySpeed = new DoubleConstant("flywheel speed", -27000.0);
		rumbler = new Joystick(RobotMap.manipJoystickPort);
	}
	
	@Override
	public void initialize() {
	}

	@Override
	public boolean execute() {
		speed = flySpeed.get();
		boolean spinning = joystick.getButton(SPIN_UP_BUTTON).isTriggered();
		
		if (spinning){
			speedControlCommand.setSpeed(speed);
		} else {
			speedControlCommand.setSpeed(0);
		}
		
		boolean rumble;
		System.out.println("FlywheelControl speed: " + speedControlCommand.getController().getSpeed());
		if (Math.abs(speed * .90) < Math.abs(speedControlCommand.getController().getSpeed())){
			rumble = true;
		} else {
			rumble = false;
		}
		
		if (rumble){
			rumble(1.0f);
		} else {
			rumble(0.0f);
		}
		
		
		return false;
	}
	
	@Override
	public void end() {
		speedControlCommand.setSpeed(0);
		rumble(0.0f);
	}
	
	@Override
	public void interrupted() {
		end();
	}
	
	private void rumble(float rumb){
			rumbler.setRumble(RumbleType.kLeftRumble, rumb);
			rumbler.setRumble(RumbleType.kRightRumble, rumb);
	}

}
