package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;

import com.team1389.base.util.control.SetableSetpointProvider;

public class JoystickPositionControl extends Command{
	double pos;
	double speedMod=.0005;
	SetableSetpointProvider provider;
	ContinuousRange rng;
	public JoystickPositionControl(SetableSetpointProvider provider,ContinuousRange rng) {
		pos=0;
		this.provider=provider;
		this.rng=rng;
	}
	@Override
	public boolean execute() {
		System.out.println("Joystick Position Control thinks "+pos);
		pos+=rng.read()*speedMod;
		provider.setSetpoint(pos);
		return false;
	}
	
}
