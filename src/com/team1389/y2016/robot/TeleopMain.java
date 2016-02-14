package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.components.Motor;

import com.team1389.base.TeleopBase;
import com.team1389.y2016.robot.commands.ButtonMotorCommand;
import com.team1389.y2016.robot.commands.JoystickCANTalonCommand;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
import com.team1389.y2016.robot.commands.JoystickMotorCommand;

//TODO: make this work the way AutonomousMain does, plus also a TestMain

public class TeleopMain extends TeleopBase{
	RobotLayout layout;
	
	public TeleopMain(RobotLayout layout) {
		this.layout = layout;
	}

	@Override
	public void setupTeleop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public  Command provideCommand() {
//		return new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver);

//		Motor motor = Motor.compose(layout.io.simpleElevationA, layout.io.simpleElevationB);
//		return new JoystickMotorCommand(motor, layout.io.controllerDriver.getAxis(1));
		
//		return new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerDriver.getAxis(1));
		return new TeleopCommand();
	}
	
	class TeleopCommand extends CommandGroup{
		public TeleopCommand() {
			simultaneously(
					new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerDriver.getAxis(0)),
//					new ButtonMotorCommand(layout.io.flywheelMotorA, layout.io.controllerDriver.getButton(1), false));
					new JoystickCANTalonCommand(layout.io.flywheelMotorA, layout.io.controllerDriver.getAxis(2))
//					new MonitorCommand(layout.io.flywheelMotorA, "flywheel")
//					new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver),
//					new MonitorStrongbackTalonCommand(layout.io.rightDriveA, "right-drive"),
//					new MonitorStrongbackTalonCommand(layout.io.leftDriveA, "left-drive")
					);
		}
		
	}

}
