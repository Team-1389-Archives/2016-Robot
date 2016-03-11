package com.team1389.y2016.robot;

import org.strongback.command.Command;

import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.util.control.PositionControllerRampCommand;
import com.team1389.base.util.control.SetableSetpointProvider;
import com.team1389.base.util.control.SetpointProvider;
import com.team1389.base.util.control.TalonDriveControl;
import com.team1389.y2016.robot.control.ArmSetpointProvider;
import com.team1389.y2016.robot.control.LowGoalElevationControl;
import com.team1389.y2016.robot.subsystems.Drivetrain;

public class Subsystems {
	TalonDriveControl drive;
	Drivetrain drivetrain;
//	ArmControl arm;
	SetableSetpointProvider armSetpointProvider;
	Command elevation;
	public Subsystems(IOLayout io) {
		drive = new TalonDriveControl(io.leftDriveController, io.rightDriveController, RobotMap.maxAutonVelocity,
				RobotMap.maxAutonAcceleration, RobotMap.wheelRotationsPerTurn, RobotMap.drivePid.get());
		drivetrain = new Drivetrain(io.leftDriveA, io.rightDriveA);

		armSetpointProvider= new SetableSetpointProvider(false);
		
		
//		armSetpointProvider = new LowGoalElevationControl(io.controllerManip.getAxis(1));

		elevation = new PositionControllerRampCommand(io.armElevationMotor, 
				armSetpointProvider, new PIDConstants(.6, 0, 0, 0, 0), .24, 0, .12);//TODO: extract these numbers to RobotMap


//		arm = new ArmControl(io.armElevationMotor, io.turntableMotor, RobotMap.armPid.get());
		
		
		//calibrate arm
		io.armElevationMotor.setCurrentPositionAs(0);
		
	}
}
