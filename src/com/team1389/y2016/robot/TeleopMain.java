package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.components.Motor;

import com.team1389.base.TeleopBase;
import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.DoubleConstant;
import com.team1389.base.util.control.ConfigurablePid;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.y2016.robot.commands.JoystickMotorCommand;
import com.team1389.y2016.robot.commands.MonitorCommand;
import com.team1389.y2016.robot.test.PIDTestCommand;

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	ConfigurablePid pidC;
	DoubleConstant target;
	
	public TeleopMain(RobotLayout layout) {
		this.layout = layout;
		pidC = new ConfigurablePid("pid config", new PIDConstants(0, 0, 0, 0, 0));
		target = new DoubleConstant("target", 1.0);
	}

	@Override
	public void setupTeleop() {
		layout.io.configFollowerTalonsToWorkAroundDumbGlitch();
	}

	@Override
	public  Command provideCommand() {
//		return new SpeedPIDTestCommand(pidC.get(), layout.io.leftDriveA, layout.io.controllerDriver, targetSpeed.get());
//		return new TestMotionProfileCommand();
//		return new PIDTestCommand(pidC.get(), layout.io.leftDriveA, layout.io.controllerDriver, target.get(), false, RobotMap.leftEncoderTicksPerRotation);
//		return CommandsUtil.combineSimultaneous(new JoystickMotorCommand(motor, layout.io.controllerDriver.getAxis(1), .5),
//				new JoystickMotorCommand(new TalonMotorWrapper(layout.io.flywheelMotorA), layout.io.controllerDriver.getAxis(2), 1.0));
		return new PIDTestCommand(pidC.get(), layout.io.simpleElevationA , layout.io.controllerDriver, target.get(), false, RobotMap.armElevationTicksPerRotation, true);
//		return new MonitorCommand(layout.io.simpleElevationA, "elev");

	}
}
