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
		super(driveTrain.getRequirements());
		this.driveTrain = driveTrain;
		this.joyStick = joystick;

	}

	@Override
	public boolean execute() {
		
		double speedMod = 0.5;
		
		double x, y;
		double left, right;
		x = joyStick.getAxis(0).read() - 0.079;
		y = joyStick.getAxis(1).read() - 0.071;
		System.out.println("x: " + x + " y: " + y);
		left = y - x;
		right = y + x;
//		System.out.println("left: " + left + " right: " + right);
		driveTrain.set(left * speedMod, right * speedMod);

		return false;
	}

}
