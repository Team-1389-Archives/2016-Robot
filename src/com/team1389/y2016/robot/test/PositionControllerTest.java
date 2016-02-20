package com.team1389.y2016.robot.test;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.Timer;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.wpiWrappers.TalonSRXPositionHardware;

import edu.wpi.first.wpilibj.CANTalon;

public class PositionControllerTest extends Command{
	
	TalonSRXPositionHardware controller;
	InputDevice input;
	StringBuilder builder;
	double maxPos, minPos, maxChange;
	Timer timer;
	PIDConstants pidC;
	double setpoint;

	public PositionControllerTest(TalonSRXPositionHardware controller, InputDevice input, PIDConstants pidC, double maxPos, double minPos, double maxChange) {
		this.controller = controller;
		this.input = input;
		this.maxPos = maxPos;
		this.minPos = minPos;
		this.maxChange = maxChange;
		this.pidC = pidC;
		
		builder = new StringBuilder();
		timer = new Timer();
		
	}
	
	@Override
	public void initialize() {
		timer.zero();
		CANTalon talon = controller.getTalon();

		talon.configNominalOutputVoltage(0, 0);
		talon.configMaxOutputVoltage(12);

		this.controller.setPID(pidC);
		StringBuilder builder = new StringBuilder();
		builder.append("p=");
		builder.append(pidC.p);
		builder.append(" i=");
		builder.append(pidC.i);
		builder.append(" d=");
		builder.append(pidC.d);
		builder.append(" kv=");
		builder.append(pidC.kv);
		builder.append(" ka=");
		builder.append(pidC.ka);
		System.out.println(builder.toString());
		
		controller.setCurrentPositionAs(0);
		setpoint = controller.getPosition();
		controller.setPosition(setpoint);
	}

	@Override
	public boolean execute() {
		double position = controller.getPosition();
		double goalPoint  = input.getAxis(1).read();
		
		setpoint = getNextSetpoint(goalPoint, timer.get());
		
		if (input.getButton(1).isTriggered()){
			controller.setPosition(setpoint);
		} else {
			controller.disable();
		}
	
		builder.append("pos:");
		builder.append(position);
		builder.append(" setpt:");
		builder.append(setpoint);
		builder.append(" out:");
		builder.append(controller.getTalon().getOutputCurrent() / controller.getTalon().getBusVoltage());
		
		System.out.println(builder.toString());
		
		builder.setLength(0);

		timer.zero();
		return false;
	}
	
	private double getNextSetpoint(double goalPoint, double timeDiff){
		double maxChangeInSetpoint = maxChange * timeDiff;
		double newSetpoint;
		
		if (Math.abs(goalPoint - setpoint) < maxChangeInSetpoint){
			newSetpoint = goalPoint;
		} else if (goalPoint > setpoint){
			newSetpoint = setpoint + maxChangeInSetpoint;
		} else {
			newSetpoint = setpoint - maxChangeInSetpoint;
		}
		
		if (newSetpoint > maxPos){
			newSetpoint = maxPos;
		} else if (newSetpoint < minPos){
			newSetpoint = minPos;
		}
		
		return newSetpoint;
	}

}
