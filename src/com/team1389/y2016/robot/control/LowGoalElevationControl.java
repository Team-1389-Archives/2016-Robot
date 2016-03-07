package com.team1389.y2016.robot.control;

import org.strongback.components.ui.ContinuousRange;

import com.team1389.base.util.control.SetpointProvider;

public class LowGoalElevationControl implements SetpointProvider{
	
	ContinuousRange axis;
	
	public LowGoalElevationControl(ContinuousRange axis) {
		this.axis = axis;
	}

	@Override
	public double getSetpoint() {
		double read = axis.read();
		double value;
		if (read > 0.5){
			value = 0.15;
		} else if (read < -0.5){
			value = 0.09;
		} else {
			value = 0.0;
		}
		
		System.out.println("value is " + value);
		return value;
	}

}
