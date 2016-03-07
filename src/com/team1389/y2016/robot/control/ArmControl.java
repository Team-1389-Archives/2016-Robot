package com.team1389.y2016.robot.control;

import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.Timer;
import com.team1389.base.util.control.SetpointProvider;
import com.team1389.base.wpiWrappers.PositionController;
import com.team1389.y2016.robot.control.ArmPositions.ArmPos;

public class ArmControl{
	//problem: position controller ramp command currently resets position on start, need to make a better one.
	PositionController elevation;
	PositionController turntable;
	ArmPos goalPoint;
	Timer timer;
	

	public ArmControl(PositionController elevation, PositionController turntable) {
		this.elevation = elevation;
		this.turntable = turntable;
		this.goalPoint = new ArmPos(elevation.getPosition(), turntable.getPosition());
		this.timer = new Timer();
	}

	private boolean aboveBumper(){
		return elevation.getPosition() > ArmPositions.ABOVE_BUMPER_ELEVATION;
	}
	
	private double getNextSetpoint(double goalPoint, double timeDiff, double maxChange, double setpoint, double minPos, double maxPos){
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
	
	class ElevationSetpoint implements SetpointProvider{

		@Override
		public double getSetpoint() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
//	public Command getTeleopControlCommand(InputDevice joystick){
//	}
	
	
}
