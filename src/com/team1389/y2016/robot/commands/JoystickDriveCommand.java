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
		
		double speedMod = 0.5 * overAllSpeedMod;
		double turnMod = .6;
		
		if(!joyStick.getButton(1).isTriggered()){
			speedMod = 1.0;
			turnMod = 1.0;
		}
		
		double y, normalTurn, extraTurn, smallTurn;
		double left, right;
		normalTurn = joyStick.getAxis(2).read() * turnMod;
		y = joyStick.getAxis(1).read();
		extraTurn = joyStick.getAxis(0).read();
		
		int pov = joyStick.getDPad(0).getDirection();
		
		final double smallTurnVoltage = 0.35;
		
		if (pov == 315 || pov == 270 || pov == 225){
			smallTurn = -smallTurnVoltage;
		} else if (pov == 135 || pov == 90 || pov == 45){
			smallTurn = smallTurnVoltage;
		} else {
			smallTurn = 0;
		}
		
		double x = absMax(absMax(normalTurn, smallTurn), extraTurn);
		left = y - x;
		right = y + x;
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
