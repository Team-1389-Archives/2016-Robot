package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.DoubleConstant;
import com.team1389.y2016.robot.subsystems.Drivetrain;

//This class commands the drive train to set the motors to the specified speeds in order to turn
public class JoystickDriveCommand extends Command {

	Drivetrain driveTrain;
	InputDevice joyStick;
	double overAllSpeedMod;

	public JoystickDriveCommand(Drivetrain driveTrain, InputDevice joystick, double speedMod) {
		super(driveTrain.getRequirements());
		this.driveTrain = driveTrain;
		this.joyStick = joystick;
		this.overAllSpeedMod = speedMod;
	}
	public JoystickDriveCommand(Drivetrain driveTrain, InputDevice joystick) {
		this(driveTrain, joystick, 1.0);
	}

	@Override
	public boolean execute() {
		
		double speedMod = 0.65 * overAllSpeedMod;
		double turnMod = .45;
		
		if(joyStick.getButton(1).isTriggered()){
			speedMod = 1.0;
			turnMod = 0.9;
		}
		
		double y, normalTurn;
		double left, right;
		normalTurn = joyStick.getAxis(2).read() * turnMod;
		y = joyStick.getAxis(1).read();
		double x = normalTurn;
		left = y - x;
		right = y + x;
//		System.out.println("left: " + left + " right: " + right);
		driveTrain.set(left * speedMod, right * speedMod);

		return false;
	}
	
	public double absMax(double a, double b){
		if (Math.abs(a) > Math.abs(b)){
			return a;
		} else {
			return b;
		}
	}

}
