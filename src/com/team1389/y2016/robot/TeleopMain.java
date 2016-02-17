package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import com.team1389.base.TeleopBase;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.wpiWrappers.TalonMotorWrapper;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
import com.team1389.y2016.robot.commands.JoystickMotorCommand;
import com.team1389.y2016.robot.commands.SpeedPIDTestCommand;

import edu.wpi.first.wpilibj.CANTalon;

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	ConfigurablePid pidC;
	DoubleConstant targetSpeed;
	
	public TeleopMain(RobotLayout layout) {
		this.layout = layout;
		pidC = new ConfigurablePid("pid config", new PIDConstants(0, 0, 0, 0, 0));
		targetSpeed = new DoubleConstant("target speed", 500.0);
	}

	@Override
	public void setupTeleop() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
	}

	@Override
	public  Command provideCommand() {
		return new SpeedPIDTestCommand(pidC.get(), layout.io.leftDriveA, layout.io.controllerDriver, targetSpeed.get());
	}
}
