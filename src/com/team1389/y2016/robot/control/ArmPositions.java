package com.team1389.y2016.robot.control;

public class ArmPositions {
	public static final double ABOVE_BUMPER_ELEVATION = 0.1;
	public static final double ELEVATION_MAX = 0.27;
	public static final double ELEVATION_MIN = 0.0;
	public static final double TURNTABLE_MAX = 0.3;
	public static final double TURNTABLE_MIN = -0.3;
	
	public static final double ELEVATION_MAX_SPEED = 0.01;
	public static final double TURNTABLE_MAX_SPEED = 0.01;
	
	
	
	public static class ArmPos{
		final double elevation;
		final double turntable;
		public ArmPos(double elevation, double turntable) {
			this.elevation = elevation;
			this.turntable = turntable;
		}
	}
	
	public static enum Position{
		DOWN,
		LOW_BAR,
		LOW_GOAL
	}
	
	public static ArmPos getPosition(Position pos){
		switch (pos) {
		case DOWN:
			return new ArmPos(0, 0);
		case LOW_BAR:
			return new ArmPos(0.2, 0);
		case LOW_GOAL:
			return new ArmPos(0.3, 0);
		default:
			//this line should never happen
			throw new IllegalArgumentException(pos + " is not a valid position");
		}
	}
}
