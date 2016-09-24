package com.team1389.y2016.robot.test;

import org.strongback.command.Command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MonitorDoubleCommand<E> extends Command{

	E object;
	String label;
	valueDisplay<E> function;
	public MonitorDoubleCommand(E object, String label, valueDisplay<E> function){
		this.object = object;
		this.label = label;
		this.function = function;
	}
	
	@Override
	public boolean execute() {
		SmartDashboard.putNumber(label, function.getDouble(object));
		return false;
	}
	
	public interface valueDisplay<E>{
		public double getDouble(E o);
	}

}
