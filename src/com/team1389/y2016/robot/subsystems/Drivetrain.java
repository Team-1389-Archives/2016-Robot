package com.team1389.y2016.robot.subsystems;

import java.util.Arrays;

import org.strongback.components.Motor;

import com.team1389.base.Subsystem;

public class Drivetrain extends Subsystem{
	Motor left;
	Motor right;
	public Drivetrain(Motor leftMotors, Motor rightMotors) {
		super(leftMotors, rightMotors);
		left = leftMotors;
		right = rightMotors;
	}
	
	public void set(double leftSpeed, double rightSpeed){

		left.setSpeed(leftSpeed);
		right.setSpeed(rightSpeed);
	}
}
