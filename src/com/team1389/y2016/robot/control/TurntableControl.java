package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class TurntableControl extends Command{

	InputDevice joy;
	CANTalon turn;
	
	public TurntableControl(InputDevice joy, CANTalon turn) {
		this.joy = joy;
		this.turn = turn;
	}
	@Override
	public boolean execute() {
		turn.changeControlMode(TalonControlMode.PercentVbus);
		turn.set(joy.getAxis(4).read());
//		if (joy.getButton(7).isTriggered()){
//			turn.set(joy.getAxis(4).read());
//		} else {
//			turn.set(0);
//		}
		return false;
	}

}
