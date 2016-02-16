package com.team1389.y2016.robot;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import com.team1389.base.TeleopBase;
import com.team1389.base.wpiWrappers.TalonMotorWrapper;
import com.team1389.y2016.robot.commands.JoystickDriveCommand;
import com.team1389.y2016.robot.commands.JoystickMotorCommand;

import edu.wpi.first.wpilibj.CANTalon;

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
			CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
			Motor elevation = Motor.compose(layout.io.simpleElevationA, layout.io.simpleElevationB);
			simultaneously(
//					new JoystickMotorCommand(layout.io.intakeMotor, layout.io.controllerDriver.getAxis(0)),
//					new ButtonMotorCommand(layout.io.flywheelMotorA, layout.io.controllerDriver.getButton(1), false));
//					new JoystickCANTalonCommand(layout.io.flywheelMotorA, layout.io.controllerDriver.getAxis(2))
					new MonitorCommand(layout.io.leftDriveA, "left"),
					new MonitorCommand(layout.io.rightDriveA, "right"),
//					new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver, 0.2)
//					new MonitorStrongbackTalonCommand(layout.io.rightDriveA, "right-drive"),
//					new MonitorStrongbackTalonCommand(layout.io.leftDriveA, "left-drive")
//					new JoystickDriveCommand(layout.subsystems.drivetrain, layout.io.controllerDriver, 1.0)
//					new JoystickMotorCommand(elevation, layout.io.controllerDriver.getAxis(1), .75)
					new JoystickMotorCommand(new TalonMotorWrapper(layout.io.leftDriveA), layout.io.controllerDriver.getAxis(0), 0.2)
					);
		}
		
	}

}
