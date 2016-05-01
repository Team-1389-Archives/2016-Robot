package com.team1389.y2016.robot.control;

import edu.wpi.first.wpilibj.Solenoid;

public class CombinedSolenoid extends Solenoid{
	static final int combinedPort=3;
	Solenoid[] solenoids;
	public CombinedSolenoid(int... ports){
		super(combinedPort);
		solenoids=new Solenoid[ports.length];
		for(int p=0;p<ports.length;p++){
			if(ports[p]==combinedPort)solenoids[p]=this;
			solenoids[p]=new Solenoid(ports[p]);
		}
	}
	@Override
	public void set(boolean on){
		for(Solenoid solenoid:solenoids){
			solenoid.set(on);
		}
	}
	
}
