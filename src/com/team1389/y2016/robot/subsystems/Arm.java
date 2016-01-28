package com.team1389.y2016.robot.subsystems;

import com.team1389.base.Subsystem;
import com.team1389.base.wpiWrappers.PositionController;

public class Arm extends Subsystem{
	PositionController elevation;
	PositionController turntable;
	public Arm(PositionController elevation, PositionController turntable) {
		this.elevation = elevation;
		this.turntable = turntable;
	}
	
	public void setElevation(double degrees){
		elevation.setPosition(degrees);
	}
	
	public void setTurnAngle(double degrees){
		turntable.setPosition(degrees);
	}
	
	public double getElevationAngle(){
		return elevation.getPosition();
	}
	
	public double getTurntableAngle(){
		return turntable.getPosition();
	}
}
