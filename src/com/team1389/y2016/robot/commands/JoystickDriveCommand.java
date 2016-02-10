package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.InputDevice;

import com.team1389.y2016.robot.subsystems.Drivetrain;

//This class commands the drive train to set the motors to the specified speeds in order to turn
public class JoystickDriveCommand extends Command {

	Drivetrain driveTrain;
	InputDevice joyStick;

	public JoystickDriveCommand(Drivetrain driveTrain, InputDevice joystick) {
//		super(driveTrain.getRequirements());
		this.driveTrain = driveTrain;
		this.joyStick = joystick;

	}

	@Override
	public boolean execute() {
		ContinuousRange x, y;
		double left, right;
		x = joyStick.getAxis(0);
		y = joyStick.getAxis(1);
		left = y.read() - x.read();
		right = y.read() + x.read();
		driveTrain.set(left, right);

		return false;
	}

}
