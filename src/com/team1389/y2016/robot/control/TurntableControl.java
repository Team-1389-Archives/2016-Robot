package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.y2016.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class TurntableControl extends Command {
	boolean zeroing;
	InputDevice joy;
	CANTalon turn;

	public TurntableControl(InputDevice joy, CANTalon turn) {
		this.joy = joy;
		this.turn = turn;
	}

	@Override
	public boolean execute() {
		if(joy.getButton(10).isTriggered()){
			zeroing=true;
		}
		if(joy.getButton(8).isTriggered()){
			turn.setPosition(0);
			zeroing=false;
		}
		double maxSpeed=.5;
		if(Math.abs(joy.getAxis(4).read())>.1){
			zeroing=false;
		}
		if (zeroing) {
			if (Math.abs(turn.getPosition()) < 20) {
				zeroing = false;
			} else {
				double setSpeed=Math.abs(turn.getPosition()) / 2000;
				if(setSpeed>maxSpeed)setSpeed=maxSpeed;
				setSpeed*=-Math.signum(turn.getPosition());
				turn.set(setSpeed);
			}
		} else {

			turn.changeControlMode(TalonControlMode.PercentVbus);
			if (Math.abs(turn.getPosition()) >= .2 * RobotMap.turnTableTicksPerRotation) {
				if (Math.signum(joy.getAxis(4).read()) != Math.signum(turn.getPosition())) {
					turn.set(joy.getAxis(4).read() / 4);
				} else {
					turn.set(0);
				}
			} else {
				turn.set(joy.getAxis(4).read() / 4);
			}
		}
		return false;
	}

}
