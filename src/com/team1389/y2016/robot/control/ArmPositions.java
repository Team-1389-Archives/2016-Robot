package com.team1389.y2016.robot.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.strongback.components.ui.InputDevice;

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
	
	private static class ButtonPosition{
		final public int button;
		final public Position pos;
		public ButtonPosition(int button, Position pos) {
			this.button = button;
			this.pos = pos;
		}
	}
	
	public static ArmPos getPosFromJoystick(InputDevice joystick){
		List<ButtonPosition> positions = new ArrayList<ButtonPosition>();
		positions.add(new ButtonPosition(0, Position.DOWN));
		positions.add(new ButtonPosition(1, Position.LOW_BAR));
		positions.add(new ButtonPosition(2, Position.LOW_GOAL));
		
		Position pos = null;
		
		for (ButtonPosition bp : positions){
			if (joystick.getButton(bp.button).isTriggered()){
				pos = bp.pos;
			}
		}
		
		if (pos == null){
			return null;
		} else {
			return getPosition(pos);
		}
	}
}
