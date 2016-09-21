package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.control.SetableSetpointProvider;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;
import com.team1389.y2016.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmSetpointProvider extends Command{
	
	InputDevice joystick;
	SetableSetpointProvider setpoint;
	double point;
	TalonSRXPositionHardware armMotor;
	TurntableControl control;
	public ArmSetpointProvider(InputDevice joystick, SetableSetpointProvider setpoint, TurntableControl control,TalonSRXPositionHardware armMotor) {
		this.joystick = joystick;
		this.setpoint = setpoint;
		this.point = 0;
		this.control=control;
		this.armMotor=armMotor;
	}

	private double getSetpoint() {

		if (joystick.getButton(4).isTriggered()){
			point =  0.15;
		} else if (joystick.getButton(1).isTriggered()){
			point =  0.0;
		} else if (joystick.getButton(2).isTriggered()){
			point = 0.25;
		} else if (joystick.getButton(3).isTriggered()){
			point = 0.07;
		} else if (joystick.getButton(6).isTriggered()){
			//point = RobotMap.highGoalPoint.get();
		}
		
		return point;
	}

	@Override
	public boolean execute() {
		SmartDashboard.putNumber("Arm setpoint",setpoint.getSetpoint());
		SmartDashboard.putNumber("Arm position",setpoint.getSetpoint());
		setpoint.setSetpoint(armMotor.getPosition());

		if(control.isClear()||armMotor.getPosition()>.12||getSetpoint()>.12){
			setpoint.setSetpoint(getSetpoint());
		}else{
			setpoint.setSetpoint(armMotor.getPosition()+.05);
		}
		return false;
	}
		
}
