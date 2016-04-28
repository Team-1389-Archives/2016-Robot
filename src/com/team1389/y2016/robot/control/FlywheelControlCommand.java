package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.Switch;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.AxisAsButton;
import com.team1389.base.util.control.SpeedControllerSetCommand;
import com.team1389.y2016.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class FlywheelControlCommand extends Command {
	final int SPIN_UP_BUTTON = 5;
	SpeedControllerSetCommand speedControlCommand;
	InputDevice joystick;
	double speed;
	Joystick rumbler;
	Switch batterFly, batterArm;

	public FlywheelControlCommand(InputDevice joystick, SpeedControllerSetCommand speedControlCommand) {
		this.speedControlCommand = speedControlCommand;
		this.joystick = joystick;
		rumbler = new Joystick(RobotMap.manipJoystickPort);
		batterFly = new AxisAsButton(joystick.getAxis(2));
		batterArm = new AxisAsButton(joystick.getAxis(3));
	}

	@Override
	public void initialize() {
	}

	@Override
	public boolean execute() {
		speed = RobotMap.flySpeed.get();
		boolean spinning = joystick.getButton(SPIN_UP_BUTTON).isTriggered();
		
		double mult = -1;

		if (spinning) {
			speedControlCommand.setSpeed(speed * mult);
		} else {
			speedControlCommand.setSpeed(0);
			if (joystick.getAxis(1).read() < -0.9) {// -0.5
				speedControlCommand.setSpeed(2500 * mult);
				//speedControlCommand.hackVoltageSet(-1);
			}
		}
//		if (joystick.getAxis(1).read() < -0.9) {// -0.5
//			// speedControlCommand.setSpeed(2500);
//			speedControlCommand.hackVoltageSet(-1);
//		} else {
//			speedControlCommand.hackVoltageSet(0);
//		}

		boolean rumble;
		if (Math.abs(speed * .90) < Math.abs(speedControlCommand.getController().getSpeed())) {
			rumble = true;
		} else {
			rumble = false;
		}

		if (rumble) {
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

	private void rumble(float rumb) {
		rumbler.setRumble(RumbleType.kLeftRumble, rumb);
		rumbler.setRumble(RumbleType.kRightRumble, rumb);
	}

}
